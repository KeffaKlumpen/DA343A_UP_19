/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.View;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class ChatMessagePanel extends JPanel {

    private final int width;
    private final int height;
    private final ClientController controller;

    private DefaultListModel<ChatListItem> listModel;
    private JList<ChatListItem> list;

    public ChatMessagePanel(int width, int height, ClientController controller){
        this.width = width;
        this.height = height;
        this.controller = controller;

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setCellRenderer(new ChatMessageRenderer(width, 35));

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(width, height));

        add(scrollPane);
    }

    public void addMessage(String text, ImageIcon imageIcon){
        ChatListItem listItem = new ChatListItem(text, imageIcon);
        listModel.addElement(listItem);
    }

    public static class ChatListItem {
        private final String msgText;
        private final ImageIcon msgIcon;

        public ChatListItem(String msgText, ImageIcon msgIcon){
            this.msgText = msgText;
            this.msgIcon = msgIcon;
        }

        public String getMsgText() {
            return msgText;
        }
        public ImageIcon getMsgIcon() {
            return msgIcon;
        }
    }

    public class ChatMessageRenderer implements ListCellRenderer<ChatListItem> {
        private final JPanel panel = new JPanel();
        private final JLabel lblText = new JLabel();
        private final JLabel lblImage = new JLabel();

        private final int width;
        private final int height;

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

            lblText.setText(chatListItem.getMsgText());

            // TODO: How to make the panel re-size if we have no ImageIcon? - setPreferredSize() didn't work..
            if(chatListItem.getMsgIcon() == null){
                lblImage.setIcon(null);
            }
            else {
                lblImage.setIcon(new ImageIcon(chatListItem.getMsgIcon().getImage().getScaledInstance(height, height, Image.SCALE_DEFAULT)));
            }

            return panel;
        }
    }
}
