package engine;

import javax.sound.sampled.*;
import java.io.File;

public class BGM {
    /** Add variable of bgmClip - OutGame*/
    private Clip outGameBGMCLip; 
    /** Add variable of bgmClip - inGame*/
    private Clip inGameBGMCLip;
    /** Add variable of bgmClip - enemy Ship*/
    private Clip enemyShipSpecialbgmCLip;
    /** Add variable of original volume*/
    private float originalVolume;

    File enemyShipSpecialAppearBGM = new File("sound/BackGroundMusic/enemyshipspecial.wav");

    public BGM() {
        try {
            String outGameBGMFilePATH = "sound/BackGroundMusic/gamescreen_bgm.wav";
            File outGameBGM = new File(outGameBGMFilePATH).getAbsoluteFile();
            AudioInputStream outGameStream = AudioSystem.getAudioInputStream(outGameBGM);
            AudioFormat outGameFormat = outGameStream.getFormat();
            DataLine.Info outGameInfo = new DataLine.Info(Clip.class, outGameFormat);

            outGameBGMCLip = (Clip) AudioSystem.getLine(outGameInfo);
            outGameBGMCLip.open(outGameStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String inGameBGMFilePATH = "sound/BackGroundMusic/outside_screen_bgm.wav";
            File inGameBGM = new File(inGameBGMFilePATH).getAbsoluteFile();
            AudioInputStream inGameStream = AudioSystem.getAudioInputStream(inGameBGM);
            AudioFormat inGameFormat = inGameStream.getFormat();
            DataLine.Info inGameInfo = new DataLine.Info(Clip.class, inGameFormat);

            inGameBGMCLip = (Clip) AudioSystem.getLine(inGameInfo);
            inGameBGMCLip.open(inGameStream);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Play enemyShipSpecial appear BGM
     */
    public void enemyShipSpecialBGMplay(){
        try {
            AudioInputStream enemyShipSpecialStream = AudioSystem.getAudioInputStream(enemyShipSpecialAppearBGM);
            AudioFormat enemyShipSpecialFormat = enemyShipSpecialStream.getFormat();
            DataLine.Info enemyShipSpecialInfo = new DataLine.Info(Clip.class, enemyShipSpecialFormat);

            enemyShipSpecialbgmCLip = (Clip) AudioSystem.getLine(enemyShipSpecialInfo);
            enemyShipSpecialbgmCLip.open(enemyShipSpecialStream);
            bgmVolumeDown();
            enemyShipSpecialbgmCLip.start();
            enemyShipSpecialbgmCLip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Stop enemyShipSpecial appear BGM
     */
    public void enemyShipSpecialBGMstop(){
        try {
            if (enemyShipSpecialbgmCLip != null && enemyShipSpecialbgmCLip.isRunning()) {
                enemyShipSpecialbgmCLip.stop();
                bgmVolumeUp();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * BGM volume down
     */
    public void bgmVolumeDown(){
        try {
            FloatControl control = (FloatControl) inGameBGMCLip.getControl(FloatControl.Type.MASTER_GAIN);
            originalVolume = control.getValue();
            control.setValue(-10.0f);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * BGM volume up
     */
    public void bgmVolumeUp(){
        try {
            FloatControl control = (FloatControl) inGameBGMCLip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(originalVolume);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void inGameBGMplay(){
//            bgmVolumeDown();
        inGameBGMCLip.start();
        inGameBGMCLip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void outGameBGMplay() {

        if (outGameBGMCLip != null && !outGameBGMCLip.isRunning()) {
            outGameBGMCLip.start();
            outGameBGMCLip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

//    public void bgm_play() {
//        // BGM을 재생합니다.
//        if (bgmClip != null && !bgmClip.isRunning()) {
//            bgmClip.start();
//            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
//        }
//    }

    public void outGameBGMstop() {
        // BGM 재생을 중지합니다.
        try {
            if (outGameBGMCLip != null && outGameBGMCLip.isRunning()) {
                outGameBGMCLip.stop();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void inGameBGMstop() {
        // BGM 재생을 중지합니다.
        try {
            if (inGameBGMCLip != null && inGameBGMCLip.isRunning()) {
                inGameBGMCLip.stop();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}