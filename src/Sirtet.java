import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

/**
 * This class loads all data, images, fonts, and audio files for use in the game before even making the main JFrame
 * visible. This ensures that no time or resources are wasted mid-game to load assets. It also initializes and declares
 * static final variables FRAME_SIZE_X and Y for use with JPanels and the main JFrame. If any of these assets cannot
 * be loaded, the program force-quits before even displaying the frame, to not waste time. However, if an error occurs,
 * a message pane will be displayed before exiting. */
public class Sirtet {
    static BufferedImage[] gameplaySceneImages = new BufferedImage[8];
    static BufferedImage[] menuImages = new BufferedImage[2];
    static BufferedImage icon;

    static ImageObserver observer = (a, b, c, d, e, f) -> false;

    static Clip[] audioClips = new Clip[AudioID.values().length];

    public static void main(String[] args) {
        try {
            loadFonts();
            loadImages();
            loadAudio();
            new SaveData();
            new SirtetAudio();
            new SirtetWindow();
        }
        // If a file does not exist/cannot be found etc.
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Load Error, Check Stack Trace");
            System.exit(1);
        }
    }

    /// Loads all images into memory
    public static void loadImages() throws Exception {
        icon = ImageIO.read(new File("Assets/Icon.png"));

        gameplaySceneImages[7] = ImageIO.read(new File("Assets/GameplayScene.png"));

        for (int i = 0; i < 7; i++)
            gameplaySceneImages[i] = ImageIO.read(new File("Assets/" + BlockID.values()[i] + " Piece.png"));

        for (int i = 0; i < ImageID.values().length; i++)
            menuImages[i] = ImageIO.read(new File("Assets/" + ImageID.values()[i] + ".png"));
    }

    /// Creates the Silkscreen font to use in-game
    public static void loadFonts() throws Exception {
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
            Font.createFont(Font.TRUETYPE_FONT,
            new File("Assets/Silkscreen-Regular.ttf"))
        );
    }

    /// Creates audio clips for all audio files in-game
    public static void loadAudio() throws Exception {
        for (int i = 0; i < audioClips.length; i++) {
            audioClips[i] = AudioSystem.getClip();
            audioClips[i].open(AudioSystem.getAudioInputStream(new File("Assets/" + AudioID.values()[i] + ".wav")));
        }
    }
}