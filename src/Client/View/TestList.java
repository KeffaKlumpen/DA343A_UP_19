/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Demonstrates how to make a JList that holds JPanels.
 */
public class TestList {

    public TestList(){
        JFrame frame = new JFrame("Testing Lists");
        JPanel panel = new JPanel();

        // Set up List with a ListModel, using a custom ListItem class <T>
        DefaultListModel<ChatListItem> listModel = new DefaultListModel<>();
        JList list = new JList(listModel);

        // Set the lists renderer to a custom ListCellRenderer<T>
        list.setCellRenderer(new ChatMessageRenderer(400, 50));

        // POPULATE LIST
        for (int i = 0; i < 20; i++) {
            ChatListItem chatListItem = new ChatListItem();
            chatListItem.itemText = "ListItem " + i;
            if(i % 4 == 0){
                chatListItem.imagePath = "files/avatars/troll.png";
            }
            listModel.addElement(chatListItem);
        }

        // Surround in a scrollPane..
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(400, 400));

        // Add to main panel
        panel.add(scrollPane);


        // boring JFrame stuff
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * A struct of data, representing one 'row' or item in the list.
     */
    public static class ChatListItem {
        private String itemText;
        private String imagePath;

        public String getItemText() {
            return itemText;
        }
        public void setItemText(String itemText) {
            this.itemText = itemText;
        }
        public String getImagePath() {
            return imagePath;
        }
        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
    }

    /**
     * Sets up a custom way to render each row in a list that uses this renderer.
     */
    public class ChatMessageRenderer implements ListCellRenderer<ChatListItem> {

        private final JPanel panel = new JPanel();
        private final JLabel lblText = new JLabel();
        private final JLabel lblImage = new JLabel();

        private int width;
        private int height;

        public ChatMessageRenderer(int width, int height) {
            this.width = width;
            this.height = height;
            panel.setPreferredSize(new Dimension(width, height));

            panel.setLayout(new BorderLayout());
            panel.add(lblText, BorderLayout.CENTER);
            panel.add(lblImage, BorderLayout.WEST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ChatListItem> list,
                                                      ChatListItem chatListItem, int index, boolean isSelected, boolean cellHasFocus) {

            // Here we bind our ListItem data to the different JLabels, buttons, etc. that we have in our panel.

            lblText.setText(chatListItem.itemText);

            if(Objects.equals(chatListItem.imagePath, "")){
                lblImage.setIcon(null);
            }
            else {
                lblImage.setIcon(new ImageIcon(new ImageIcon(chatListItem.imagePath).getImage().getScaledInstance(height, height, Image.SCALE_DEFAULT)));
            }

            return panel;
        }
    }

    public static void main(String[] args) {
        new TestList();
    }
}
