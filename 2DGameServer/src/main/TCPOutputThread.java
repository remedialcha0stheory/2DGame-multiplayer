package main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import structs.GameEvent;

public class TCPOutputThread implements Runnable {
    ConcurrentLinkedQueue <GameEvent> outputQueue;
    ArrayList<Socket> clientTCPSockets;
    public boolean running = true;

    public TCPOutputThread(Queues queues, ArrayList<Socket> tcpsockets){
        this.outputQueue = queues.eventQueue;
        this.clientTCPSockets = tcpsockets;
    }

    @Override
    public void run() {
        while(running){
            GameEvent gameEvent = outputQueue.poll();
            if(gameEvent==null) continue;

            byte[] data = gameEvent.serialize();

            for(Socket sock: clientTCPSockets){
                try {
                    OutputStream out = sock.getOutputStream();
                    out.write(data);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for(int i=clientTCPSockets.size()-1; i>=0; i--){
            try {
                clientTCPSockets.get(i).close();
                clientTCPSockets.remove(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
