/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Server.Controller;

import Model.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedList;

public class ServerController {

    private Hashtable<User, ClientConnection> connectedUsers = new Hashtable<>();
    private Hashtable<User, LinkedList<Message>> unsentMessageBuffers = new Hashtable<>();
    private Hashtable<User, User[]> contactLists = new Hashtable<>();

    private static int connectionTimeout;

    public ServerController(int port){
        this(port, 5000);
    }
    public ServerController(int port, int connectionTimeout){
        ServerController.connectionTimeout = connectionTimeout;
        new ConnectionListener(port, this).start();
        // start CLI etc.
    }

    public synchronized void addConnection(ClientConnection clientConnection, User user){
        System.out.println("Adding connection: " + clientConnection);

        ClientConnection old = connectedUsers.put(user, clientConnection);
        if(old != null){
            System.out.println("Removing old connection..");
            old = null;
        }
        broadcastServerUpdate(user);

        // TODO: Load from file: users contactList.
        sendContactListUpdate(user);

        sendUnsentMessages(user);

        System.out.println(connectedUsers);
    }
    public synchronized void removeConnection(ClientConnection clientConnection){
        System.out.println("Removing connection: " + clientConnection);
        connectedUsers.remove(clientConnection.getUser());
        broadcastServerUpdate(null);

        System.out.println(connectedUsers);
    }

    public synchronized void broadcastServerUpdate(User newUser){
        System.out.println("Broadcasting Server Update");
        for (ClientConnection con: connectedUsers.values()) {
            ServerUpdate update = new ServerUpdate(connectedUsers.keySet().toArray(new User[0]), newUser);
            con.addToOutput(update);
        }
    }

    // Either put the message to the ClientConnections output, or in the unsentMessages buffer.
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

    public void incomingContactListUpdate(ContactListUpdate clu) {
        clu.reachedServer();

        User user = clu.getUser();

        contactLists.put(user, clu.getContacts());
        // TODO: Write to file..

        sendContactListUpdate(user);
    }
    private void sendContactListUpdate(User user){
        if(contactLists.containsKey(user)){
            if(connectedUsers.containsKey(user)){
                ContactListUpdate clu = new ContactListUpdate(user, contactLists.get(user));
                connectedUsers.get(user).addToOutput(clu);
            }
        }
    }

    class ConnectionListener extends Thread {

        private ServerSocket serverSocket;

        private ServerController controller;

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
            System.out.println("Server started!");

            while (!isInterrupted()){
                try {
                    Socket socket = serverSocket.accept();
                    socket.setSoTimeout(connectionTimeout);
                    System.out.println("--New connection incomming--");
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
