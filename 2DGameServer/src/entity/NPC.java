package entity;

import java.awt.Rectangle;

import main.GameWorld;

public class NPC extends Entity {
    int dir;
    public NPC(GameWorld gameWorld){
        super(gameWorld);
        this.solidArea = new Rectangle(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
    }

    public void update(){
        if(frameCounter==90){
            dir = gameWorld.randomGenerator.nextInt(1, 5);
            frameCounter = 0;
        }
        frameCounter++;
        switch(dir){
            case 1:
                direction = "up";
                moveUp(speed);
                break;
            case 2:
                direction = "down";
                moveDown(speed);
                break;
            case 3:
                direction = "left";
                moveLeft(speed);
                break;
            case 4: 
                direction = "right";
                moveRight(speed);
                break;
        }
        
        if(this.invincibleAfterCollisionCounter==this.invincibleAfterCollisionTimer){
            this.invincibleAfterCollisionCounter = 0;
            this.invincibleAfterCollision = false;
        }
        this.invincibleAfterCollisionCounter++;
    }
}
