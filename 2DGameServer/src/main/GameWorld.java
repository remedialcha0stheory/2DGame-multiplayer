package main;

import java.util.Random;

import entity.EntityManager;
import object.ObjectHandler;
import structs.GameEvent;

public class GameWorld {
    Queues queues;
    public Map map = new Map(this, 1, 50, 50);
    public int tileSize = 48;

    public int worldCol = 50;
    public int worldRow = 50;

    public Random randomGenerator = new Random();

    public int[] startingX = {23, 33};
    public int[] startingY = {21, 37};
    public int player_count;
    public EntityManager entityManager;
    public ObjectHandler objectHandler;

    public GameWorld(Queues queues){
        this.queues = queues;
        entityManager = new EntityManager(this);
        objectHandler = new ObjectHandler(this);
    }

    public void gameOver(int player_id){
        GameEvent gameEvent = new GameEvent();
        gameEvent.eventType = 8;
        gameEvent.actorId = player_id;
        addEvent(gameEvent);
        System.out.println("player won: " + player_id);
    }

    public void addEvent(GameEvent event){
        queues.eventQueue.add(event);
    }
}
