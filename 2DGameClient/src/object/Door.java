package object;

import java.io.IOException;

import javax.imageio.ImageIO;
import main.GamePanel;
import util.Utility;

public class Door extends Object {
    public Door(int x, int y, GamePanel gamePanel){
        super(gamePanel);
        collision = true;
        Utility util = new Utility();
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            image = util.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch(IOException e){
            e.printStackTrace();
        }
        worldX = x;
        worldY = y;
    }
}
