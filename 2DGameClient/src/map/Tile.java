package map;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;
    public String name;
    public Rectangle solidArea = new Rectangle(0, 0, 32, 32);
}
