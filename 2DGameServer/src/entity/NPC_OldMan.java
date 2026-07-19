package entity;

import main.GameWorld;

public class NPC_OldMan extends NPC{
    String[] dialogue;
    int dialogue_index = 0;
    int dialogue_count = 4;
    public NPC_OldMan(GameWorld gameWorld, int x, int y){
        super(gameWorld);
        speed = 1;
        maxLife = 10;
        life = maxLife;
        this.worldX = x*gameWorld.tileSize;
        this.worldY = y*gameWorld.tileSize;
        dialogue = new String[4];
    }

    public int getMessageCount(){
        return dialogue_count;
    }

    public int getMessageIndex(){
        return dialogue_index;
    }
}
