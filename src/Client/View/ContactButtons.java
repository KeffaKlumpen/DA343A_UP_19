/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class ContactButtons extends JPanel {
    public ContactButtons (int width, int height, ClientController controller){
        JButton btnAddContact = new JButton("Add Contact");
        btnAddContact.setPreferredSize(new Dimension(width / 2, height));
        btnAddContact.addActionListener( e -> controller.addContacts());
        add(btnAddContact);

        JButton btnRemoveContact = new JButton("Remove Contact");
        btnRemoveContact.setPreferredSize(new Dimension(width / 2, height));
        btnRemoveContact.addActionListener( e -> controller.removeContacts());
        add(btnRemoveContact);
    }
}
