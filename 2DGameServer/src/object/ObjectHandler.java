package object;

import java.util.Arrays;
import java.util.LinkedHashMap;

import entity.Entity;
import main.GameWorld;
import structs.GameEvent;

public class ObjectHandler {
    public LinkedHashMap<Integer, Object> object;
    int[][] objectMap;
    GameWorld gameWorld;
    int objectCount = 0;

    public ObjectHandler(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        object = new LinkedHashMap<>();
        objectMap = new int[50][50];
        for (int[] row : objectMap) {
            Arrays.fill(row, -1);
        }

        createStartingObjects();
    }

    private void createStartingObjects(){
        Key key1 = new Key(20, 8, gameWorld);
        key1.setId(objectCount++);
        insertObject(key1);

        Key key2 = new Key(36, 30, gameWorld);
        key2.setId(objectCount++);
        insertObject(key2);

        Chest chest = new Chest(10, 8, gameWorld);
        chest.setId(objectCount++);
        insertObject(chest);

        Door door1 = new Door(10, 12, gameWorld);
        door1.setId(objectCount++);
        insertObject(door1);

        Door door2 = new Door(14, 28, gameWorld);
        door2.setId(objectCount++);
        insertObject(door2);
    }

    public void insertObject(Object obj) {
        object.put(obj.getId(), obj);
        GameEvent event = new GameEvent();
        event.actorId = -1;
        event.targetId = obj.getId();
        if(obj instanceof Key) event.objectType = 1;
        if(obj instanceof Door) event.objectType = 2;
        if(obj instanceof Chest) event.objectType = 3;
        event.worldX = obj.worldX;
        event.worldY = obj.worldY;
        event.eventType = 2;
        gameWorld.addEvent(event);
        objectMap[obj.worldX][obj.worldY] = obj.getId();
    }

    public Object getObject(int object_id){
        return object.get(object_id);
    }

    public void removeObject(int object_id, Entity e){
        Object obj = getObject(object_id);
        objectMap[obj.worldX][obj.worldY] = -1;
        object.remove(object_id);
    }

    private int processReturnValue(int i, Entity entity) {
        Object obj = getObject(i);
        if (obj.interact(entity)) {
            if (obj instanceof Key || obj instanceof Door) {
                removeObject(i, entity);
                GameEvent gameEvent = new GameEvent();
                gameEvent.actorId = entity.getId();
                gameEvent.targetId = i;
                gameEvent.eventType = 1;
                gameWorld.addEvent(gameEvent);
            }
            return i;
        } else
            return 700;
    }

    public int checkObject(Entity entity, String direction) {
        int topY = entity.worldY + entity.solidArea.y;
        int bottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int leftX = entity.worldX + entity.solidArea.x;
        int rightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;

        switch (direction) {
            case "up":
                int projectedTopY = topY - entity.speed;
                int tileY = projectedTopY / gameWorld.tileSize;
                int tileX1 = leftX / gameWorld.tileSize;
                int tileX2 = rightX / gameWorld.tileSize;

                if(objectMap[tileX1][tileY]>-1){
                    return processReturnValue(objectMap[tileX1][tileY], entity);
                }
                if(objectMap[tileX2][tileY]>-1){
                    return processReturnValue(objectMap[tileX2][tileY], entity);
                }
                break;
            case "down":
                int projectedBottomY = bottomY + entity.speed;
                tileY = projectedBottomY / gameWorld.tileSize;
                tileX1 = leftX / gameWorld.tileSize;
                tileX2 = rightX / gameWorld.tileSize;
                
                if(objectMap[tileX1][tileY]>-1){
                    return processReturnValue(objectMap[tileX1][tileY], entity);
                }
                if(objectMap[tileX2][tileY]>-1){
                    return processReturnValue(objectMap[tileX2][tileY], entity);
                }
                break;
            case "left":
                int projectedLeftX = leftX - entity.speed;
                int tileX = projectedLeftX / gameWorld.tileSize;
                int tileY1 = topY / gameWorld.tileSize;
                int tileY2 = bottomY / gameWorld.tileSize;

                if(objectMap[tileX][tileY1]>-1){
                    return processReturnValue(objectMap[tileX][tileY1], entity);
                }
                if(objectMap[tileX][tileY2]>-1){
                    return processReturnValue(objectMap[tileX][tileY2], entity);
                }
                break;
            case "right":
                int projectedRightX = rightX + entity.speed;
                tileX = projectedRightX / gameWorld.tileSize;
                tileY1 = topY / gameWorld.tileSize;
                tileY2 = bottomY / gameWorld.tileSize;
                
                if(objectMap[tileX][tileY1]>-1){
                    return processReturnValue(objectMap[tileX][tileY1], entity);
                }
                if(objectMap[tileX][tileY2]>-1){
                    return processReturnValue(objectMap[tileX][tileY2], entity);
                }
                break;
        }
        return 999;
    }
}
