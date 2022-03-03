/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

public class ContactListUpdate extends Message {
    private User user;
    private User[] contacts;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public User[] getContacts() {
        return contacts;
    }
    public void setContacts(User[] contacts) {
        this.contacts = contacts;
    }

    public ContactListUpdate(User user, User[] contacts) {
        this.user = user;
        this.contacts = contacts;
    }
}
