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

/**
 * Main entry for the client side program.
 * Handles execution flow and traffic between the ServerConnection and MainFrame (UI).
 */
public class ClientController {

    public User getMyLogin() {
        return myLogin;
    }

    private User myLogin;
    private MainFrame view;
    private ServerConnection connection;

    /**
     * Prompts the user with a loginDialog, then connects to the server and sets up the main UI.
     */
    public ClientController(){
        LoginDialog loginDialog = new LoginDialog(new Frame());
        loginDialog.setVisible(true);

        if(loginDialog.confirmed()){
            String username = loginDialog.getUsername();
            String imagePath = loginDialog.getImagePath();
            myLogin = new User(username, new ImageIcon(imagePath));

            view = new MainFrame(this, 800, 600);

            System.out.println(myLogin);

            String serverIp = loginDialog.getServername();
            int serverPort = loginDialog.getServerport();
            connectToServer(serverIp, serverPort);

            System.out.println(connection);

            view.setUserName(myLogin.getUsername());
            view.setUserIcon(myLogin.getImageIcon());
        }
        else {
            // Process doesn't terminate normally after we use LoginDialog...
            // TODO: Make us able to start the client even if the server connection isn't working.
            System.exit(0);
        }
    }

    /**
     * Establishes a connection to a server.
     * @param ip Ip-adress of the server.
     * @param port Port to connect to.
     */
    public void connectToServer(String ip, int port){
        connection = new ServerConnection(ip, port, this);
    }

    /**
     * Re-establishes an already created connection.
     * Used in the event of a dropped connection.
     */
    public void reconnectToServer(){
        // TODO: Only show this button when we've timed-out...
        connection = new ServerConnection(connection.getIp(), connection.getPort(), this);
    }

    /**
     * Manually drop the connection to the server.
     */
    public void disconnectFromServer(){
        // TODO: Lol, no way this is good.
        connection.interrupt();
    }
    public void selectServer(){
        System.out.println("SELECT SERVER NOT IMPLEMENTED.");

        // Prompt user with server details..

        //disconnectFromServer();
        //connectToServer(newIp, newPort);
    }

    /**
     * ButtonPress call-back.
     * Generate a ChatMessage object using the UI data, then sends it to the ServerConnection.
     */
    public void sendChatMessage(){
        if(connection != null){
            String msgText = view.getMessageText();
            ImageIcon msgIcon = view.getMessageIcon();
            String[] recipientNames = view.getMessageRecipients();
            User[] recipients = User.userListFromStrings(recipientNames, new ImageIcon("files/avatars/troll.png"));

            ChatMessage msg = new ChatMessage(myLogin, recipients, msgText, msgIcon);

            if(!msgText.equals("") || msgIcon != null){
                if(recipients.length > 0){
                    connection.sendMessage(msg);
                    view.setMessageText("");
                    view.setMessageIcon(null);
                }
            }
        }
    }

    /**
     * ButtonPress call-back.
     * Allows the user to select an image to use as their new user icon.
     */
    public void changeUserIcon(){
        System.out.println("Change Icon Pressed");

        ImageIcon icon = getImageIcon("avatars");
        myLogin.setImageIcon(icon);
        view.setUserIcon(myLogin.getImageIcon());
    }

    /**
     * ButtonPress call-back.
     * Allows the user to select an image to be sent as part of a ChatMessage.
     */
    public void selectMessageIcon(){
        view.setMessageIcon(getImageIcon("emoji"));
    }

    /**
     * Helper function to display a FileChooser, allowing the user to select an image.
     * @param subFolder name if the subfolder within ./files/ to view.
     * @return A generated ImageIcon based on the file selected. Returns null if invalid path chosen.
     */
    private ImageIcon getImageIcon(String subFolder){
        String path = "";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setCurrentDirectory(new java.io.File("./files/" + subFolder));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(new JFrame());
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getPath();
        }

        return path != "" ? new ImageIcon(path) : null;
    }

    // TODO: Forward this to a ContactManager class?
    private ArrayList<String> contacts = new ArrayList<>();

    /**
     * ButtonPress call-back.
     * Adds the selected connected users to contacts and notifies the server of the change.
     */
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
    /**
     * ButtonPress call-back.
     * Removes the selected users from contacts and notifies the server of the change.
     */
    public void removeContacts(){
        String[] selectedContacts = view.getSelectedContacts();
        for (String user : selectedContacts) {
            contacts.remove(user);
        }
        // notify server of my new contacts...
        User[] userContacts = User.userListFromStrings(contacts.toArray(new String[0]), new ImageIcon("files/avatars/troll.png"));
        connection.sendMessage(new ContactListUpdate(myLogin, userContacts));
    }

    /**
     * Notifies the UI of the currently connected users
     * @param serverUpdate ServerUpdate recieved.
     */
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
    /**
     * Adds the recieved ChatMessage to the UI.
     * @param chatMessage ChatMessage recieved.
     */
    public void handleChatMessage(ChatMessage chatMessage){
        chatMessage.reachedRecipient();

        view.addChatMessage(chatMessage.toString(), chatMessage.getMsgIcon());

        System.out.println(myLogin.getUsername() + " Adding: " + chatMessage);
    }

    /**
     * Updates our contact list and updates the UI to reflect the change.
     * @param contactListUpdate ContactListUpdate recieved.
     */
    public void handleContactListUpdate(ContactListUpdate contactListUpdate){
        contactListUpdate.reachedRecipient();

        User[] userContacts = contactListUpdate.getContacts();
        ArrayList<String> contactNames = new ArrayList<>();
        for (int i = 0; i < userContacts.length; i++) {
            contactNames.add(userContacts[i].getUsername());
        }

        contacts = contactNames;
        view.setContacts(contacts.toArray(new String[0]));
        view.updateStatusForContacts();
    }

    public static void main(String[] args) {
        new ClientController();
        //new ClientController();
        //new ClientController();

        //new ClientController("realUser", new ImageIcon("files/avatars/monk-face.png"));
        //new ClientController("trollololol", new ImageIcon("files/avatars/troll.png"));
    }
}
