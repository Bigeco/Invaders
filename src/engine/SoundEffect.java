package engine;

import java.io.File;
import java.util.TimerTask;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.util.Timer;


public class SoundEffect {

    File shipShootingSound = new File("sound/soundEffect/Shipshooting.wav");
    File shipDestructionSound = new File("sound/soundEffect/Shipdestruction.wav");
    File shipCollisionSound = new File("sound/soundEffect/Shipcollision.wav");
    File enemyDestructionSound = new File("sound/soundEffect/Enemydestruction.wav");
    File enemyShootingSound = new File("sound/soundEffect/Enemyshooting.wav");
    File buttonClickSound = new File("sound/soundEffect/ButtonClick.wav");
    File spaceButtOnSound = new File("sound/soundEffect/SpaceButton.wav");
    File stageChangeSound = new File("sound/soundEffect/StageChange.wav");
    File initialStartSound = new File("sound/soundEffect/initialStart.wav");
    File startSound = new File("sound/soundEffect/start.wav");
    
    File useCoinSound = new File("sound/soundEffect/usecoin.wav");
    

    File enhancedSound = new File("sound/soundEffect/enhancedDestruction.wav");
    /**
     * Play ship's shooting sound
     */
    public void playStageChangeSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(stageChangeSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Stage change sound does not played."); }
    }

    public void playShipShootingSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipShootingSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Missile shooting sound does not played."); }
    }

    /**
     * Play ship's destruction sound
     */
    public void playShipDestructionSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipDestructionSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Ship's destroyed sound does not played."); }
    }

    /**
     * Play ship's collision sound
     */
    public void playShipCollisionSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipCollisionSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Ship's attacked sound does not played."); }
    }
    /**
     * Play enemy's destruction sound
     */
    public void playEnemyDestructionSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(enemyDestructionSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Enemy's destroyed sound does not played."); }
    }

    /**
     * Play enemy's shooting sound
     */
    public void playEnemyShootingSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(enemyShootingSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Enemy's bullet sound does not played."); }
    }
    // sound for button moving sound
    public void playButtonClickSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(buttonClickSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Button Click sound error."); }
    }
    // sound for spacebar key
    public void playSpaceButtonSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(spaceButtOnSound));
            clip.start();

            Thread.sleep(1);
        } catch (Exception e) {
            System.err.println("SOUND ERROR: Space Key sound error.");
        }
    }

    public void playUseCoinSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(useCoinSound));
            clip.start();

            Thread.sleep(1);
        } catch (Exception e) {
            System.err.println("SOUND ERROR: useCoin sound error.");
        }
    }

    public void playEnhancedSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(enhancedSound));
            clip.start();

            Thread.sleep(1);
        } catch (Exception e) {
            System.err.println("SOUND ERROR: Enhanced Sound error.");
        }
    }

    public void soundEffect_play(){

    }
    public void soundEffect_stop(){

    }


    /**
     * Play initial game start sound
     *
     *
     */
    public void initialStartSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(initialStartSound));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Play game start sound
     *
     *
     */
    public void startSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(startSound));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playInitialStartSoundWithDelay() {
        Timer timer = new Timer();
        timer.schedule(new InitialStartSoundTask(), 2000); // 2000ms = 2 sec
    }

    class InitialStartSoundTask extends TimerTask {
        @Override
        public void run() {
            initialStartSound();
        }
    }


    /**
     * Play Enemyshipspecial's destruction sound
     */
    public void enemyShipSpecialDestructionSound(){
        try {
            String soundFilePath = "sound/soundEffect/enemyshipspecialdestructionsound.wav";
            File soundFile = new File(soundFilePath).getAbsoluteFile();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



//    public void play() {
//        if (clip != null) {
//            clip.setFramePosition(0);
//            clip.start();
//        }
//    }
//
//    public void stop() {
//        if (clip != null) {
//            clip.stop();
//        }
//    }


}
