/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

import java.io.Serializable;

public interface IMessage extends Serializable {
    public void setReachedServerTime();
    public void setReachedRecipientTime();
    public void getReachedServerTime();
    public void getReachedRecipientTime();
}
