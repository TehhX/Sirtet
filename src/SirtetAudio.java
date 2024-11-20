import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
public class SirtetAudio {
    /**
     * This class loads and plays audio clips when required, such as the background track on launch, and individual
     * sfx when a block is placed, for example.
     */
    static Clip bgmClip;
    public SirtetAudio() {
        try {
            bgmClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Assets/bgm.wav"));
            bgmClip.open(inputStream);
            FloatControl fc = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue((float) ((Math.log(SaveData.bgmVolume / 9.0)) / Math.log(10.0) * 20.0));
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateBGMVolume() {
        FloatControl fc = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue((float) ((Math.log(SaveData.bgmVolume / 9.0)) / Math.log(10.0) * 20.0));
    }
    public static void playAudio(String filePath) {
        try {
            Clip sfxClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Assets/" + filePath));
            sfxClip.open(inputStream);
            FloatControl fc = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue((float)((Math.log(SaveData.sfxVolume / 9.0)) / Math.log(10.0) * 20.0));
            sfxClip.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}