package network;

import java.io.DataInputStream;
import java.io.IOException;
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
    DataInputStream in;
    public DatagramSocket udpSocket;
    static final String SERVER_IP = "10.16.24.70";

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

            in = new DataInputStream(tcpSocket.getInputStream());
            message = readLine(in);
            String[] parts = message.split(":");
            gamePanel.player_id = Integer.parseInt(parts[1]);

            message = readLine(in);
            parts = message.split(":");
            server_addr.port = Integer.parseInt(parts[1]);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputThread outThread = new OutputThread(udpSocket, outQueue, server_addr);
        InputThread inThread = new InputThread(udpSocket, inQueue);
        InputTCPThread tcpThread = new InputTCPThread(tcpSocket, eventQueue, in);

        Thread outputThread = new Thread(outThread);
        Thread inputThread = new Thread(inThread);
        Thread inputTCPThread = new Thread(tcpThread);

        outputThread.start();
        inputThread.start();
        inputTCPThread.start();
    }

    private String readLine(DataInputStream in) throws IOException {
        StringBuilder line = new StringBuilder();
        int b;

        while ((b = in.read()) != -1) {
            if (b == '\n') {
                break;
            }
            if (b != '\r') {
                line.append((char) b);
            }
        }

        return line.toString();
}
}
