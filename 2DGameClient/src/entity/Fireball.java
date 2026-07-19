package entity;

import main.GamePanel;

public class Fireball extends Entity{
    
    public Fireball(GamePanel gamePanel, int worldX, int worldY, String direction){
        super(gamePanel);
        loadCharacterImage("/fireball/fireball");
        speed = 5;
        contactAttack = 2;
        maxLife = 120;
        life = maxLife;
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
}
