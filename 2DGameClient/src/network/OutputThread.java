package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ConcurrentLinkedQueue;

import structs.ClientAddress;
import structs.OutgoingPacket;

public class OutputThread implements Runnable{
    DatagramSocket udpSocket;
    ConcurrentLinkedQueue<OutgoingPacket> outQueue;
    public boolean running = true;
    ClientAddress addr;

    public OutputThread(DatagramSocket udpSocket, ConcurrentLinkedQueue<OutgoingPacket> oQ, ClientAddress addr){
        this.udpSocket = udpSocket;
        this.outQueue = oQ;
        this.addr = addr;
    }

    @Override
    public void run() {
        while(running){
            OutgoingPacket packet = outQueue.poll();
            if(packet==null) continue;

            byte[] data = packet.serialize();
            DatagramPacket datagram = new DatagramPacket(data, data.length, addr.clientIP, addr.port);

            try {
                udpSocket.send(datagram);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
