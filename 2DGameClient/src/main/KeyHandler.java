package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed;
    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_T:
                if(gamePanel.gameState==gamePanel.PLAY_STATE){
                    gamePanel.gameState = gamePanel.PAUSE_STATE;
                }
                else if(gamePanel.gameState==gamePanel.PAUSE_STATE){
                    gamePanel.gameState = gamePanel.PLAY_STATE;
                }
                break;
            case KeyEvent.VK_ENTER:
                if(gamePanel.player.collisionOn){    
                    if(gamePanel.gameState==gamePanel.PLAY_STATE){
                        if(gamePanel.player.collidingEntity.interact()) gamePanel.gameState = gamePanel.DIALOGUE_STATE;
                        else{
                            enterPressed = true;
                        }
                    }
                }
                else{
                    enterPressed = true;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if(gamePanel.gameState==gamePanel.DIALOGUE_STATE){
                    gamePanel.gameState = gamePanel.PLAY_STATE;
                }
                break;
            case KeyEvent.VK_SPACE:
                if(gamePanel.gameState==gamePanel.PLAY_STATE){
                    spacePressed = true;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false;
                break;
        }
    }
}