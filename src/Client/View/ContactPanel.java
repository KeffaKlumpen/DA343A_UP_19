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

    public ContactPanel(int width, int height, ClientController controller) {
        setLayout(new GridLayout(3, 1));

        connectedUsersListModel = new DefaultListModel<>();
        connectedUsers = new JList<>(connectedUsersListModel);

        JScrollPane scrollPane = new JScrollPane(connectedUsers);
        scrollPane.setPreferredSize(new Dimension(width, height / 11 * 5));
        add(scrollPane);

        // TODO: Dumma GridLayout gör dom lika stora.. BorderLayout?
        ContactButtons contactButtons = new ContactButtons(width, height / 11, controller);
        add(contactButtons);

        contactsListModel = new DefaultListModel<>();
        contacts = new JList<>(contactsListModel);

        JScrollPane scrollPane2 = new JScrollPane(contacts);
        scrollPane2.setPreferredSize(new Dimension(width, height / 11 * 5));
        add(scrollPane2);
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
}
