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

public class ServerController {

    private Hashtable<User, ClientConnection> connectedUsers = new Hashtable<>();

    private Hashtable<User, Buffer<IMessage>> unsentMessages = new Hashtable<>();

    private static int connectionTimeout;

    public ServerController(int port, int connectionTimeout){
        ServerController.connectionTimeout = connectionTimeout;
        new ConnectionListener(port, this).start();
    }
    public ServerController(int port){
        this(port, 5000);
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

    public void incommingChatMessage(ChatMessage cm){
        System.out.println("handleClientMessage() NOT IMPLEMENTED!");
        // Add to unsentMessages buffer<>...
        // broadcast() -> send to all recipients + sender... How to handle offline..
        // If any recipient or sender offline: Create new message with offline clients as recipients, and add to unsentBuffer..
        //
        // unsent = HashMap <User, Buffer<ChatMessage>
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

    class ChatMessageConsumer extends Thread {

    }

    public static void main(String[] args) {
        new ServerController(2343, 15000);
    }
}
