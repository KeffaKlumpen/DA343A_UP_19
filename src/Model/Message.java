/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Message implements Serializable {
    private ZonedDateTime reachedServerTime;
    private ZonedDateTime reachedRecipientTime;

    private void setReachedServerTime(ZonedDateTime reachedServerTime){
        this.reachedServerTime = reachedServerTime;
    }
    private void setReachedRecipientTime(ZonedDateTime reachedRecipientTime){
        this.reachedRecipientTime = reachedRecipientTime;
    }

    public ZonedDateTime getReachedServerTime(){
        return reachedServerTime;
    }
    public ZonedDateTime getReachedRecipientTime(){
        return reachedRecipientTime;
    }

    public void reachedServer(){
        assert reachedRecipientTime == null;
        setReachedServerTime(ZonedDateTime.now());
    }

    public void reachedRecipient(){
        assert reachedRecipientTime == null;
        setReachedRecipientTime(ZonedDateTime.now());
    }
}
