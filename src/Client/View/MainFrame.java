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
    public ImageIcon getMessageIcon() {
        System.out.println("MainFrame.getMessageIcon - PLACEHOLDER: Not the right icon being sent!");
        return new ImageIcon("files/avatars/fish-monster.png");
        // TODO: Icon cast to ImageIcon?
        //return mainPanel.getSouthPanel().getBtnMessageIcon().getIcon();
    }

    public String[] getMessageRecipients(){
        mainPanel.getCenterPanel().getContactPanel().getConnectedUsers().getSelectedValuesList();
        mainPanel.getCenterPanel().getContactPanel().getContacts().getSelectedValuesList();

        // TODO: Selected contacts + selected online -- REMOVE DUPLICATE in controller?

        String[] recipients = new String[0];

        return recipients;
    }

    public void updateConnectedUsers(String[] usernames){
        DefaultListModel<String> listModel = mainPanel.getCenterPanel().getContactPanel().getConnectedUsersListModel();

        listModel.clear();

        for (String username : usernames) {
            listModel.addElement(username);
        }

        mainPanel.getCenterPanel().getContactPanel().getConnectedUsers().setSelectedIndex(0);
    }
}
