package util;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utility {
    public Font arial_20B = new Font("Arial", Font.BOLD, 20);
    public Font arial_36 = new Font("Arial", Font.PLAIN, 36);
    public Font arial_40 = new Font("Arial", Font.PLAIN, 40);
    public Font arial_60B = new Font("Arial", Font.BOLD, 60);

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public int getTextLength(Graphics2D g, String text){
        return (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
    }
}
