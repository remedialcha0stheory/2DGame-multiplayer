package object;

import entity.Entity;
import main.GameWorld;

public class Chest extends Object {
    public Chest(int x, int y, GameWorld gameWorld){
        super(gameWorld);
        collision = false;
        worldX = x;
        worldY = y;
    }

    public boolean interact(Entity player){
        gameWorld.gameOver(player.getId());
        return true;
    }
}
