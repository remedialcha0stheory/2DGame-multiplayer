package entity;

import main.GamePanel;

public class NPC_OldMan extends NPC{
    String[] dialogue;
    int dialogue_index = 0;
    int dialogue_count = 4;
    public NPC_OldMan(GamePanel gamePanel, int x, int y){
        super(gamePanel);
        speed = 1;
        maxLife = 10;
        life = maxLife;
        loadCharacterImage("/npc/oldman/oldman");
        this.worldX = x*gamePanel.tileSize;
        this.worldY = y*gamePanel.tileSize;
        dialogue = new String[4];
        setDialogue();
    }

    private void setDialogue(){
        dialogue[0] = "Hello, lad.";
        dialogue[1] = "I was a young man\nlike yourself once.";
        dialogue[2] = "Trying to survive this island,\nbut with little success.";
        dialogue[3] = "I hope you succeed.";
    }

    public boolean interact(){
        gamePanel.ui.setMessage(dialogue[dialogue_index++]);
        if(dialogue_index==4){
            dialogue_index = 0;
        }
        return true;
    }

    public int getMessageCount(){
        return dialogue_count;
    }

    public int getMessageIndex(){
        return dialogue_index;
    }
}
