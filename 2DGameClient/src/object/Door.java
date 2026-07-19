package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;
import main.GamePanel;
import util.Utility;

public class Door extends Object {
    private boolean open = false;
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

    public boolean interact(Player player){
        if(open){
            return true;
        }
        if(player.keysCount>0){
            player.keysCount--;
            open = true;
            collision = false;
            return true;
        }
        return false;
    }
}
