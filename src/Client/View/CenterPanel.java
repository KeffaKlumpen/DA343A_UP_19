/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    private ClientController controller;

    private JTextField tfMessageViewer;

    private ContactPanel contactPanel;

    public CenterPanel(ClientController controller, int width, int height) {
        this.controller = controller;

        tfMessageViewer = new JTextField();
        tfMessageViewer.setPreferredSize(new Dimension(width / 3 * 2, height));
        add(tfMessageViewer);

        contactPanel = new ContactPanel(width / 3, height);
        add(contactPanel);
    }

    public JTextField getTfMessageViewer() {
        return tfMessageViewer;
    }
    public ContactPanel getContactPanel() {
        return contactPanel;
    }
}
