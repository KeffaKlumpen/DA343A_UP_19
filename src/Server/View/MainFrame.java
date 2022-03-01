/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;

public class MainFrame extends JFrame {
    private ServerController controller;

    public MainFrame(ServerController controller) {
        super("Server Window");
        this.controller = controller;
    }
}
