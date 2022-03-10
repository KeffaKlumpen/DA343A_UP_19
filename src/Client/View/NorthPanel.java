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
        JButton btnDisconnect = new JButton("Disconnect From Server");
        btnDisconnect.addActionListener(e -> controller.disconnectFromServer());
        add(btnDisconnect);

        JButton btnReconnect = new JButton("Reconnect To Server");
        btnReconnect.addActionListener(e -> controller.reconnectToServer());
        add(btnReconnect);

        JButton btnSelectServer = new JButton("Select Server");
        btnSelectServer.addActionListener(e -> controller.selectServer());
        add(btnSelectServer);
    }
}
