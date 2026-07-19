package main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    Clip clip;
    URL[] soundURL;

    public Sound(){
        soundURL = new URL[30];
        soundURL[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[3] = getClass().getResource("/sounds/powerup.wav");
        soundURL[4] = getClass().getResource("/sounds/unlock.wav");
    }

    public void setClip(int clip_id){
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL[clip_id]);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void startClip(){
        clip.start();
    }
    public void loopClip(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stopClip(){
        clip.stop();
    }
}
