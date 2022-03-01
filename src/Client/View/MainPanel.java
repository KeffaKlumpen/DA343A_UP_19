/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private ClientController controller;
    private int width, height;

    private NorthPanel northPanel;
    private CenterPanel centerPanel;
    private SouthPanel southPanel;

    public MainPanel(ClientController controller, int width, int height) {
        this.controller = controller;
        this.width = width;
        this.height = height;

        addComponents();
    }

    public void addComponents(){
        setLayout(new BorderLayout());

        northPanel = new NorthPanel(controller, width, height / 10);
        add(northPanel, BorderLayout.NORTH);

        centerPanel = new CenterPanel(controller, width, height / 10 * 8);
        add(centerPanel, BorderLayout.CENTER);

        southPanel = new SouthPanel(controller, width, height / 10);
        add(southPanel, BorderLayout.SOUTH);
    }

    public CenterPanel getCenterPanel() {
        return centerPanel;
    }
    public SouthPanel getSouthPanel() {
        return southPanel;
    }
}
