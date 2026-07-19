package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

import main.GamePanel;
import structs.ClientAddress;
import structs.GameEvent;
import structs.IncomingPacket;
import structs.OutgoingPacket;

public class Network {
    GamePanel gamePanel;
    public Socket tcpSocket;
    public DatagramSocket udpSocket;
    static final String SERVER_IP = "192.168.1.28";

    public ConcurrentLinkedQueue<OutgoingPacket> outQueue = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<IncomingPacket> inQueue = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<GameEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public Network(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        ClientAddress server_addr = new ClientAddress();
        try {
            server_addr.clientIP = InetAddress.getByName(SERVER_IP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            udpSocket = new DatagramSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            tcpSocket = new Socket(SERVER_IP, 8080);
            String message = "UDP_PORT:"+udpSocket.getLocalPort()+"\n";
            OutputStream out = tcpSocket.getOutputStream();

            out.write(message.getBytes());
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            message = in.readLine();
            String[] parts = message.split(":");
            gamePanel.player_id = Integer.parseInt(parts[1]);

            in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            message = in.readLine();
            parts = message.split(":");
            server_addr.port = Integer.parseInt(parts[1]);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputThread outThread = new OutputThread(udpSocket, outQueue, server_addr);
        InputThread inThread = new InputThread(udpSocket, inQueue);
        InputTCPThread tcpThread = new InputTCPThread(tcpSocket, eventQueue);

        Thread outputThread = new Thread(outThread);
        Thread inputThread = new Thread(inThread);
        Thread inputTCPThread = new Thread(tcpThread);

        outputThread.start();
        inputThread.start();
        inputTCPThread.start();
    }

    
}
