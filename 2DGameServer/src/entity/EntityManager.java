package entity;

import java.util.LinkedHashMap;

import main.GameWorld;

public class EntityManager {
    GameWorld gameWorld;
    public LinkedHashMap<Integer, Entity> entity;

    public EntityManager(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        entity = new LinkedHashMap<>();
    }

    public void addEntity(Entity e){
        entity.put(e.getId(), e);
    }

    public Entity getEntity(int entity_id){
        return entity.get(entity_id);
    }

    public void removeEntity(int entity_id){
        entity.remove(entity_id);
    }

    private void processContactDamage(Entity i, Entity e){
        if(e.getClass() == i.getClass()) return;
        i.damage(e.contactAttack);
        e.damage(i.contactAttack);
    }

    private void processAttackDamage(Entity attacker, Entity victim){
        victim.damage(attacker.attack);
    }

    private int processReturnValue(Entity i, Entity e){
        e.collidingEntity = i;
        i.collidingEntity = e;
        e.collisionOn |= true;
        i.collisionOn |= true;
        processContactDamage(i, e);
        return i.getId();
    }

    public int checkEntityCollision(Entity e, String direction){
        switch (direction) {
            case "up":
                for (Entity i: entity.values()) {
                    if(i.getId()==e.getId()) continue;

                    i.solidArea.x += i.worldX - e.worldX;
                    i.solidArea.y += i.worldY - e.worldY + e.speed;
                    if(i.solidArea.intersects(e.solidArea)){
                        i.setDefaultSolidArea();
                        return processReturnValue(i, e);
                    }
                    if(e.attacking){
                        e.solidArea.x += e.attackArea.x;
                        e.solidArea.y += e.attackArea.y;
                        e.solidArea.width = e.attackArea.width;
                        e.solidArea.height = e.attackArea.height;
                        if(i.solidArea.intersects(e.solidArea)){
                            processAttackDamage(e, i);
                        }
                        e.setDefaultSolidArea();
                    }
                    i.setDefaultSolidArea();
                }
                break;
            case "down":
                for (Entity i: entity.values()) {
                    if(i.getId()==e.getId()) continue;

                    i.solidArea.x += i.worldX - e.worldX;
                    i.solidArea.y += i.worldY - e.worldY - e.speed;
                    if(i.solidArea.intersects(e.solidArea)){
                        i.setDefaultSolidArea();
                        return processReturnValue(i, e);
                    }
                    if(e.attacking){
                        e.solidArea.x += e.attackArea.x;
                        e.solidArea.y += e.attackArea.y;
                        e.solidArea.width = e.attackArea.width;
                        e.solidArea.height = e.attackArea.height;
                        if(i.solidArea.intersects(e.solidArea)){
                            processAttackDamage(e, i);
                        }
                        e.setDefaultSolidArea();
                    }
                    i.setDefaultSolidArea();
                }
                break;
            case "left":
                for (Entity i: entity.values()) {
                    if(i.getId()==e.getId()) continue;
                    
                    i.solidArea.x += i.worldX - e.worldX + e.speed;
                    i.solidArea.y += i.worldY - e.worldY;
                    if(i.solidArea.intersects(e.solidArea)){
                        i.setDefaultSolidArea();
                        return processReturnValue(i, e);
                    }
                    if(e.attacking){
                        e.solidArea.x += e.attackArea.x;
                        e.solidArea.y += e.attackArea.y;
                        e.solidArea.width = e.attackArea.width;
                        e.solidArea.height = e.attackArea.height;
                        if(i.solidArea.intersects(e.solidArea)){
                            processAttackDamage(e, i);
                        }
                        e.setDefaultSolidArea();
                    }
                    i.setDefaultSolidArea();
                }
                break;
            case "right":
                for (Entity i: entity.values()) {
                    if(i.getId()==e.getId()) continue;
                    
                    i.solidArea.x += i.worldX - e.worldX - e.speed;
                    i.solidArea.y += i.worldY - e.worldY;
                    if(i.solidArea.intersects(e.solidArea)){
                        i.setDefaultSolidArea();
                        return processReturnValue(i, e);
                    }
                    if(e.attacking){
                        e.solidArea.x += e.attackArea.x;
                        e.solidArea.y += e.attackArea.y;
                        e.solidArea.width = e.attackArea.width;
                        e.solidArea.height = e.attackArea.height;
                        if(i.solidArea.intersects(e.solidArea)){
                            processAttackDamage(e, i);
                        }
                        e.setDefaultSolidArea();
                    }
                    i.setDefaultSolidArea();
                }
                break;
        }
        return 999;
    }
}
