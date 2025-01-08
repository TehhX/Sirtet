import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * This class loads and plays audio clips when required, such as the background track on launch, and individual
 * sfx when a block is placed, for example. Will start the BGM when constructor is called. */
class SirtetAudio {
    public SirtetAudio() {
        Sirtet.audioClips[AudioID.BackgroundMusic.ordinal()].loop(Clip.LOOP_CONTINUOUSLY);
        updateBGMVolume();

        Sirtet.audioClips[AudioID.BackgroundMusic.ordinal()].start();
    }

    public static void updateBGMVolume() {
        FloatControl fc = (FloatControl) Sirtet.audioClips[AudioID.BackgroundMusic.ordinal()].getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue((float) ((Math.log(SaveData.bgmVolume / 9.0)) / Math.log(10.0) * 20.0));
    }

    /// For playing sfx. No class object needed so long as class already called from PSVM.
    public static void playAudio(AudioID sfxID) {
        Sirtet.audioClips[sfxID.ordinal()].stop();

        FloatControl fc = (FloatControl) Sirtet.audioClips[sfxID.ordinal()].getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue((float) ((Math.log(SaveData.sfxVolume / 9.0)) / Math.log(10.0) * 20.0));

        Sirtet.audioClips[sfxID.ordinal()].setMicrosecondPosition(0);
        Sirtet.audioClips[sfxID.ordinal()].start();
    }
}