package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import entity.NPC_Greenslime;
import entity.NPC_OldMan;
import entity.Player;
import structs.ClientAddress;
import structs.GameEvent;

public class Main {
    static Queues queues;
    static boolean gameOver = false;
    public static void main(String[] args) throws Exception {
        queues = new Queues();
        GameWorld gameWorld = new GameWorld(queues);

        ArrayList<Socket> TCPSockets = new ArrayList<>();
        ArrayList<ClientAddress> clients = new ArrayList<>();
        ServerSocket listeningSocket = new ServerSocket(8080);
        listeningSocket.setSoTimeout(1000);

        long endTime = System.currentTimeMillis() + 10_000;

        Thread worker = new Thread(new Worker(queues, gameWorld));
        
        while(System.currentTimeMillis() <= endTime){
            Socket client = null;
            try{
                client = listeningSocket.accept();
            } catch(SocketTimeoutException e){
                continue;
            }
            System.out.println("inside the loop");
            TCPSockets.add(client);
            ClientAddress addr = new ClientAddress();

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String message = in.readLine();
            addr.clientIP = client.getInetAddress();
            String[] parts = message.split(":");
            addr.port = Integer.parseInt(parts[1]);
            clients.add(addr);
            
            Player player = new Player(gameWorld);

            OutputStream out = client.getOutputStream();
            message = "PLAYER_ID:"+player.getId()+"\n";
            gameWorld.entityManager.addEntity(player);
            out.write(message.getBytes());
            out.flush();

            GameEvent gE = new GameEvent();
            gE.eventType = 4;
            gE.targetId = player.getId();
            gE.worldX = player.worldX;
            gE.worldY = player.worldY;
            queues.eventQueue.add(gE);
        }
        GameEvent gE1 = new GameEvent();

        NPC_OldMan oldman = new NPC_OldMan(gameWorld, 23, 39);
        gameWorld.entityManager.addEntity(oldman);
        gE1.eventType = 5;
        gE1.targetId = oldman.getId();
        gE1.worldX = oldman.worldX;
        gE1.worldY = oldman.worldY;
        queues.eventQueue.add(gE1);

        GameEvent gE2 = new GameEvent();

        NPC_Greenslime slime1 = new NPC_Greenslime(gameWorld, 37, 9);
        gameWorld.entityManager.addEntity(slime1);
        gE2.eventType = 6;
        gE2.targetId = slime1.getId();
        gE2.worldX = slime1.worldX;
        gE2.worldY = slime1.worldY;
        queues.eventQueue.add(gE2);

        GameEvent gE3 = new GameEvent();

        NPC_Greenslime slime2 = new NPC_Greenslime(gameWorld, 39, 11);
        gameWorld.entityManager.addEntity(slime2);
        gE3.eventType = 6;
        gE3.targetId = slime2.getId();
        gE3.worldX = slime2.worldX;
        gE3.worldY = slime2.worldY;
        queues.eventQueue.add(gE3);

        DatagramSocket udpSocket = new DatagramSocket(8097);
        Thread inputThread = new Thread(new InputThread(queues, udpSocket));
        Thread outputThread = new Thread(new OutputThread(queues, clients, udpSocket));
        Thread tcpOutputThread = new Thread(new TCPOutputThread(queues, TCPSockets));

        System.out.println("syncing");
        synchronize(TCPSockets);
        System.out.println("sync complete!");

        worker.start();
        inputThread.start();
        outputThread.start();
        tcpOutputThread.start();

        listeningSocket.close();
    }

    private static void synchronize(ArrayList<Socket> TCPSockets){
        for(Socket sock: TCPSockets){
            try {
                DataOutputStream out = new DataOutputStream(sock.getOutputStream());
                out.writeBytes("UDP_PORT:8097\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
