package main;

import structs.IncomingPacket;

public class Worker implements Runnable {
    Queues queues;
    GameWorld gameWorld;
    int tickRate = 60;

    public Worker(Queues queues, GameWorld gameWorld){
        this.queues = queues;
        this.gameWorld = gameWorld;
    }

    @Override
    public void run() {
        double tickInterval = 1000000000/tickRate;
        double nextTickTime = System.nanoTime() + tickInterval;

        Processor proc = new Processor(gameWorld);
        while(Main.gameOver==false){
            // processing
            IncomingPacket packet;
            while((packet = queues.incomingQueue.poll())!=null){
                proc.addPacket(packet);
            }
            queues.outputQueue.add(proc.processQueue());
            proc.clean();

            try {
                double remainingTime = nextTickTime - System.nanoTime();
                remainingTime /= 1000000;   

                if(remainingTime<0) remainingTime = 0;
                Thread.sleep((long)remainingTime);

                nextTickTime += tickInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
