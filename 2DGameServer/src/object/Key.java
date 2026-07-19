package object;

import entity.Entity;
import main.GameWorld;

public class Key extends Object {
    public Key(int x, int y, GameWorld gameWorld){
        super(gameWorld);
        collision = false;
        worldX = x;
        worldY = y;
    }

    public boolean interact(Entity player){
        player.keysCount++;
        return true;
    }
}
