/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Server.Controller;

import Model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * A TCP-Connection representing the servers connection to a client.
 * Responsible for transfering messages between its Input/Output and the ServerController.
 */
public class ClientConnection {
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    private Socket socket;
    private ServerController controller;

    private OutputToClient output;
    private InputFromClient input;

    private Buffer<Message> inputBuffer = new Buffer<>();
    private Buffer<Message> outputBuffer = new Buffer<>();

    /**
     * Establishes a TCP-connection using two threads - one for input, one for output.
     * @param socket Socket to read from/write to.
     * @param controller Reference to the ServerController.
     */
    public ClientConnection(Socket socket, ServerController controller) {
        this.socket = socket;
        this.controller = controller;

        output = new OutputToClient(socket);
        input = new InputFromClient(socket);

        System.out.println("Client created: " + socket);
    }

    /**
     * Places a Message-object into the output buffer.
     * @param message Message to be sent.
     */
    public void addToOutput(Message message){
        outputBuffer.put(message);
    }

    /**
     * Drops the output connection and notifies the controller that this connection has been dropped.
     * Called when input is timed out.
     */
    private void inputConnectionDropped(){
        output.interrupt();
        controller.removeConnection(this);
        Logger.logUserStatus(user.getUsername(), "offline");
    }

    /**
     * Notifies the server that this connection has been established.
     * Called when input receives it's first message, containing the User-info of the connected user.
     * @param newUser User-info of the connected user.
     */
    private void notifyConnectionEstablished(User newUser){
        setUser(newUser);
        controller.addConnection(this, newUser);
    }

    /**
     * Thread responsible for listening to the client and forward messages to the ServerController.
     */
    class InputFromClient extends Thread {
        private final Socket socket;
        private ObjectInputStream ois;

        public InputFromClient(Socket socket){
            this.socket = socket;
            start();
        }

        /**
         * First listens for a Message containing the connected users login-information.
         * Then keeps listening for any other messages from the client.
         */
        @Override
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());

                ChatMessage chatMessage = (ChatMessage) ois.readObject();
                User newUser = chatMessage.getSender();
                notifyConnectionEstablished(newUser);
                Logger.logUserStatus(newUser.getUsername(), "online");

                System.out.println("Input thread processed UserInfo");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            while (!isInterrupted()) {
                System.out.println("Input running.");

                try {
                    Message msg = (Message) ois.readObject();
                    if(msg instanceof ChatMessage chatMessage){
                        System.out.println("From Client: " + chatMessage.toDebugString());
                        Logger.logChatMessage(chatMessage);
                        controller.incomingChatMessage(chatMessage);
                    }
                    else if (msg instanceof ContactListUpdate contactListUpdate){
                        System.out.println("Contact List update incomming..");
                        Logger.log(String.format("Recieved ContactList update from: %s",
                                contactListUpdate.getUser().getUsername()));
                        controller.incomingContactListUpdate(contactListUpdate);
                    }
                } catch (SocketException e){
                    System.out.println("SocketException: Timing out client!");
                    try {
                        socket.close();
                        interrupt();
                    } catch (IOException e2) {
                        e.printStackTrace();
                        e2.printStackTrace();
                    }
                } catch (SocketTimeoutException e){
                    System.out.println("SocketTimeoutException: Timing out client!");
                    try {
                        socket.close();
                        interrupt();
                    } catch (IOException e2) {
                        e.printStackTrace();
                        e2.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            inputConnectionDropped();
        }
    }

    /**
     * Thread responsible for sending messages to the client.
     */
    class OutputToClient extends Thread{
        private final Socket socket;
        private ObjectOutputStream ous;

        public OutputToClient(Socket socket){
            this.socket = socket;
            start();
        }

        @Override
        public void run() {
            try {
                ous = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Output running.");
            while (!isInterrupted()){
                try {
                    Message msg = outputBuffer.get();
                    ous.writeObject(msg);
                    ous.flush();

                    if(msg instanceof ChatMessage cm){
                        Logger.log("Sending ChatMessage to " + getUser().getUsername());
                    }
                    if(msg instanceof ContactListUpdate clu){
                        Logger.log("Sending ContactListUpdate to " + getUser().getUsername());
                    }
                    if(msg instanceof ServerUpdate su){
                        Logger.log("Sending ServerUpdate to " + getUser().getUsername());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("OutputToClient interrupted");
        }
    }
}
