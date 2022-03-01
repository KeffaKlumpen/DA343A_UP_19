/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ContactPanel extends JPanel {
    private JList<String> contacts;
    private DefaultListModel<String> contactsListModel;
    private JList<String> connectedUsers;
    private DefaultListModel<String> connectedUsersListModel;

    public ContactPanel(int width, int height) {
        setLayout(new GridLayout(2, 1));

        connectedUsersListModel = new DefaultListModel<>();
        connectedUsers = new JList<>(connectedUsersListModel);

        JScrollPane scrollPane = new JScrollPane(connectedUsers);
        scrollPane.setPreferredSize(new Dimension(width, height / 2));
        add(scrollPane);

        contactsListModel = new DefaultListModel<>();
        contacts = new JList<>(contactsListModel);

        JScrollPane scrollPane2 = new JScrollPane(contacts);
        scrollPane2.setPreferredSize(new Dimension(width, height / 2));
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
