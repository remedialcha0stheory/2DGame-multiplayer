package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class NPC extends Entity {
    int dir;
    public NPC(GamePanel gamePanel){
        super(gamePanel);
        this.solidArea = new Rectangle(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
    }
}
