/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Client.Controller;

import Model.Buffer;
import Model.Message;

public class PingProducer extends Thread {
    private final Buffer<Message> outputBuffer;
    private final int delay;

    public PingProducer(Buffer<Message> outputBuffer, int delay){
        this.outputBuffer = outputBuffer;
        this.delay = delay;

        start();
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                Thread.sleep(delay);
                outputBuffer.put(new Message());
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
