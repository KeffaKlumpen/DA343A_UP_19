/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.Controller;

import Client.Model.PingProducer;
import Model.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerConnection {

    public String getIp() {
        return ip;
    }
    public int getPort() {
        return port;
    }

    private String ip;
    private int port;
    private ClientController controller;

    private InputFromServer input;
    private OutputToServer output;

    public ServerConnection(String ip, int port, ClientController controller) {
        this.ip = ip;
        this.port = port;
        this.controller = controller;

        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Client 1.");
            output = new OutputToServer(socket);
            System.out.println("Client 2.");
            input = new InputFromServer(socket);
            System.out.println("Client 3.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ServerConnection created");
        PingProducer pingProducer = new PingProducer(output.outputBuffer, 10000);
    }

    public void sendMessage(Message msg){
        output.outputBuffer.put(msg);
    }

    public void interrupt(){
        output.interrupt();
        input.interrupt();
    }

    class OutputToServer extends Thread {
        private final Socket socket;
        private ObjectOutputStream oos;

        private Buffer<Message> outputBuffer = new Buffer<>();

        public OutputToServer(Socket socket) throws IOException {
            this.socket = socket;
            start();
        }

        @Override
        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                // send first contact message
                User myUser = controller.getMyLogin();
                ChatMessage firstMsg = new ChatMessage(myUser);
                oos.writeObject(firstMsg);
                oos.flush();
                System.out.println("UserInfo sent!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!isInterrupted()){
                try {
                    Message msg = outputBuffer.get();
                    System.out.println("Sending Output From Client");
                    oos.writeObject(msg);
                    oos.flush();
                } catch (IOException e){
                    interrupt();
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    interrupt();
                    e.printStackTrace();
                }
            }

            System.out.println("OutputToServer interrupted");
        }
    }

    class InputFromServer extends Thread {
        private final Socket socket;
        private ObjectInputStream ois;

        public InputFromServer(Socket socket) throws IOException {
            this.socket = socket;
            start();
        }

        @Override
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!isInterrupted()){
                try {
                    Object o = ois.readObject();
                    if (o instanceof ChatMessage msg) {
                        controller.handleChatMessage(msg);
                    } else if (o instanceof ServerUpdate serverUpdate) {
                        controller.handleServerUpdate(serverUpdate);
                    } else if (o instanceof ContactListUpdate contactListUpdate){
                        controller.handleContactListUpdate(contactListUpdate);
                    }
                } catch (EOFException | SocketException e) {
                    interrupt();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("InputFromServer interrupted");
        }
    }

}
