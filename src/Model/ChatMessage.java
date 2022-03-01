/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

import javax.swing.*;
import java.io.Serializable;

public class ChatMessage implements IMessage {
    private String msgText;
    private ImageIcon msgIcon;

    private User sender;
    private String[] recipients;

    public ChatMessage(User sender, String[] recipients, String msgText, ImageIcon msgIcon){
        this.sender = sender;
        this.recipients = recipients;
        this.msgText = msgText;
        this.msgIcon = msgIcon;
    }
    public ChatMessage(User sender, String[] recipients, String msgText){
        this(sender, recipients, msgText, null);
    }
    public ChatMessage(User sender, String[] recipients, ImageIcon msgIcon){
        this(sender, recipients, null, msgIcon);
    }
    public ChatMessage(User sender) {
        this(sender, null, null, null);
    }

    public User getSender() {
        return sender;
    }
    public String[] getRecipients() {
        return recipients;
    }
    public void setRecipients(String[] recipients) {
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

    @Override
    public void setReachedServerTime(){
        System.out.println("setReachedServerTime() NOT IMPLEMENTED");
    }
    @Override
    public void setReachedRecipientTime(){
        System.out.println("setReachedRecipientTime() NOT IMPLEMENTED");
    }
    @Override
    public void getReachedServerTime() {

    }
    @Override
    public void getReachedRecipientTime() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("msgText='").append(msgText).append('\'');
        sb.append(", msgIcon=").append(msgIcon);
        sb.append('}');
        return sb.toString();
    }
}
