/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

public class ServerUpdate implements IMessage {
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
        final StringBuilder sb = new StringBuilder("ServerUpdate{");
        sb.append("newUser=").append(newUser);
        sb.append(", currentlyConnectedUsers=").append(currentlyConnectedUsers.length);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void setReachedServerTime() {

    }
    @Override
    public void setReachedRecipientTime() {

    }
    @Override
    public void getReachedServerTime() {

    }
    @Override
    public void getReachedRecipientTime() {

    }
}
