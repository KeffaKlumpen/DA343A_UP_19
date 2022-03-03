/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.Controller;

import Client.View.LoginDialog;
import Client.View.MainFrame;
import Model.ChatMessage;
import Model.ContactListUpdate;
import Model.User;
import Model.ServerUpdate;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;

public class ClientController {

    public User getMyLogin() {
        return myLogin;
    }

    private User myLogin;
    private MainFrame view;
    private ServerConnection connection;

    public ClientController(){
        LoginDialog loginDialog = new LoginDialog(new Frame());
        loginDialog.setVisible(true);

        if(loginDialog.confirmed()){
            String username = loginDialog.getUsername();
            String imagePath = loginDialog.getImagePath();
            myLogin = new User(username, new ImageIcon(imagePath));

            System.out.println(myLogin);

            String serverIp = loginDialog.getServername();
            int serverPort = loginDialog.getServerport();
            connectToServer(serverIp, serverPort);

            System.out.println(connection);

            view = new MainFrame(this, 800, 600);
            view.setUserName(myLogin.getUsername());
            view.setUserIcon(myLogin.getImageIcon());
        }
        else {
            // Why do I need to do this??
            // Process doesn't terminate normally after we use LoginDialog...
            System.exit(0);
        }
    }

    public void connectToServer(String ip, int port){
        connection = new ServerConnection(ip, port, this);
    }

    // TODO: Only show this button when we've timed-out...
    public void reconnectToServer(){
        connection = new ServerConnection(connection.getIp(), connection.getPort(), this);
    }

    public void sendChatMessage(){
        // Create message, add to sendBuffer..
        System.out.println("Send Pressed");

        if(connection != null){
            String msgText = view.getMessageText();
            ImageIcon msgIcon = view.getMessageIcon();
            String[] recipientNames = view.getMessageRecipients();
            User[] recipients = User.userListFromStrings(recipientNames, new ImageIcon("files/avatars/troll.png"));

            ChatMessage msg = new ChatMessage(myLogin, recipients, msgText, msgIcon);
            connection.sendMessage(msg);

            view.setMessageText("");
        }
    }

    public void changeUserIcon(){
        System.out.println("Change Icon Pressed");

        ImageIcon icon = new ImageIcon(getIconPath());
        myLogin.setImageIcon(icon);
        view.setUserIcon(myLogin.getImageIcon());
    }

    public void selectMessageIcon(){
        view.setMessageIcon(new ImageIcon(getIconPath()));
    }

    private String getIconPath(){
        String path = "files/avatars/ninja-head.png";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setCurrentDirectory(new java.io.File("./files/avatars"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(new JFrame());
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getPath();
        }

        return path;
    }

    // TODO: Forward this to a ContactManager class?
    private ArrayList<String> contacts = new ArrayList<>();
    public void addContacts(){
        String[] selectedUsers = view.getSelectedConnectedUsers();
        for (String user : selectedUsers) {
            if(!contacts.contains(user)){
                contacts.add(user);
            }
        }

        // notify server of my new contacts...
        User[] userContacts = User.userListFromStrings(contacts.toArray(new String[0]), new ImageIcon("files/avatars/troll.png"));
        connection.sendMessage(new ContactListUpdate(myLogin, userContacts));
    }
    public void removeContacts(){
        String[] selectedContacts = view.getSelectedContacts();
        for (String user : selectedContacts) {
            contacts.remove(user);
        }

        // notify server of my new contacts...
        User[] userContacts = User.userListFromStrings(contacts.toArray(new String[0]), new ImageIcon("files/avatars/troll.png"));
        connection.sendMessage(new ContactListUpdate(myLogin, userContacts));
    }

    public void handleServerUpdate(ServerUpdate serverUpdate) {
        serverUpdate.reachedRecipient();

        User[] connectedUsers = serverUpdate.getCurrentlyConnectedUsers();
        int userCount = connectedUsers.length;
        String[] usernames = new String[userCount];

        for (int i = 0; i < userCount; i++) {
            usernames[i] = connectedUsers[i].getUsername();
        }

        view.setConnectedUsers(usernames);
    }
    public void handleChatMessage(ChatMessage chatMessage){
        chatMessage.reachedRecipient();

        // TODO: Handle chat icons..
        view.addChatMessage(chatMessage.toString());

        System.out.println(myLogin.getUsername() + " Adding: " + chatMessage);
    }
    public void handleContactListUpdate(ContactListUpdate contactListUpdate){
        contactListUpdate.reachedRecipient();

        User[] userContacts = contactListUpdate.getContacts();
        ArrayList<String> contactNames = new ArrayList<>();
        for (int i = 0; i < userContacts.length; i++) {
            contactNames.add(userContacts[i].getUsername());
        }

        contacts = contactNames;
        view.setContacts(contacts.toArray(new String[0])); // do this when the server sends me my new contacts...
    }


    public static void main(String[] args) {
        new ClientController();
        //new ClientController();
        //new ClientController();

        //new ClientController("realUser", new ImageIcon("files/avatars/monk-face.png"));
        //new ClientController("trollololol", new ImageIcon("files/avatars/troll.png"));
    }
}
