/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;

public class NorthPanel extends JPanel {
    private ClientController controller;

    public NorthPanel(ClientController controller, int width, int height){
        JButton btnReconnect = new JButton("Reconnect To Server");
        btnReconnect.addActionListener(e -> {
            controller.reconnectToServer();
        });
        add(btnReconnect);
    }
}
