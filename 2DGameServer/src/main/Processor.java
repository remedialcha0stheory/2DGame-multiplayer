package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import entity.Entity;
import entity.Fireball;
import entity.NPC;
import entity.Player;
import structs.EntityInfo;
import structs.GameEvent;
import structs.IncomingPacket;
import structs.OutgoingPacket;
import structs.Tick;
import java.util.Map;

public class Processor {
    ArrayList<IncomingPacket> queue;
    GameWorld gameWorld;
    boolean[][] tickChecked;
    static int tickCounter = 0;

    public Processor(GameWorld gameWorld){
        queue = new ArrayList<>();
        this.gameWorld = gameWorld;
        tickChecked = new boolean[gameWorld.player_count][100000];
    }

    public OutgoingPacket processQueue(){
        OutgoingPacket outputPacket = new OutgoingPacket();
        updateNPCs();

        for(IncomingPacket packet: queue){
            int player_id = packet.player_id;
            Entity e = gameWorld.entityManager.getEntity(player_id);
            if(e==null) continue;

            if(tickChecked[player_id][(int)packet.tick3.tick%100000]==false){
                tickChecked[player_id][(int)(packet.tick3.tick%100000)] = true;
                processTick(e, packet.tick3);
            }
            if(tickChecked[player_id][(int)packet.tick2.tick%100000]==false){
                tickChecked[player_id][(int)(packet.tick2.tick%100000)] = true;
                processTick(e, packet.tick2);
            }
            if(tickChecked[player_id][(int)packet.tick1.tick%100000]==false){
                tickChecked[player_id][(int)(packet.tick1.tick%100000)] = true;
                processTick(e, packet.tick1);
            }
        }

        processOutput(outputPacket);

        return outputPacket;
    }

    private void processOutput(OutgoingPacket outputPacket){
        outputPacket.tick = tickCounter++;
        outputPacket.numEntities = gameWorld.entityManager.entity.size();
        outputPacket.entity = new EntityInfo[outputPacket.numEntities];

        int i = 0;
        for(Entity e: gameWorld.entityManager.entity.values()){
            outputPacket.entity[i] = new EntityInfo();
            outputPacket.entity[i].id = e.getId();
            outputPacket.entity[i].worldX = e.worldX;
            outputPacket.entity[i].worldY = e.worldY;
            switch(e.direction){
                case "up":
                    outputPacket.entity[i].direction = 1;
                    break;
                case "down":
                    outputPacket.entity[i].direction = 2;
                    break;
                case "left":
                    outputPacket.entity[i].direction = 3;
                    break;
                case "right":
                    outputPacket.entity[i].direction = 4;
                    break;

            }
            outputPacket.entity[i].life = e.life;
            outputPacket.entity[i].invincibleOn = e.invincibleAfterCollision;
            outputPacket.entity[i].collisionOn = e.collisionOn;
            if(e.collidingEntity!=null){
                outputPacket.entity[i].collidingEntityId = e.collidingEntity.getId();
            }
            else{
                outputPacket.entity[i].collidingEntityId = -1;
            }
            outputPacket.entity[i].attacking = e.attacking;
            outputPacket.entity[i].running = e.running;
            outputPacket.entity[i].keysCount = e.keysCount;
            i++;
        }

    }

    private void processTick(Entity e, Tick tick){
        if(e.life <= 0){
            GameEvent event = new GameEvent();
            event.actorId = -1;
            event.targetId = e.getId();
            event.worldX = -1;
            event.worldY = -1;
            event.objectType = -1;
            event.eventType = 3;
            gameWorld.addEvent(event);
            gameWorld.entityManager.removeEntity(e.getId());
            return;
        }
        e.upPressed = tick.wPressed;
        e.downPressed = tick.sPressed;
        e.leftPressed = tick.aPressed;
        e.rightPressed = tick.dPressed;
        if(e.collisionOn==false) e.attacking = tick.enterPressed;
        if(tick.spacePressed && !e.cooldownAfterFireball){
            Fireball fireball = new Fireball(gameWorld, e.worldX, e.worldY, e.direction);
            gameWorld.entityManager.addEntity(fireball);
            e.cooldownAfterFireball = true;
            GameEvent gE = new GameEvent();
            gE.eventType = 7;
            gE.worldX = e.worldX;
            gE.worldY = e.worldY;
            gE.targetId = fireball.getId();
            switch(e.direction){
                case "up": gE.direction = 1; break;
                case "down": gE.direction = 2; break;
                case "left": gE.direction = 3; break;
                case "right": gE.direction = 4; break;
            }
            gameWorld.addEvent(gE);
        }

        if(e.trueFrames==10){
            e.collisionOn = false;
            e.trueFrames = 0;
        }
        e.trueFrames++;
        if(e.attacking){
                e.attack();
        }
        e.update();
    }

    public void clean(){
        queue.clear();
    }

    private void updateNPCs(){
        Iterator<Map.Entry<Integer, Entity>> it = gameWorld.entityManager.entity.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer, Entity> ent = it.next();
            Entity e = ent.getValue();
            if(e==null) continue;
            if(e instanceof Player) continue;
            if(e.life <= 0){
                GameEvent gE = new GameEvent();
                gE.eventType = 3;
                gE.targetId = e.getId();
                gameWorld.addEvent(gE);
                // gameWorld.entityManager.removeEntity(e.getId());
                it.remove();
                continue;
            }

            if(e.trueFrames==10){
                e.collisionOn = false;
                e.trueFrames = 0;
            }
            e.trueFrames++;
            if(e.attacking){
                    e.attack();
            }
            e.update();
        }
    }

    public void addPacket(IncomingPacket packet){
        queue.add(packet);
    }
}
