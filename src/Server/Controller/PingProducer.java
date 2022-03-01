/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Server.Controller;

import Model.Buffer;
import Model.ChatMessage;
import Model.IMessage;
import Model.PingMessage;

public class PingProducer extends Thread {
    private final Buffer<IMessage> outputBuffer;
    private final int delay;

    public PingProducer(Buffer<IMessage> outputBuffer, int delay){
        this.outputBuffer = outputBuffer;
        this.delay = delay;

        start();
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                Thread.sleep(delay);
                outputBuffer.put(new PingMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
