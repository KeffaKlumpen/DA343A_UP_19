/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ChatMessage extends Message {
    private String msgText;
    private ImageIcon msgIcon;

    private User sender;
    private User[] recipients;

    public ChatMessage(User sender, User[] recipients, String msgText, ImageIcon msgIcon){
        this.sender = sender;
        this.recipients = recipients;
        this.msgText = msgText;
        this.msgIcon = msgIcon;
    }
    public ChatMessage(User sender, User[] recipients, String msgText){
        this(sender, recipients, msgText, null);
    }
    public ChatMessage(User sender, User[] recipients, ImageIcon msgIcon){
        this(sender, recipients, null, msgIcon);
    }
    public ChatMessage(User sender) {
        this(sender, null, null, null);
    }

    public User getSender() {
        return sender;
    }
    public User[] getRecipients() {
        return recipients;
    }
    public void setRecipients(User[] recipients) {
        this.recipients = recipients;
    }

    public String getMsgText() {
        return msgText;
    }
    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
    public ImageIcon getMsgIcon() {
        return msgIcon;
    }
    public void setMsgIcon(ImageIcon msgIcon) {
        this.msgIcon = msgIcon;
    }

    public String toDebugString() {
        final StringBuilder sb = new StringBuilder("ChatMessage{");
        sb.append("msgText='").append(msgText).append('\'');
        sb.append(", msgIcon=").append(msgIcon);
        sb.append(", sender=").append(sender);
        sb.append(", recipients=").append(Arrays.toString(recipients));
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toString() {
        /* TODO: Only add this if the difference is > 1 minute.
            And add day/month/year information if message was sent on a different day/month/year. */
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String str = String.format("[%s] %s: %s",
                getReachedServerTime().toLocalTime().format(dtf).toString(),
                getSender().getUsername(),
                getMsgText());

        return str;
    }
}
