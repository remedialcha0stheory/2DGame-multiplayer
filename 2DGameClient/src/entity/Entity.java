package entity;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import util.Utility;

public class Entity {
    private int id;
    public int worldX, worldY; // in pixels.
    public int speed;
    public int life;
    public int maxLife;
    public int contactAttack = 0;
    public int attack = 1;
    public int keysCount = 0;
    public Entity collidingEntity;
    public boolean collisionOn = false;
    protected int DEFAULT_MOVE_VALUE = 6000;
    protected boolean invincibleAfterCollision = false;
    protected int invincibleAfterCollisionCounter = 0;
    protected int invincibleAfterCollisionTimer = 90;

    public int trueFrames = 0; // to hold the collisionOn value for 2 frames after detaching, otherwise it alternates fast
    // when player and npc moving in the same direction.

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attack_up1, attack_up2, attack_down1, attack_down2, attack_left1, attack_left2, attack_right1, attack_right2;
    public String direction="down"; 
    public int runCounter;
    public int runNum;
    public boolean attacking = false;
    public int attackCounter;
    public int attackNum;
    public int frameCounter = 90; // to update npc direction every 1.5 seconds
    public GamePanel gamePanel;
    public int running = 1;

    public Rectangle solidArea;
    public int RECT_X = 10;
    public int RECT_Y = 16;
    public int RECT_WIDTH = 28;
    public int RECT_HEIGHT = 28;
    public Rectangle attackArea;

    protected boolean player = false;

    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        runNum = 1;
        id = gamePanel.entity_id++;
        solidArea = new Rectangle(0, 0, gamePanel.tileSize, gamePanel.tileSize);
        attackArea = new Rectangle();
    }

    public void update(){
        
        if(this.invincibleAfterCollisionCounter==this.invincibleAfterCollisionTimer){
            this.invincibleAfterCollisionCounter = 0;
            this.invincibleAfterCollision = false;
        }
        this.invincibleAfterCollisionCounter++;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    protected void setDefaultSolidArea(){
        this.solidArea.x = RECT_X;
        this.solidArea.y = RECT_Y;
        // this.solidArea.width = RECT_WIDTH;
        // this.solidArea.height = RECT_HEIGHT;
    }

    protected void updateRunCounter(){
        runCounter++;
        if(runCounter > 10){
            if(runNum==1){
                runNum=2;
            }
            else if(runNum==2){
                runNum=1;
            }
            runCounter=0;
        }
    }

    protected void loadCharacterImage(String path){
        up1 = setupImage(path+"_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setupImage(path+"_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setupImage(path+"_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setupImage(path+"_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setupImage(path+"_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setupImage(path+"_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setupImage(path+"_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setupImage(path+"_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    protected void loadAttackImage(String path){
        attack_up1 = setupImage(path+"_attack_up_1", gamePanel.tileSize, 2*gamePanel.tileSize);
        attack_up2 = setupImage(path+"_attack_up_2", gamePanel.tileSize, 2*gamePanel.tileSize);
        attack_down1 = setupImage(path+"_attack_down_1", gamePanel.tileSize, 2*gamePanel.tileSize);
        attack_down2 = setupImage(path+"_attack_down_2", gamePanel.tileSize, 2*gamePanel.tileSize);
        attack_left1 = setupImage(path+"_attack_left_1", 2*gamePanel.tileSize, gamePanel.tileSize);
        attack_left2 = setupImage(path+"_attack_left_2", 2*gamePanel.tileSize, gamePanel.tileSize);
        attack_right1 = setupImage(path+"_attack_right_1", 2*gamePanel.tileSize, gamePanel.tileSize);
        attack_right2 = setupImage(path+"_attack_right_2", 2*gamePanel.tileSize, gamePanel.tileSize);
    }

    protected BufferedImage setupImage(String imageName, int width, int height){
        Utility util = new Utility();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imageName+".png"));
            image = util.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void draw(Graphics2D g){
        int screenX = gamePanel.player.screenX - gamePanel.player.worldX + this.worldX;
        int screenY = gamePanel.player.screenY - gamePanel.player.worldY + this.worldY;

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(attacking){
                    if(attackNum==1) image = attack_up1;
                    else image = attack_up2;
                }
                else {
                    if(runNum==1) image = up1;
                    else image = up2;
                }
                break;
            case "down":
                if(attacking){
                    if(attackNum==1) image = attack_down1;
                    else image = attack_down2;
                }
                else {
                    if(runNum==1) image = down1;
                    else image = down2;
                }
                break;
            case "left":
                if(attacking){
                    if(attackNum==1) image = attack_left1;
                    else image = attack_left2;
                }
                else {
                    if(runNum==1) image = left1;
                    else image = left2;
                }
                break;
            case "right":
                if(attacking){
                    if(attackNum==1) image = attack_right1;
                    else image = attack_right2;
                }
                else {
                    if(runNum==1) image = right1;
                    else image = right2;
                }
                break;
        }

        if(attacking){
            if(direction=="up"){
                screenY -= gamePanel.tileSize;
            }
            else if(direction=="left"){
                screenX -= gamePanel.tileSize;
            }
        }

        if(this.invincibleAfterCollision){
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g.drawImage(image, screenX, screenY, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if(attacking){
            if(direction=="up"){
                screenY += gamePanel.tileSize;
            }
            else if(direction=="left"){
                screenX += gamePanel.tileSize;
            }
        }

        if(!(this instanceof Player) && !(this instanceof Fireball)){
            int unit = gamePanel.tileSize;
            g.setColor(Color.WHITE);
            g.fillRoundRect(screenX, screenY-unit/2, unit, unit/2, 3, 3);
            g.setColor(Color.RED);
            int width = unit*life/maxLife;
            g.fillRoundRect(screenX, screenY-unit/2, width, unit/2, 3, 3);
            g.setStroke(new BasicStroke(4));
            g.setColor(Color.BLACK);
            g.drawRoundRect(screenX, screenY-unit/2, unit, unit/2, 3, 3);
        }
    }

    public boolean interact(){
        return false;
    }

    public void collide(Entity e){
        
    }

    public void damage(int damage){
        if(damage==0) return;
        if(this.invincibleAfterCollision==false){
            this.life-=damage;
            this.invincibleAfterCollision = true;
        }
    }

    public void attack(){
        setAttackArea();
        if(attackCounter <= 5){
            attackNum = 1;
        }
        else if(attackCounter <= 25){
            attackNum = 2;
        }
        else{
            attackCounter = 0;
            attacking = false;
        }
        attackCounter++;
    }

    private void setAttackArea(){
        switch(direction){
            case "up":
                attackArea.x = 14;
                attackArea.y = -40;
                attackArea.width = 20;
                attackArea.height = 40;
                break;
            
            case "down":
                attackArea.x = 14;
                attackArea.y = 48;
                attackArea.width = 20;
                attackArea.height = 40;
                break;
            
            case "left":
                attackArea.x = -40;
                attackArea.y = -14;
                attackArea.width = 40;
                attackArea.height = 20;
                break;
            
            case "right":
                attackArea.x = 48;
                attackArea.y = -14;
                attackArea.width = 40;
                attackArea.height = 20;
                break;
        }
    }
}
