package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import util.Utility;

public class UI {
    GamePanel gamePanel;
    Graphics2D g;
    Utility util = new Utility();
    final int unit;

    String currMessage = "Hello!\nHow are you?";
    int messageX = 24+72;
    int messageY = 60+48;

    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        unit = gamePanel.tileSize;
    }

    public void setMessage(String message){
        this.currMessage = message;
    }

    public void draw(Graphics2D g){
        this.g = g;

        if(gamePanel.gameState==gamePanel.PLAY_STATE){
            if(gamePanel.player.collisionOn) drawLegend();
            drawPlayerLife();
        }
        else if(gamePanel.gameState==gamePanel.PAUSE_STATE){
            drawPauseScreen();
        }
        else if(gamePanel.gameState==gamePanel.DIALOGUE_STATE){
            drawDialogueScreen();
            int counter = 0;
            g.setFont(util.arial_36);
            for(String line: currMessage.split("\n")){
                g.drawString(line, messageX, messageY + counter*gamePanel.tileSize);
                counter++;
            }
        }
        else if(gamePanel.gameState==gamePanel.GAMEOVER_STATE){
            drawGameOverScreen();
        }
        else if(gamePanel.gameState==gamePanel.GAMEWON_STATE){
            drawGameWonScreen();
        }
    }

    private void drawPlayerLife(){
        g.setColor(Color.WHITE);
        g.fillRoundRect(unit, unit, 3*unit, unit, 5, 5);
        g.setColor(Color.RED);
        int width = 3*unit*gamePanel.player.life/gamePanel.player.maxLife;
        g.fillRoundRect(unit, unit, width, unit, 5, 5);
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.BLACK);
        g.drawRoundRect(unit, unit, 3*unit, unit, 5, 5);
    }

    private void drawLegend(){
        g.setFont(util.arial_20B);
        g.setColor(Color.WHITE);
        g.drawString("Press ENTER to interact", gamePanel.tileSize, 8*gamePanel.tileSize);
    }

    private void drawPauseScreen(){
        g.setFont(util.arial_60B);
        g.setColor(Color.WHITE);
        String text = "PAUSED";
        int y = gamePanel.screenHeight/2;
        int x = gamePanel.screenWidth/2 - util.getTextLength(g, text)/2;

        g.drawString(text, x, y);
    }

    private void drawGameOverScreen(){
        g.setFont(util.arial_60B);
        g.setColor(Color.WHITE);
        String text = "GAME OVER!";
        int y = gamePanel.screenHeight/2;
        int x = gamePanel.screenWidth/2 - util.getTextLength(g, text)/2;

        g.drawString(text, x, y);
    }

    private void drawGameWonScreen(){
        g.setFont(util.arial_60B);
        g.setColor(Color.WHITE);
        String text = "CONGRATULATIONS!";
        int y = gamePanel.screenHeight/2;
        int x = gamePanel.screenWidth/2 - util.getTextLength(g, text)/2;

        g.drawString(text, x, y);
    }

    private void drawDialogueScreen(){
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(72, gamePanel.tileSize, 13*gamePanel.tileSize, 4*gamePanel.tileSize, 20, 20);
        g.setColor(new Color(255, 255, 255, 255));
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(70, gamePanel.tileSize-2, 13*gamePanel.tileSize+4, 4*gamePanel.tileSize+4, 20, 20);
    }
}
