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
    private final ChatMessagePanel messagePanel;
    private final ContactPanel contactPanel;

    public CenterPanel(ClientController controller, int width, int height) {
        messagePanel = new ChatMessagePanel(width / 3 * 2, height, controller);
        add(messagePanel);

        contactPanel = new ContactPanel(width / 3, height, controller);
        add(contactPanel);
    }

    public ChatMessagePanel getMessagePanel() {
        return messagePanel;
    }
    public ContactPanel getContactPanel() {
        return contactPanel;
    }
}
