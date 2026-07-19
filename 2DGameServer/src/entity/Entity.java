package entity;
import java.awt.Rectangle;

import main.GameWorld;

public class Entity {
    static private int entityCounter = 0;
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
    public boolean invincibleAfterCollision = false;
    protected int invincibleAfterCollisionCounter = 0;
    protected int invincibleAfterCollisionTimer = 90;
    
    public boolean cooldownAfterFireball = false;
    protected int cooldownAfterFireballCounter = 0;
    protected int cooldownAfterFireballTimer = 60;

    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;

    public int running = 1;

    public int trueFrames = 0; // to hold the collisionOn value for 2 frames after detaching, otherwise it alternates fast
    // when player and npc moving in the same direction.

    public String direction; 
    public boolean attacking = false;
    public int attackingEntityId;
    public int attackCounter;
    public int frameCounter = 90; // to update npc direction every 1.5 seconds
    public GameWorld gameWorld;

    public Rectangle solidArea;
    public int RECT_X = 10;
    public int RECT_Y = 16;
    public int RECT_WIDTH = 28;
    public int RECT_HEIGHT = 28;
    public Rectangle attackArea;

    protected boolean player = false;

    public Entity(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        id = entityCounter++;
        solidArea = new Rectangle(0, 0, gameWorld.tileSize, gameWorld.tileSize);
        attackArea = new Rectangle();
    }

    public void update(){
        if(this.invincibleAfterCollisionCounter==this.invincibleAfterCollisionTimer){
            this.invincibleAfterCollisionCounter = 0;
            this.invincibleAfterCollision = false;
        }
        this.invincibleAfterCollisionCounter++;
        if(this.cooldownAfterFireballCounter==this.cooldownAfterFireballTimer){
            this.cooldownAfterFireballCounter = 0;
            this.cooldownAfterFireball = false;
        }
        this.cooldownAfterFireballCounter++;
    }

    public int getId(){
        return id;
    }

    protected void setDefaultSolidArea(){
        this.solidArea.x = RECT_X;
        this.solidArea.y = RECT_Y;
        // this.solidArea.width = RECT_WIDTH;
        // this.solidArea.height = RECT_HEIGHT;
    }

    protected int moveUp(int speed){
        if(worldY-speed>0 && !gameWorld.map.checkTile(this, direction)){
            if(gameWorld.objectHandler.checkObject(this, direction)!=700) {
                int entityCollision = gameWorld.entityManager.checkEntityCollision(this, direction);
                if(entityCollision!=999) return entityCollision; // collision happened;
                worldY -= speed;
            }
        }
        return DEFAULT_MOVE_VALUE;
    }
    
    protected int moveDown(int speed){
        if(worldY+speed+gameWorld.tileSize<(gameWorld.worldRow)*gameWorld.tileSize && !gameWorld.map.checkTile(this, direction)) {
            if(gameWorld.objectHandler.checkObject(this, direction)!=700) {
                int entityCollision = gameWorld.entityManager.checkEntityCollision(this, direction);
                if(entityCollision!=999) return entityCollision; // collision happened;
                worldY += speed;
            }
        }
        return DEFAULT_MOVE_VALUE;
    }

    protected int moveLeft(int speed){
        if(worldX-speed>0 && !gameWorld.map.checkTile(this, direction)) {
            if(gameWorld.objectHandler.checkObject(this, direction)!=700) {
                int entityCollision = gameWorld.entityManager.checkEntityCollision(this, direction);
                if(entityCollision!=999) return entityCollision; // collision happened;
                worldX -= speed;
            }
        }
        return DEFAULT_MOVE_VALUE;
    }

    protected int moveRight(int speed){
        if(worldX+speed+gameWorld.tileSize<(gameWorld.worldCol)*gameWorld.tileSize && !gameWorld.map.checkTile(this, direction)) {
            if(gameWorld.objectHandler.checkObject(this, direction)!=700) {
                int entityCollision = gameWorld.entityManager.checkEntityCollision(this, direction);
                if(entityCollision!=999) return entityCollision; // collision happened;
                worldX += speed;
            }
        }
        return DEFAULT_MOVE_VALUE;
    }

    public void interact(){

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
        if(attackCounter > 25){
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
