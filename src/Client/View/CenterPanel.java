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

    private JTextArea taMessageViewer;

    private ContactPanel contactPanel;

    public CenterPanel(ClientController controller, int width, int height) {
        this.controller = controller;

        taMessageViewer = new JTextArea();
        taMessageViewer.setPreferredSize(new Dimension(width / 3 * 2, height));
        add(taMessageViewer);

        contactPanel = new ContactPanel(width / 3, height);
        add(contactPanel);
    }

    public JTextArea getTaMessageViewer() {
        return taMessageViewer;
    }
    public ContactPanel getContactPanel() {
        return contactPanel;
    }
}
