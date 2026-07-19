package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
    public KeyHandler keyHandler;
    int defaultWorldX;
    int defaultWorldY;
    public int screenX, screenY;
    public boolean taskCompleted = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        super(gamePanel);
        this.keyHandler = keyHandler;
        this.player = true;
        maxLife = 10;
        life = maxLife;

        setDefaultValues();
        loadCharacterImage("/player/boy");
        loadAttackImage("/player/boy");
    }

    private void setDefaultValues(){
        defaultWorldX = 23*gamePanel.tileSize;
        defaultWorldY = 21*gamePanel.tileSize;
        worldX = defaultWorldX;
        worldY = defaultWorldY;
        speed = 4;
        direction = "down";
    }

    public boolean isPlayer(){
        return this.player;
    }

    public void computeScreenPlayerCoordinates(){
        int screenWidth = gamePanel.screenWidth;
        int screenHeight = gamePanel.screenHeight;

        int topY = worldY - screenHeight/2; // 21 - 6 = 15 
        int bottomY = worldY + screenHeight/2; // 27
        int leftX = worldX - screenWidth/2; // 15
        int rightX = worldX + screenWidth/2; // 31

        screenX = gamePanel.centerX;
        screenY = gamePanel.centerY;

        if(topY < 0){
            screenY += topY;
        }
        if(bottomY > (gamePanel.worldRow)*gamePanel.tileSize){
            screenY -= ((gamePanel.worldRow)*gamePanel.tileSize - bottomY);
        }
        if(leftX < 0){
            screenX += leftX;
        }
        if(rightX > (gamePanel.worldCol)*gamePanel.tileSize){
            screenX -= ((gamePanel.worldCol)*gamePanel.tileSize - rightX);
        }
    }
}
