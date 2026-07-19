package object;

import entity.Entity;
import main.GameWorld;

public class Door extends Object {
    private boolean open = false;
    public Door(int x, int y, GameWorld gameWorld){
        super(gameWorld);
        collision = true;
        worldX = x;
        worldY = y;
    }

    public boolean interact(Entity player){
        if(open){
            return true;
        }
        if(player.keysCount>0){
            player.keysCount--;
            open = true;
            collision = false;
            return true;
        }
        return false;
    }
}
