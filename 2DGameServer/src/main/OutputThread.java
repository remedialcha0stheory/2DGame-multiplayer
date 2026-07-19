package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import structs.ClientAddress;
import structs.OutgoingPacket;

public class OutputThread implements Runnable{
    ConcurrentLinkedQueue <OutgoingPacket> outputQueue;
    public DatagramSocket socket;
    boolean runnning = true;
    ArrayList<ClientAddress> client;

    public OutputThread(Queues queues, ArrayList<ClientAddress>client, DatagramSocket socket){
        this.outputQueue = queues.outputQueue;
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {

        while(runnning){
            OutgoingPacket outgoingPacket = outputQueue.poll();
            if(outgoingPacket==null) continue;

            byte[] data = outgoingPacket.serialize();
            
            DatagramPacket packet;

            for(ClientAddress address: client){
                packet = new DatagramPacket(data, data.length, address.clientIP, address.port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        socket.close();
    }

}
