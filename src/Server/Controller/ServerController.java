/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Server.Controller;

import Model.*;
import Server.View.MainFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Main entry for the server-side program.
 * Accepts new connections, keeps track of active connections and handles traffic between them.
 * Also responsible for storing unsent messages and writing/reading users contact-info from disk.
 */
public class ServerController {

    private final Hashtable<User, ClientConnection> connectedUsers = new Hashtable<>();
    private final Hashtable<User, LinkedList<Message>> unsentMessageBuffers = new Hashtable<>();
    private final Hashtable<User, User[]> contactLists;

    private static int connectionTimeout;

    /**
     * Creates a ConnectionListner and intialize main UI.
     * @param port Port to listen to
     * @param connectionTimeout Time to wait for input from clients before timeout.
     */
    public ServerController(int port, int connectionTimeout){
        contactLists = ContactFileManager.readContactLists();

        ServerController.connectionTimeout = connectionTimeout;
        new ConnectionListener(port, this).start();

        new MainFrame(this);
    }

    /**
     * Add a connection to the currently connected users, then notify all users of the currently connected users.
     * Also send the matching contact list and any other unsent messages to the new connection.
     * @param clientConnection Connection for the client to be added.
     * @param user User-info of the client that is connecting.
     */
    public synchronized void addConnection(ClientConnection clientConnection, User user){
        System.out.println("Adding connection: " + clientConnection);

        ClientConnection old = connectedUsers.put(user, clientConnection);
        if(old != null){
            System.out.println("Removing old connection..");
            // Does this even do anything?
            old = null;
        }

        sendContactListUpdate(user);
        sendUnsentMessages(user);

        broadcastServerUpdate(user);

        System.out.println(connectedUsers);
    }

    /**
     * Removes a connection from the currently connected users and then notifies all users of this change.
     * @param clientConnection Connection for the client to be removed.
     */
    public synchronized void removeConnection(ClientConnection clientConnection){
        System.out.println("Removing connection: " + clientConnection);
        connectedUsers.remove(clientConnection.getUser());
        broadcastServerUpdate(null);

        System.out.println(connectedUsers);
    }

    /**
     * Send a list of the currently connected users to all connected users.
     * Also include an optional newly connected user, if this broadcast is triggered by a new connection.
     * @param newlyConnected User-info of the newly connected user. Null if triggered by a disconnect.
     */
    public synchronized void broadcastServerUpdate(User newlyConnected){
        System.out.println("Broadcasting Server Update");
        for (ClientConnection con: connectedUsers.values()) {
            ServerUpdate update = new ServerUpdate(connectedUsers.keySet().toArray(new User[0]), newlyConnected);
            con.addToOutput(update);
        }
    }

    /**
     * Either send the message to the client, or place it in the unsentMessages buffer.
     * @param cm ChatMessage object to be sent.
     * @param user User-info of the target client.
     */
    private synchronized void sendOrBufferMessage(ChatMessage cm, User user){
        if(connectedUsers.containsKey(user)){
            connectedUsers.get(user).addToOutput(cm);
        }
        else {
            if(!unsentMessageBuffers.containsKey(user)){
                unsentMessageBuffers.put(user, new LinkedList<>());
            }
            unsentMessageBuffers.get(user).addLast(cm);
        }
    }

    /**
     * Sends any unsent messages to the target client.
     * @param user User-info of the target client.
     */
    private synchronized void sendUnsentMessages(User user){
        System.out.println("Checking for unsent messages to: " + user.getUsername());

        if(unsentMessageBuffers.containsKey(user)){
            System.out.println("Sending unsent messages!");

            LinkedList<Message> unsentQueue = unsentMessageBuffers.get(user);
            while (unsentQueue.size() > 0){
                Message message = unsentQueue.removeFirst();
                if(message instanceof ChatMessage cm){
                    cm.setMsgText("<OLD> " + cm.getMsgText());
                }
                connectedUsers.get(user).addToOutput(message);
            }
        }
    }

    /* TODO: This is currently being executed from the thread belonging to ClientConnection.InputFromClient.
        A lot of ServerController stuff could maybe run on separate thread? */

    /**
     * Forward a ChatMessage from one client to it's recipients.
     * @param cm ChatMessage to be sent.
     */
    public void incomingChatMessage(ChatMessage cm){
        cm.reachedServer();

        User sender = cm.getSender();
        sendOrBufferMessage(cm, sender);

        for (User recipient: cm.getRecipients()) {
            if(recipient.equals(sender))
                continue;

            sendOrBufferMessage(cm, recipient);
        }
    }

    /**
     * Updates the stored contact list-data using the ContactListUpdate's parameters.
     * @param clu ContactListUpdate containing a user and it's contact-list.
     */
    public void incomingContactListUpdate(ContactListUpdate clu) {
        clu.reachedServer();

        User user = clu.getUser();

        contactLists.put(user, clu.getContacts());
        ContactFileManager.writeContactLists(contactLists);

        sendContactListUpdate(user);
    }

    /**
     * Sends the current contact list-data to a user.
     * @param user User-info to send contact-list to.
     */
    private void sendContactListUpdate(User user){
        if(contactLists.containsKey(user)){
            if(connectedUsers.containsKey(user)){
                ContactListUpdate clu = new ContactListUpdate(user, contactLists.get(user));
                connectedUsers.get(user).addToOutput(clu);
            }
        }
    }

    /**
     * Separate thread responsible for listening and accepting new connections to the server.
     * Creates a ClientConnection for each new connection.
     */
    class ConnectionListener extends Thread {

        private ServerSocket serverSocket;

        private final ServerController controller;

        public ConnectionListener(int port, ServerController controller){
            this.controller = controller;

            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            //System.out.println("Server started!");


            while (!isInterrupted()){
                try {
                    Socket socket = serverSocket.accept();
                    socket.setSoTimeout(connectionTimeout);
                    System.out.println("--New connection incoming--");
                    new ClientConnection(socket, controller);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new ServerController(2343, 15000);
    }
}
