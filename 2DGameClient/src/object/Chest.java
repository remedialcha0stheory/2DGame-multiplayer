package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;
import main.GamePanel;
import util.Utility;

public class Chest extends Object {
    public Chest(int x, int y, GamePanel gamePanel){
        super(gamePanel);
        collision = false;
        Utility util = new Utility();
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            image = util.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch(IOException e){
            e.printStackTrace();
        }
        worldX = x;
        worldY = y;
    }

    public boolean interact(Player player){
        player.taskCompleted = true;
        return true;
    }
}
