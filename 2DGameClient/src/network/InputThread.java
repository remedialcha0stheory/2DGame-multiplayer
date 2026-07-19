package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ConcurrentLinkedQueue;

import structs.IncomingPacket;

public class InputThread implements Runnable {
    DatagramSocket udpSocket;
    ConcurrentLinkedQueue<IncomingPacket> inQueue;
    public boolean running = true;

    public InputThread(DatagramSocket udpSocket, ConcurrentLinkedQueue<IncomingPacket> iQ){
        this.udpSocket = udpSocket;
        this.inQueue = iQ;
    }

    @Override
    public void run() {
        byte[] buff = new byte[1024];
        while(running){
            DatagramPacket packet = new DatagramPacket(buff, buff.length);

            try {
                udpSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            IncomingPacket incPacket = IncomingPacket.deserialize(buff);
            inQueue.add(incPacket);
        }
    }


}
