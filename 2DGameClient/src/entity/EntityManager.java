package entity;

import java.awt.Graphics2D;
import java.util.LinkedHashMap;

import main.GamePanel;
import structs.EntityInfo;
import structs.IncomingPacket;

public class EntityManager {
    GamePanel gamePanel;
    public LinkedHashMap<Integer, Entity> entity;

    public EntityManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        entity = new LinkedHashMap<>();
    }

    public void addEntity(Entity e){
        entity.put(e.getId(), e);
    }

    public Entity getEntity(int entity_index){
        return entity.get(entity_index);
    }

    public void removeEntity(int entity_id){
        entity.remove(entity_id);
    }

    public void update(){
        IncomingPacket packet;
        while((packet = gamePanel.networkHandler.inQueue.poll())!=null){
            for(int e=0; e<packet.numEntities; e++){
                EntityInfo i = packet.entity[e];
                if(getEntity(i.id)==null) continue;
                getEntity(i.id).worldX = i.worldX;
                getEntity(i.id).worldY = i.worldY;
                switch(i.direction){
                    case 1:
                        getEntity(i.id).direction = "up";
                        break;
                    case 2:
                        getEntity(i.id).direction = "down";
                        break;
                    case 3:
                        getEntity(i.id).direction = "left";
                        break;
                    case 4:
                        getEntity(i.id).direction = "right";
                        break;
                }
                getEntity(i.id).life = i.life;
                getEntity(i.id).invincibleAfterCollision = i.invincibleOn;
                getEntity(i.id).collisionOn = i.collisionOn;
                if(i.collidingEntityId!=-1) getEntity(i.id).collidingEntity = getEntity(i.collidingEntityId);
                getEntity(i.id).attacking = i.attacking;
                getEntity(i.id).running = i.running;
                getEntity(i.id).keysCount = i.keysCount;
                if(getEntity(i.id).running==2) getEntity(i.id).updateRunCounter();
            }
        }
    }

    public void draw(Graphics2D g){
        for(Entity e: entity.values()){
            e.draw(g);
        }
    }
}
