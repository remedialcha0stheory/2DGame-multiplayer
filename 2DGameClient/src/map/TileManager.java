package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;

import main.GamePanel;
import util.Utility;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;
    public int worldCol, worldRow;

    public TileManager(GamePanel gamePanel, int mapNum, int worldCol, int worldRow){
        this.gamePanel = gamePanel;
        tile = new Tile[50];
        this.worldCol = worldCol;
        this.worldRow = worldRow;
        mapTileNum = new int[worldRow][worldCol];

        loadTileImages();
        loadMap(mapNum);
    }

    private void loadTileImages(){

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
        Utility util = new Utility();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles_new/"+imagePath+".png"));
            tile[index].image = util.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;
            tile[index].name = imagePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        // System.out.println(mapTileNum[21][23]);
    }

    public void draw(Graphics2D g){
        int playerX = gamePanel.player.worldX; //23*tile
        int playerY = gamePanel.player.worldY; // 21*tile

        int screenWidth = gamePanel.screenWidth; // 16
        int screenHeight = gamePanel.screenHeight; // 12

        int topY = playerY - screenHeight/2; // 15*tile
        int bottomY = playerY + screenHeight/2; // 27*tile 
        int leftX = playerX - screenWidth/2; // 15*tile
        int rightX = playerX + screenWidth/2; // 31*tile 
        
        int Y1 = topY/gamePanel.tileSize; // 15
        int X1 = leftX/gamePanel.tileSize; //15
        int marginX = leftX%gamePanel.tileSize;
        int marginY = topY%gamePanel.tileSize;

        int endX = 17;
        int endY = 13;

        if(topY < 0){
            Y1 = 0;
            marginY = 0;
        }
        if(bottomY >= (worldRow)*gamePanel.tileSize){
            Y1 -= (bottomY-(worldRow)*gamePanel.tileSize)/gamePanel.tileSize;
            marginY=0;
            endY--;

        }
        if(leftX < 0){
            X1 = 0;
            marginX = 0;
        }
        if(rightX >= (worldCol)*gamePanel.tileSize){
            X1 -= (rightX - (worldCol)*gamePanel.tileSize)/gamePanel.tileSize;
            marginX = 0;
            endX--;
        }
        // System.out.println(X1);

        for(int i=X1; i<X1+endX; i++){ // 15 -> 31
            for(int j=Y1; j<Y1+endY; j++){ // 15 -> 27 
                g.drawImage(tile[mapTileNum[j][i]].image,(i-X1)*gamePanel.tileSize - marginX, (j-Y1)*gamePanel.tileSize - marginY, null);
            }
        }
    }
}
