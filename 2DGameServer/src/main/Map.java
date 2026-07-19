package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import entity.Entity;

public class Map {
    int[][] mapTileNum;
    boolean[] tile;
    GameWorld gameWorld;
    public int worldCol, worldRow;

    public Map(GameWorld gameWorld, int mapNum, int worldCol, int worldRow){
        this.gameWorld = gameWorld;
        tile = new boolean[50];
        this.worldCol = worldCol;
        this.worldRow = worldRow;
        mapTileNum = new int[worldRow][worldCol];

        loadTiles();
        loadMap(mapNum);
    }

    private void loadTiles(){

        // setup(0, "grass", false);
        // setup(1, "wall", true);
        // setup(2, "water", true);
        // setup(3, "earth", false);
        // setup(4, "tree", true);
        // setup(5, "sand", false);


        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        // --------------------------------------------------

        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
        setup(42, "hut", false);
        setup(43, "floor01", false);
        setup(44, "table01", true);
    }

    private void setup(int index, String imagePath, boolean collision){
            tile[index] = collision;
    }

    private void loadMap(int mapNum){
        String filename = String.format("res/map/worldV2.txt", mapNum);
        File file = new File(filename);
        Scanner s = null;
        try {
            s = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i=0; i<worldRow; i++){
            for(int j=0; j<worldCol; j++){
                mapTileNum[i][j] = s.nextInt();
            }
        }
    }

    public boolean checkTile(Entity entity, String direction){
        int topY = entity.worldY + entity.solidArea.y;
        int bottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int leftX = entity.worldX + entity.solidArea.x;
        int rightX = entity.worldX + entity.solidArea.x  + entity.solidArea.width;

        switch(direction){
            case "up":
                int projectedTopY = topY - entity.speed;
                int tileY = projectedTopY/gameWorld.tileSize;
                int tileX1 = leftX/gameWorld.tileSize;
                int tileX2 = rightX/gameWorld.tileSize;
                boolean collisionTopLeft = tile[mapTileNum[tileY][tileX1]];
                boolean collisionTopRight = tile[mapTileNum[tileY][tileX2]];
                return collisionTopLeft || collisionTopRight;
            case "down":
                int projectedBottomY = bottomY + entity.speed;
                tileY = projectedBottomY/gameWorld.tileSize;
                tileX1 = leftX/gameWorld.tileSize;
                tileX2 = rightX/gameWorld.tileSize;
                boolean collisionBottomLeft = tile[mapTileNum[tileY][tileX1]];
                boolean collisionBottomRight = tile[mapTileNum[tileY][tileX2]];
                return collisionBottomLeft || collisionBottomRight;
            case "left":
                int projectedLeftX = leftX - entity.speed;
                int tileX = projectedLeftX/gameWorld.tileSize;
                int tileY1 = topY/gameWorld.tileSize;
                int tileY2 = bottomY/gameWorld.tileSize;
                collisionTopLeft = tile[mapTileNum[tileY1][tileX]];
                collisionBottomLeft = tile[mapTileNum[tileY2][tileX]];
                return collisionTopLeft || collisionBottomLeft;
            case "right":
                int projectedRightX = rightX + entity.speed;
                tileX = projectedRightX/gameWorld.tileSize;
                tileY1 = topY/gameWorld.tileSize;
                tileY2 = bottomY/gameWorld.tileSize;
                collisionTopRight = tile[mapTileNum[tileY1][tileX]];
                collisionBottomRight = tile[mapTileNum[tileY2][tileX]];
                return collisionTopRight || collisionBottomRight;
        }
        return false;
    }

    public class Entry<T1, T2> {
    }
}
