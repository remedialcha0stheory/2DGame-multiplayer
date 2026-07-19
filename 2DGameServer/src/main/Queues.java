package main;

import java.util.concurrent.ConcurrentLinkedQueue;

import structs.GameEvent;
import structs.IncomingPacket;
import structs.OutgoingPacket;

public class Queues {
    ConcurrentLinkedQueue<IncomingPacket> incomingQueue = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<OutgoingPacket> outputQueue = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<GameEvent> eventQueue = new ConcurrentLinkedQueue<>();
}
