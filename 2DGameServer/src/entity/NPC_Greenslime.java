package entity;

import java.awt.Rectangle;

import main.GameWorld;

public class NPC_Greenslime extends NPC {
    public NPC_Greenslime(GameWorld gameWorld, int x, int y) {
        super(gameWorld);
        solidArea = new Rectangle(3, 18, 42, 30);
        RECT_X = 3;
        RECT_Y = 18;
        RECT_WIDTH = 42;
        RECT_HEIGHT = 30;
        maxLife = 8;
        speed = 1;
        life = maxLife;
        contactAttack = 1;
        worldX = x * gameWorld.tileSize;
        worldY = y * gameWorld.tileSize;
    }

    public void collide(Entity e) {

    }

    public void interact() {

    }
}
