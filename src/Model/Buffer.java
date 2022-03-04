/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

import java.util.LinkedList;

public class Buffer <T> {
    private final LinkedList<T> buffer = new LinkedList<>();

    public synchronized void put (T obj){
        buffer.addLast(obj);
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (buffer.isEmpty()){
            wait();
        }
        return buffer.removeFirst();
    }

    public int size() {
        return buffer.size();
    }
}
