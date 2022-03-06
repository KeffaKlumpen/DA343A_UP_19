package Server.View;
import Server.Controller.ServerController;
import javax.swing.*;

public class MainFrame extends JFrame {
    private int width = 600;
    private int height = 600;
    private MainPanel mainPanel;
    private ServerController controller;

    public MainFrame(ServerController controller){
        setUpframe();
        this.controller = controller;
    }

    public void setUpframe(){
        final int offsetX = width/5;
        final int offsetY = height/5;

        setSize(width, height);
        setTitle("Server Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(offsetX, offsetY);

        mainPanel = new MainPanel(controller ,width, height);
        setContentPane(mainPanel);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
