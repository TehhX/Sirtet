import javax.sound.sampled.*;
import java.io.File;

/**
 * This class loads and plays audio clips when required, such as the background track on launch, and individual
 * sfx when a block is placed, for example.
 */
class SirtetAudio {
    static Clip backgroundMusic;

    public SirtetAudio() throws Exception {
        backgroundMusic = AudioSystem.getClip();
        AudioInputStream stream = AudioSystem.getAudioInputStream(new File("Assets/BGM.wav"));
        backgroundMusic.open(stream);
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        updateBGMVolume();
        backgroundMusic.start();
    }

    public static void updateBGMVolume() {
        FloatControl fc = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue((float) ((Math.log(SaveData.bgmVolume / 9.0)) / Math.log(10.0) * 20.0));
    }

    public static void playAudio(AudioID filePath) {
        double oldNum = System.nanoTime();
        new Thread(() -> {
            try {
                Clip sfxClip = AudioSystem.getClip();
                AudioInputStream stream = AudioSystem.getAudioInputStream(new File("Assets/" + filePath + ".wav"));
                sfxClip.open(stream);
                sfxClip.addLineListener(event -> {
                    if (LineEvent.Type.START.equals(event.getType())) {
                        System.out.printf("%.2f\n", (System.nanoTime() - oldNum) * Math.pow(10, -6));
                    }
                    else if (LineEvent.Type.STOP.equals(event.getType())) {
                        event.getLine().close();
                    }
                });
                FloatControl fc = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
                fc.setValue((float) ((Math.log(SaveData.sfxVolume / 9.0)) / Math.log(10.0) * 20.0));
                sfxClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}