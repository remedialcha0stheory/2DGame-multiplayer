package network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import structs.GameEvent;

public class InputTCPThread implements Runnable {
    Socket tcpSocket;
    ConcurrentLinkedQueue<GameEvent> eventQueue;
    public boolean running = true;

    public InputTCPThread(Socket tcpSocket, ConcurrentLinkedQueue<GameEvent> eQ){
        this.tcpSocket = tcpSocket;
        this.eventQueue = eQ;
    }

    @Override
    public void run() {
        byte[] payload = new byte[28];
        DataInputStream in = null;
        try {
            in = new DataInputStream(tcpSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(running){
            try {
                in.readFully(payload);
            } catch (IOException e) {
                e.printStackTrace();
            }

            GameEvent gE = GameEvent.deserialize(payload);
            eventQueue.add(gE);
        }
    }
}
