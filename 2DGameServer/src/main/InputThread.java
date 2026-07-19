package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ConcurrentLinkedQueue;

import structs.IncomingPacket;

public class InputThread implements Runnable {
    ConcurrentLinkedQueue <IncomingPacket> inputQueue;
    boolean running = true;
    public DatagramSocket socket;

    public InputThread(Queues queues, DatagramSocket socket){
        this.inputQueue = queues.incomingQueue;
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] packet = new byte[40];

        while(running){
            DatagramPacket datagram = new DatagramPacket(packet, packet.length);
            try {
                socket.receive(datagram);
            } catch (IOException e) {
                e.printStackTrace();
            }
            IncomingPacket incomingPacket = IncomingPacket.deserialize(packet);
            inputQueue.add(incomingPacket);
        }
        socket.close();
    }

}
