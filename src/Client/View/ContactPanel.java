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
        setLayout(new BorderLayout());

        connectedUsersListModel = new DefaultListModel<>();
        connectedUsers = new JList<>(connectedUsersListModel);
        JScrollPane scrollerConnected = new JScrollPane(connectedUsers);
        scrollerConnected.setPreferredSize(new Dimension(width, height / 11 * 5));
        add(scrollerConnected, BorderLayout.NORTH);

        ContactButtons contactButtons = new ContactButtons(width, height / 11, controller);
        add(contactButtons, BorderLayout.CENTER);

        contactsListModel = new DefaultListModel<>();
        contacts = new JList<>(contactsListModel);
        JScrollPane scrollerContacts = new JScrollPane(contacts);
        scrollerContacts.setPreferredSize(new Dimension(width, height / 11 * 5));
        add(scrollerContacts, BorderLayout.SOUTH);
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
