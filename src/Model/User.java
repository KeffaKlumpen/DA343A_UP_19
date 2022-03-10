/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private ImageIcon imageIcon;

    public User(String username, ImageIcon imageIcon) {
        this.username = username;
        this.imageIcon = imageIcon;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }
    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    @Override
    public int hashCode(){
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return username.equals(((User) obj).getUsername());
        }
        return false;
    }

    @Override
    public String toString() {
        String sb = "User{" + "username='" + username + '\'' +
                ", imageIcon=" + imageIcon +
                '}';
        return sb;
    }

    public static User[] userListFromStrings(String[] strings, ImageIcon defaultIcon){
        User[] users = new User[strings.length];
        for (int i = 0; i < strings.length; i++) {
            users[i] = new User(strings[i], defaultIcon);
        }

        return users;
    }
}
