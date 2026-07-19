package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GameWorld;

public abstract class Object {
    int object_id;
    BufferedImage image;
    boolean collision = false;
    int worldX, worldY;
    GameWorld gameWorld;

    abstract public boolean interact(Entity entity);

    public Object(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    public int getId(){
        return object_id;
    }
    public void setId(int id){
        this.object_id = id;
    }
}
