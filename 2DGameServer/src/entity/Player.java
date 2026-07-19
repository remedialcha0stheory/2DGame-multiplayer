package entity;

import java.awt.Rectangle;

import main.GameWorld;

public class Player extends Entity{
    int defaultWorldX;
    int defaultWorldY;
    public int screenX, screenY;
    public boolean taskCompleted = false;

    public Player(GameWorld gameWorld){
        super(gameWorld);
        this.player = true;
        this.solidArea = new Rectangle(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        maxLife = 10;
        life = maxLife;

        setDefaultValues();
    }

    private void setDefaultValues(){
        defaultWorldX = gameWorld.startingX[gameWorld.player_count]*gameWorld.tileSize;
        defaultWorldY = gameWorld.startingY[gameWorld.player_count]*gameWorld.tileSize;
        gameWorld.player_count++;
        worldX = defaultWorldX;
        worldY = defaultWorldY;
        speed = 4;
        direction = "down";
    }

    public boolean isPlayer(){
        return this.player;
    }

    public void update(){
        int keysPressed = (upPressed ? 1 : 0) + 
                  (downPressed ? 1 : 0) + 
                  (leftPressed ? 1 : 0) + 
                  (rightPressed ? 1 : 0);

        int tempSpeed = keysPressed>1 ? speed*1000/1200 : speed;

        if(upPressed==true){
            direction = "up";
            moveUp(tempSpeed);
        }
        if(downPressed==true){
            direction = "down";
            moveDown(tempSpeed);
        }
        if(leftPressed==true){
            direction = "left";
            moveLeft(tempSpeed);
        }
        if(rightPressed==true){
            direction = "right";
            moveRight(tempSpeed);
        }
        if(keysPressed>0){
            running = 2;
        }
        else{
            running = 1;
        }
        super.update();
    }
}
