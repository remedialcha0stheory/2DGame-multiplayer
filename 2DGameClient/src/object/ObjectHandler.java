package object;

import java.awt.Graphics2D;
import java.util.LinkedHashMap;

import main.GamePanel;
import main.Sound;

public class ObjectHandler {
    public LinkedHashMap<Integer, Object> object;
    GamePanel gamePanel;
    int objectCount = 0;
    private Sound sound = new Sound();

    public ObjectHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        object = new LinkedHashMap<>();
    }

    public void insertObject(Object obj) {
        object.put(obj.getId(), obj);
    }

    public Object getObject(int object_id){
        return object.get(object_id);
    }

    public void removeObject(int object_id){
        object.remove(object_id);
    }

    public void playSoundEffect(int object_id){
        switch(getObject(object_id).getClass().getSimpleName()){
            case "Key":
                sound.setClip(1);
                sound.startClip();
                break;
            case "Door":
                sound.setClip(4);
                sound.startClip();
                break;
            // case "Chest":
            //     sound.setClip(2);
            //     sound.startClip();
            //     break;
        }
    }
    public void draw(Graphics2D g) {
        for (Object i: object.values()) {
            if (i == null) {
                continue;
            }

            int screenX = gamePanel.player.screenX - gamePanel.player.worldX + i.worldX*gamePanel.tileSize;
            int screenY = gamePanel.player.screenY - gamePanel.player.worldY + i.worldY*gamePanel.tileSize;

            g.drawImage(i.image, screenX, screenY, null);
        }
    }
}
