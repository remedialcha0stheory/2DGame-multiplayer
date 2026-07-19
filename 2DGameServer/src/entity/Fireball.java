package entity;

import java.awt.Rectangle;

import main.GameWorld;

public class Fireball extends Entity{
    
    public Fireball(GameWorld gameWorld, int worldX, int worldY, String direction){
        super(gameWorld);
        speed = 5;
        contactAttack = 2;
        maxLife = 120;
        life = maxLife;
        RECT_X = 22;
        RECT_Y = 22;
        RECT_WIDTH = 4;
        RECT_HEIGHT = 4;
        solidArea = new Rectangle(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        switch(direction){
            case "up":
                this.worldY -= 33;
                break;
            case "down":
                this.worldY += 45;
                break;
            case "left":
                this.worldX -= 39;
                break;
            case "right":
                this.worldX += 39;
                break;
        }
    }

    public void update(){
        switch(direction){
            case "up":
                moveUp(speed);
                break;
            case "down":
                moveDown(speed);
                break;
            case "left":
                moveLeft(speed);
                break;
            case "right":
                moveRight(speed);
                break;
        }
        this.life--;
    }

    public void damage(int damage){ // called whenever theres a collision.
        this.life=0;
    }
}
