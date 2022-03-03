package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class CenterPanel extends JPanel {

    private JList <String> list;
    private ServerController controller;

    public CenterPanel(ServerController controller, int width, int height, int margin){
        this.controller = controller;
        setBorder(BorderFactory.createTitledBorder("Server Activity"));
        Border border = this.getBorder();
        Border emptyBorder = BorderFactory.createEmptyBorder(margin,margin,margin-100,margin);
        setBorder(new CompoundBorder(border, emptyBorder));
        setPreferredSize(new Dimension(width, height));

        list = new JList(); //data has type Object[]
        Font font = new Font("Courier New", Font.PLAIN, 11);
        list.setFont(font);

        JScrollPane s = new JScrollPane(list);
        s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        s.setPreferredSize(new Dimension(width+350, height-50));
        add(s);

    }
}
