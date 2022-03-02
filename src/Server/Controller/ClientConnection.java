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

public class ClientConnection {
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    private Socket socket;
    private ServerController controller;

    private OutputToClient output;
    private InputFromClient input;

    private Buffer<Message> inputBuffer = new Buffer<>();
    private Buffer<Message> outputBuffer = new Buffer<>();

    public ClientConnection(Socket socket, ServerController controller) {
        this.socket = socket;
        this.controller = controller;
        System.out.println("ClientConnection var: " + controller);

        output = new OutputToClient(socket);
        input = new InputFromClient(socket);

        System.out.println("Client created: " + socket);
    }

    public void addToOutput(Message message){
        outputBuffer.put(message);
    }

    private void inputConnectionDropped(){
        output.interrupt();
        controller.removeConnection(this);
    }

    private void notifyConnectionEstablished(User newUser){
        setUser(newUser);
        controller.addConnection(this, newUser);
    }

    class InputFromClient extends Thread {
        private final Socket socket;
        private ObjectInputStream ois;

        public InputFromClient(Socket socket){
            this.socket = socket;
            start();
        }

        @Override
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());

                ChatMessage chatMessage = (ChatMessage) ois.readObject();
                User newUser = chatMessage.getSender();

                notifyConnectionEstablished(newUser);

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
                        controller.incommingChatMessage(chatMessage);
                    }
                    else {
                        // ignoring Pings for now..
                    }
                } catch (SocketException e){
                    System.out.println("Timing out client!");
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
                    Serializable o = outputBuffer.get();
                    ous.writeObject(o);
                    ous.flush();
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
