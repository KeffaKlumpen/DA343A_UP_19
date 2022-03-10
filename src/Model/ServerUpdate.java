/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

public class ServerUpdate extends Message {
    private User newUser;
    private User[] currentlyConnectedUsers;

    /**
     * Use this for when a user disconnects rather than connects.
     * This is represented by leaving newUser as null.
     * @param currentlyConnectedUsers Currently connected users
     */
    public ServerUpdate(User[] currentlyConnectedUsers) {
        this(currentlyConnectedUsers, null);
    }

    public ServerUpdate(User[] currentlyConnectedUsers, User newUser) {
        this.newUser = newUser;
        this.currentlyConnectedUsers = currentlyConnectedUsers;
    }

    public User getNewUser() {
        return newUser;
    }
    public User[] getCurrentlyConnectedUsers() {
        return currentlyConnectedUsers;
    }

    @Override
    public String toString() {
        String sb = "ServerUpdate{" + "newUser=" + newUser +
                ", currentlyConnectedUsers=" + currentlyConnectedUsers.length +
                '}';
        return sb;
    }

}
