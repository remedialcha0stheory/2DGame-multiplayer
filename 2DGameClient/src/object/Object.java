package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public abstract class Object {
    int id;
    BufferedImage image;
    boolean collision = false;
    int worldX, worldY;
    GamePanel gamePanel;

    public Object(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void draw(Graphics2D g){
        
    }
}
