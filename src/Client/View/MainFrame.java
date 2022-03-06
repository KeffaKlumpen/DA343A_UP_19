/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private ClientController controller;

    private MainPanel mainPanel;

    public MainFrame(ClientController controller, int width, int height){
        super("Client Window");

        this.controller = controller;

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new MainPanel(controller, width, height);
        setContentPane(mainPanel);

        pack();
        setResizable(false);
        setVisible(true);
    }

    public void setUserName(String text){
        mainPanel.getSouthPanel().getLblMyUserName().setText(text);
    }
    public void setUserIcon(ImageIcon icon) {
        JButton btn = mainPanel.getSouthPanel().getBtnMyImageIcon();
        btn.setIcon(new ImageIcon(
                icon.getImage().getScaledInstance(btn.getWidth(), btn.getHeight(), Image.SCALE_FAST))
        );

        btn.repaint();
    }

    public void setMessageIcon(ImageIcon icon){
        JButton btn = mainPanel.getSouthPanel().getBtnMyImageIcon();
        mainPanel.getSouthPanel().getBtnMessageIcon().setIcon(new ImageIcon(
                icon.getImage().getScaledInstance(btn.getWidth(), btn.getHeight(), Image.SCALE_FAST))
        );

        btn.repaint();
    }

    public String getMessageText(){
        return mainPanel.getSouthPanel().getTfMessageInput().getText();
    }
    public void setMessageText(String text){
        mainPanel.getSouthPanel().getTfMessageInput().setText(text);
    }
    public ImageIcon getMessageIcon() {
        System.out.println("MainFrame.getMessageIcon - PLACEHOLDER: Not the right icon being sent!");
        return new ImageIcon("files/avatars/fish-monster.png");
        // TODO: Icon cast to ImageIcon?
        //return mainPanel.getSouthPanel().getBtnMessageIcon().getIcon();
    }
    public String[] getSelectedContacts(){
        return mainPanel.getCenterPanel().getContactPanel().getContacts().getSelectedValuesList().toArray(new String[0]);
    }
    public String[] getSelectedConnectedUsers(){
        return mainPanel.getCenterPanel().getContactPanel().getConnectedUsers().getSelectedValuesList().toArray(new String[0]);
    }
    public String[] getMessageRecipients(){
        List<String> connectedUsersSelected = mainPanel.getCenterPanel().getContactPanel().getConnectedUsers().getSelectedValuesList();
        List<String> contactsSelected = mainPanel.getCenterPanel().getContactPanel().getContacts().getSelectedValuesList();

        ArrayList<String> recipients = new ArrayList<>(connectedUsersSelected);

        for (String user : contactsSelected) {
            if(!recipients.contains(user)){
                recipients.add(user);
            }
        }

        return recipients.toArray(new String[0]);
    }

    public void setConnectedUsers(String[] usernames){
        DefaultListModel<String> listModel = mainPanel.getCenterPanel().getContactPanel().getConnectedUsersListModel();

        listModel.clear();

        for (String username : usernames) {
            listModel.addElement(username);
        }

        mainPanel.getCenterPanel().getContactPanel().getConnectedUsers().setSelectedIndex(0);
    }
    public void setContacts(String[] usernames){
        DefaultListModel<String> listModel = mainPanel.getCenterPanel().getContactPanel().getContactsListModel();

        listModel.clear();

        for (String username : usernames) {
            listModel.addElement(username);
        }

        mainPanel.getCenterPanel().getContactPanel().getContacts().setSelectedIndex(0);
    }

    public void addChatMessage(String chatMessage){
        mainPanel.getCenterPanel().getTaMessageViewer().append("\n" + chatMessage);
    }

    public void updateStatusForContacts(){
        mainPanel.getCenterPanel().getContactPanel().updateStatusForContacts();
    }
}
