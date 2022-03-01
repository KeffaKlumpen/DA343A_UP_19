/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {

    private JTextField tfUsername;
    private JTextField tfServerName;
    private JTextField tfServerPort;
    private JLabel lbUsername;
    private JLabel lbServerName;
    private JLabel lbServerport;
    private JButton btnLogin;
    private JButton btnCancel;

    private String imagePath = "files/avatars/ninja-head.png";

    public boolean confirmed() {
        return confirm;
    }

    private boolean confirm;

    public LoginDialog(Frame parent){
        super(parent, "LoginDialog", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        tfUsername.setText("joel1337");
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbServerName = new JLabel("Server IP: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbServerName, cs);

        tfServerName = new JTextField(20);
        tfServerName.setText("127.0.0.1");
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfServerName, cs);

        lbServerport = new JLabel("Server Port: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbServerport, cs);

        tfServerPort = new JTextField(20);
        tfServerPort.setText("2343");
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tfServerPort, cs);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            confirm = true;
            dispose();
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener( e -> {
            confirm = false;
            dispose();
        });

        JButton btnIconChooser = new JButton("Choose Icon");
        btnIconChooser.addActionListener( e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setCurrentDirectory(new java.io.File("./files/avatars"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & PNG Images", "jpg", "png");
            fileChooser.setFileFilter(filter);

            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                imagePath = fileChooser.getSelectedFile().getPath();
            }
        });

        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);
        bp.add(btnIconChooser);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getServername(){
        return tfServerName.getText();
    }

    public int getServerport(){
        return Integer.parseInt(tfServerPort.getText());
    }

    public String getImagePath(){
        return imagePath;
    }
}
