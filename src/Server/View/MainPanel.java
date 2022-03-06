package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class MainPanel extends JPanel {
    private int width;
    private int height;
    private SouthPanel southPanel;
    private CenterPanel centerPanel;
    private BorderLayout layout;
    private ServerController controller;

    public MainPanel(ServerController controller, int width, int height){
        this.width = width;
        this.height = height;
        this.controller = controller;
        setUpPanel();
    }

    public void setUpPanel(){
        layout = new BorderLayout();
        setLayout(layout);

        Border border = this.getBorder();
        Border margin = BorderFactory.createEmptyBorder(6,6,6,6);
        setBorder(new CompoundBorder(border, margin));

        centerPanel = new CenterPanel(controller,6*width/10, (8*height/10)-100, 6);
        southPanel = new SouthPanel(centerPanel,controller, width,(height/8)+100, 6);
        add(southPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

    }
}
