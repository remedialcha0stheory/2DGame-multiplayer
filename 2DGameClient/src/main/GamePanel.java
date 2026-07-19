package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

import entity.EntityManager;
import entity.Fireball;
import entity.NPC_Greenslime;
import entity.NPC_OldMan;
import entity.Player;
import map.TileManager;
import network.Network;
import object.Chest;
import object.Door;
import object.Key;
import object.ObjectHandler;
import structs.GameEvent;
import structs.OutgoingPacket;
import structs.Tick;

public class GamePanel extends JPanel implements Runnable {
    public int player_id;
    public Network networkHandler;
    public int entity_id = 0;
    public final int tileSize = 48;
    final int maxScreenCol = 16; // 768
    final int maxScreenRow = 12; // 576
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;
    public final int centerX = screenWidth / 2;
    public final int centerY = screenHeight / 2;
    final int PLAY_STATE = 1;
    final int PAUSE_STATE = 2;
    final int DIALOGUE_STATE = 3;
    final int GAMEOVER_STATE = 4;
    final int GAMEWON_STATE = 5;
    Tick tick1 = new Tick();
    Tick tick2 = new Tick();
    Tick tick3 = new Tick();
    long tick = 2;

    public int gameState;

    public int worldRow, worldCol;

    Thread gameThread;
    public Random randomGenerator = new Random();

    public KeyHandler keyHandler = new KeyHandler(this);
    public TileManager tileManager;
    public Player player;
    public int maxFPS = 60;
    public ObjectHandler objectHandler = new ObjectHandler(this);
    public Sound music = new Sound();
    public Sound SE = new Sound();
    public EntityManager entityManager = new EntityManager(this);
    public UI ui = new UI(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GREEN);
        this.setDoubleBuffered(true);
        this.gameState = PLAY_STATE;
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        tick2.tick = 0;
        tick1.tick = 1;

        loadTileManager(1);

        networkHandler = new Network(this);
        // long endTime = System.currentTimeMillis() + 1_000;
        // while(System.currentTimeMillis() <= endTime){
        //     updateEvents();
        // }
        updateEvents();

        music.setClip(0);
        music.startClip();
        music.loopClip();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void loadTileManager(int mapNum) {
        switch (mapNum) {
            case 1:
                tileManager = new TileManager(this, mapNum, 50, 50);
                this.worldCol = tileManager.worldCol;
                this.worldRow = tileManager.worldRow;
                break;
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / maxFPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            if(gameState==PLAY_STATE){
                createOutputPackage();

                updateEvents();

                update();
            }

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0)
                    remainingTime = 0;

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameState == PLAY_STATE) {
            entityManager.update();
        }
    }

    public void paintComponent(Graphics g) {
        if(player==null) return;
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        player.computeScreenPlayerCoordinates();
        objectHandler.draw(g2);
        entityManager.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }

    public void gameOver() {
        gameState = GAMEOVER_STATE;
    }

    private void createOutputPackage() {
        tick3 = tick2;
        tick2 = tick1;
        tick1 = new Tick();
        tick1.tick = tick++;
        if (tick == 100000) {
            tick = 0;
        }

        tick1.aPressed = keyHandler.leftPressed;
        tick1.wPressed = keyHandler.upPressed;
        tick1.sPressed = keyHandler.downPressed;
        tick1.dPressed = keyHandler.rightPressed;
        tick1.enterPressed = keyHandler.enterPressed;
        tick1.spacePressed = keyHandler.spacePressed;

        OutgoingPacket outgoingPacket = new OutgoingPacket(player_id, tick1, tick2, tick3);
        networkHandler.outQueue.add(outgoingPacket);
    }

    public void updateEvents() {
        GameEvent event;
        while ((event= networkHandler.eventQueue.poll())!=null) {
            System.out.println("event of type: "+event.eventType);
            if (event.eventType == 1) {
                objectHandler.playSoundEffect(event.targetId);
                objectHandler.removeObject(event.targetId);
            } else if (event.eventType == 2) {
                if (event.objectType == 1) {
                    Key key = new Key(event.worldX, event.worldY, this);
                    key.setId(event.targetId);
                    objectHandler.insertObject(key);
                }
                if (event.objectType == 2) {
                    Door door = new Door(event.worldX, event.worldY, this);
                    door.setId(event.targetId);
                    objectHandler.insertObject(door);
                }
                if (event.objectType == 3) {
                    Chest chest = new Chest(event.worldX, event.worldY, this);
                    chest.setId(event.targetId);
                    objectHandler.insertObject(chest);
                }
            } else if (event.eventType == 3) {
                entityManager.removeEntity(event.targetId);
                if(event.targetId==player.getId()){
                    this.gameState=this.GAMEOVER_STATE;
                }
            } else if (event.eventType == 4) {
                Player p = new Player(this, keyHandler);
                p.setId(event.targetId);
                p.worldX = event.worldX;
                p.worldY = event.worldY;
                entityManager.addEntity(p);
                if (event.targetId == player_id) {
                    this.player = p;
                }
            } else if (event.eventType == 5) {
                NPC_OldMan oldman = new NPC_OldMan(this, event.worldX, event.worldY);
                oldman.setId(event.targetId);
                entityManager.addEntity(oldman);
            } else if (event.eventType == 6) {
                NPC_Greenslime slime = new NPC_Greenslime(this, event.worldX, event.worldY);
                slime.setId(event.targetId);
                entityManager.addEntity(slime);
            }
            else if(event.eventType == 7){
                String dir="";
                switch(event.direction){
                    case 1: dir = "up"; break;
                    case 2: dir = "down"; break;
                    case 3: dir = "left"; break;
                    case 4: dir = "right"; break;
                }
                Fireball fireball = new Fireball(this, event.worldX, event.worldY, dir);
                fireball.setId(event.targetId);
                entityManager.addEntity(fireball);
            }
            else if(event.eventType==8){
                System.out.println(player.getId());
                System.out.println(event.actorId);
                if(event.actorId==player.getId()){
                    System.out.println("game won!");
                    gameState = GAMEWON_STATE;
                    music.stopClip();
                    music.setClip(2);
                    music.startClip();
                }
                else{
                    gameState = GAMEOVER_STATE;
                    music.stopClip();
                }
            }
        }
    }
}
