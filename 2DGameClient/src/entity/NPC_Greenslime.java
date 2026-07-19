package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class NPC_Greenslime extends NPC {
    public NPC_Greenslime(GamePanel gamePanel, int x, int y) {
        super(gamePanel);
        solidArea = new Rectangle(3, 18, 42, 30);
        RECT_X = 3;
        RECT_Y = 18;
        RECT_WIDTH = 42;
        RECT_HEIGHT = 30;
        maxLife = 8;
        speed = 1;
        life = maxLife;
        contactAttack = 1;
        loadCharacterImage();
        worldX = x * gamePanel.tileSize;
        worldY = y * gamePanel.tileSize;
    }

    private void loadCharacterImage() {
        up1 = setupImage("/npc/greenslime/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setupImage("/npc/greenslime/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setupImage("/npc/greenslime/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setupImage("/npc/greenslime/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setupImage("/npc/greenslime/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setupImage("/npc/greenslime/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setupImage("/npc/greenslime/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setupImage("/npc/greenslime/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void collide(Entity e) {

    }
}
