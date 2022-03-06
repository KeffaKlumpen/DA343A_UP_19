/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class ContactPanel extends JPanel {
    private JList<String> contacts;
    private DefaultListModel<String> contactsListModel;
    private JList<String> connectedUsers;
    private DefaultListModel<String> connectedUsersListModel;
    private JLabel status;
    private JLabel status2;
    private JLabel status3;
    private JPanel contactsP;
    private JPanel statusPanel;

    public ContactPanel(int width, int height, ClientController controller) {
        setLayout(new BorderLayout());

        connectedUsersListModel = new DefaultListModel<>();
        connectedUsers = new JList<>(connectedUsersListModel);
        JScrollPane scrollerConnected = new JScrollPane(connectedUsers);
        scrollerConnected.setPreferredSize(new Dimension(width, height / 11 * 5));
        add(scrollerConnected, BorderLayout.NORTH);

        ContactButtons contactButtons = new ContactButtons(width, height / 11, controller);
        add(contactButtons, BorderLayout.CENTER);

        //Philip
        contactsP = new JPanel();
        contactsP.setLayout(new BorderLayout());
        contactsListModel = new DefaultListModel<>();
        contacts = new JList<>(contactsListModel);
        JScrollPane scrollerContacts = new JScrollPane(contacts);
        scrollerContacts.setPreferredSize(new Dimension(width, height / 11 * 5));
        contactsP.add(scrollerContacts, BorderLayout.CENTER);
        add(contactsP, BorderLayout.SOUTH);

        statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.PAGE_AXIS));

        statusPanel.add(Box.createRigidArea(new Dimension(0,3)));
        ImageIcon icon = new ImageIcon("files/status/online.png");
        status = new JLabel(resizeStatusIcon(icon));
        statusPanel.add(status);

        statusPanel.add(Box.createRigidArea(new Dimension(0,3)));
        ImageIcon icon2 = new ImageIcon("files/status/offline.png");
        status2 = new JLabel(resizeStatusIcon(icon2));
        statusPanel.add(status2);

        statusPanel.add(Box.createRigidArea(new Dimension(0,3)));
        ImageIcon icon3 = new ImageIcon("files/status/away.png");
        status3 = new JLabel(resizeStatusIcon(icon3));
        statusPanel.add(status3);

        contactsP.add(statusPanel, BorderLayout.WEST);





    }

    public JList<String> getContacts() {
        return contacts;
    }
    public DefaultListModel<String> getContactsListModel() {
        return contactsListModel;
    }
    public JList<String> getConnectedUsers() {
        return connectedUsers;
    }
    public DefaultListModel<String> getConnectedUsersListModel() {
        return connectedUsersListModel;
    }

    private Icon resizeStatusIcon(ImageIcon icon){
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImg);
        return newIcon;
    }
}
