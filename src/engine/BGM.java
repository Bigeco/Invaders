package engine;

import javax.sound.sampled.*;
import java.io.File;

public class BGM {
    /** Add variable of bgmClip - OutGame*/
    private Clip outGame_bgmCLip; 
    /** Add variable of bgmClip - inGame*/
    private Clip inGame_bgmCLip;
    /** Add variable of bgmClip - enemy Ship*/
    private Clip enemyShipSpecialbgmCLip;
    /** Add variable of original volume*/
    private float originalVolume;

    File enemyShipSpecialAppearBGM = new File("sound/BackGroundMusic/enemyshipspecial.wav");

    public BGM() {
        try {
            String outGame_bgm_FilePATH = "sound/BackGroundMusic/gamescreen_bgm.wav";
            File outGame_bgm = new File(outGame_bgm_FilePATH).getAbsoluteFile();
            AudioInputStream outGame_Stream = AudioSystem.getAudioInputStream(outGame_bgm);
            AudioFormat outGame_Format = outGame_Stream.getFormat();
            DataLine.Info outGame_Info = new DataLine.Info(Clip.class, outGame_Format);

            outGame_bgmCLip = (Clip) AudioSystem.getLine(outGame_Info);
            outGame_bgmCLip.open(outGame_Stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String inGame_bgm_FilePATH = "sound/BackGroundMusic/outside_screen_bgm.wav";
            File inGame_bgm = new File(inGame_bgm_FilePATH).getAbsoluteFile();
            AudioInputStream inGame_Stream = AudioSystem.getAudioInputStream(inGame_bgm);
            AudioFormat inGame_Format = inGame_Stream.getFormat();
            DataLine.Info inGame_Info = new DataLine.Info(Clip.class, inGame_Format);

            inGame_bgmCLip = (Clip) AudioSystem.getLine(inGame_Info);
            inGame_bgmCLip.open(inGame_Stream);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Play enemyShipSpecial appear BGM
     */
    public void enemyShipSpecialBGM_play(){
        try {
            AudioInputStream enemyShipSpecialStream = AudioSystem.getAudioInputStream(enemyShipSpecialAppearBGM);
            AudioFormat enemyShipSpecialFormat = enemyShipSpecialStream.getFormat();
            DataLine.Info enemyShipSpecialInfo = new DataLine.Info(Clip.class, enemyShipSpecialFormat);

            enemyShipSpecialbgmCLip = (Clip) AudioSystem.getLine(enemyShipSpecialInfo);
            enemyShipSpecialbgmCLip.open(enemyShipSpecialStream);
            bgm_volumeDown();
            enemyShipSpecialbgmCLip.start();
            enemyShipSpecialbgmCLip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Stop enemyShipSpecial appear BGM
     */
    public void enemyShipSpecialBGM_stop(){
        try {
            if (enemyShipSpecialbgmCLip != null && enemyShipSpecialbgmCLip.isRunning()) {
                enemyShipSpecialbgmCLip.stop();
                bgm_volumeUp();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * BGM volume down
     */
    public void bgm_volumeDown(){
        try {
            FloatControl control = (FloatControl) inGame_bgmCLip.getControl(FloatControl.Type.MASTER_GAIN);
            originalVolume = control.getValue();
            control.setValue(-10.0f);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * BGM volume up
     */
    public void bgm_volumeUp(){
        try {
            FloatControl control = (FloatControl) inGame_bgmCLip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(originalVolume);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void inGameBGM_play(){
//            bgm_volumeDown();
        inGame_bgmCLip.start();
        inGame_bgmCLip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void outGameBGM_play() {

        if (outGame_bgmCLip != null && !outGame_bgmCLip.isRunning()) {
            outGame_bgmCLip.start();
            outGame_bgmCLip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

//    public void bgm_play() {
//        // BGM을 재생합니다.
//        if (bgmClip != null && !bgmClip.isRunning()) {
//            bgmClip.start();
//            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
//        }
//    }

    public void outGameBGM_stop() {
        // BGM 재생을 중지합니다.
        try {
            if (outGame_bgmCLip != null && outGame_bgmCLip.isRunning()) {
                outGame_bgmCLip.stop();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void inGameBGM_stop() {
        // BGM 재생을 중지합니다.
        try {
            if (inGame_bgmCLip != null && inGame_bgmCLip.isRunning()) {
                inGame_bgmCLip.stop();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}