import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * This class loads and plays audio clips when required, such as the background track on launch, and individual
 * sfx when a block is placed, for example.
 */
class SirtetAudio {
    public SirtetAudio() {
        Sirtet.audioClips[0].loop(Clip.LOOP_CONTINUOUSLY);
        updateBGMVolume();
        Sirtet.audioClips[0].start();
    }

    public static void updateBGMVolume() {
        FloatControl fc = (FloatControl) Sirtet.audioClips[0].getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue((float) ((Math.log(SaveData.bgmVolume / 9.0)) / Math.log(10.0) * 20.0));
    }

    public static void playAudio(AudioID audioID) {
        new Thread(() -> {
            Clip clip = Sirtet.audioClips[audioID.ordinal()];
            clip.stop();
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue((float) ((Math.log(SaveData.sfxVolume / 9.0)) / Math.log(10.0) * 20.0));
            clip.setMicrosecondPosition(0);
            clip.start();
        }).start();
    }
}