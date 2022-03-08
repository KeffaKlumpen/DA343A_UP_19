/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class SouthPanel extends JPanel {
    private ClientController controller;
    private int width, height;

    private JTextField tfMessageInput;
    private JButton btnMyImageIcon;
    private JButton btnMessageIcon;
    private JLabel lblMyUserName;

    private ImageIcon msgIcon;
    private ImageIcon userIcon;

    public SouthPanel(ClientController controller, int width, int height) {
        this.controller = controller;
        this.width = width;
        this.height = height;

        addComponents();
    }

    private void addComponents(){
        //setLayout(new FlowLayout());

        // TODO: Resize icons..?
        btnMyImageIcon = new JButton();
        btnMyImageIcon.setPreferredSize(new Dimension(height, height));
        btnMyImageIcon.addActionListener(e -> {
            controller.changeUserIcon();
        });
        add(btnMyImageIcon);

        lblMyUserName = new JLabel();
        lblMyUserName.setPreferredSize(new Dimension(250, height));
        lblMyUserName.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMyUserName);

        tfMessageInput = new JTextField();
        tfMessageInput.setPreferredSize(new Dimension(250, height));
        add(tfMessageInput);

        btnMessageIcon = new JButton();
        btnMessageIcon.setPreferredSize(new Dimension(height, height));
        btnMessageIcon.addActionListener(e -> {
            controller.selectMessageIcon();
        });
        add(btnMessageIcon);

        JButton btnSendMessage = new JButton("Send");
        btnSendMessage.setPreferredSize(new Dimension(150, height));
        btnSendMessage.addActionListener(e -> {
            controller.sendChatMessage();
        });
        add(btnSendMessage);
    }

    public JButton getBtnMyImageIcon() {
        return btnMyImageIcon;
    }
    public JLabel getLblMyUserName() {
        return lblMyUserName;
    }

    public JTextField getTfMessageInput() {
        return tfMessageInput;
    }

    public void setMessageIcon(ImageIcon icon){
        msgIcon = icon;

        if(icon != null){
            btnMessageIcon.setIcon(new ImageIcon(
                    icon.getImage().getScaledInstance(btnMessageIcon.getWidth(), btnMessageIcon.getHeight(), Image.SCALE_FAST))
            );
        }else {
            btnMessageIcon.setIcon(null);
        }


        btnMessageIcon.repaint();
    }
    public ImageIcon getMessageIcon() {
        return msgIcon;
    }
}
