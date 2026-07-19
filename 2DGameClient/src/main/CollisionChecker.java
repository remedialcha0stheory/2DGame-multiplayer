package main;

import entity.Entity;

public class CollisionChecker {
    public GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public boolean checkTile(Entity entity, String direction){
        int topY = entity.worldY + entity.solidArea.y;
        int bottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int leftX = entity.worldX + entity.solidArea.x;
        int rightX = entity.worldX + entity.solidArea.x  + entity.solidArea.width;

        switch(direction){
            case "up":
                int projectedTopY = topY - entity.speed;
                int tileY = projectedTopY/gamePanel.tileSize;
                int tileX1 = leftX/gamePanel.tileSize;
                int tileX2 = rightX/gamePanel.tileSize;
                boolean collisionTopLeft = gamePanel.tileManager.tile[gamePanel.tileManager.mapTileNum[tileY][tileX1]].collision;
                boolean collisionTopRight = gamePanel.tileManager.tile[gamePanel.tileManager.mapTileNum[tileY][tileX2]].collision;
                return collisionTopLeft || collisionTopRight;
            case "down":
                int projectedBottomY = bottomY + entity.speed;
                tileY = projectedBottomY/gamePanel.tileSize;
                tileX1 = leftX/gamePanel.tileSize;
                tileX2 = rightX/gamePanel.tileSize;
                boolean collisionBottomLeft = gamePanel.tileManager.tile[gamePanel.tileManager.mapTileNum[tileY][tileX1]].collision;
                boolean collisionBottomRight = gamePanel.tileManager.tile[gamePanel.tileManager.mapTileNum[tileY][tileX2]].collision;
                return collisionBottomLeft || collisionBottomRight;
            case "left":
                int projectedLeftX = leftX - entity.speed;
                int tileX = projectedLeftX/gamePanel.tileSize;
                int tileY1 = topY/gamePanel.tileSize;
                int tileY2 = bottomY/gamePanel.tileSize;
                collisionTopLeft = gamePanel.tileManager.tile[gamePanel.tileManager.mapTileNum[tileY1][tileX]].collision;
                collisionBottomLeft = gamePanel.tileManager.tile[gamePanel.tileManager.mapTileNum[tileY2][tileX]].collision;
                return collisionTopLeft || collisionBottomLeft;
            case "right":
                int projectedRightX = rightX + entity.speed;
                tileX = projectedRightX/gamePanel.tileSize;
                tileY1 = topY/gamePanel.tileSize;
                tileY2 = bottomY/gamePanel.tileSize;
                collisionTopRight = gamePanel.tileManager.tile[gamePanel.tileManager.mapTileNum[tileY1][tileX]].collision;
                collisionBottomRight = gamePanel.tileManager.tile[gamePanel.tileManager.mapTileNum[tileY2][tileX]].collision;
                return collisionTopRight || collisionBottomRight;
        }
        return false;
    }
}
