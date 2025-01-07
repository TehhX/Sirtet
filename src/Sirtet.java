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
    static final Font SILKSCREEN_60 = new Font("Silkscreen", Font.PLAIN, 60);
    static final Font SILKSCREEN_40 = new Font("Silkscreen", Font.PLAIN, 40);
    static final Font SILKSCREEN_30 = new Font("Silkscreen", Font.PLAIN, 30);

    static BufferedImage[] gameplaySceneImages = new BufferedImage[8];
    static BufferedImage[] menuImages = new BufferedImage[6];
    static BufferedImage icon;

    /* Creates an ImageObserver interface for BufferedImage to load images with throughout the project.
     * It returns false. */
    static ImageObserver observer = (a, b, c, d, e, f) -> false;

    static Clip[] audioClips = new Clip[AudioID.values().length];

    /// Loads all assets, calls constructors of necessary classes
    public static void main(String[] args) {
        try {
            loadFonts();
            loadImages();
            loadAudio();

            new SaveData();
            new SirtetAudio();
            new SirtetWindow();
        /// If a file does not exist/cannot be found etc.
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Load Error, Check Stack Trace");

            System.exit(1);
        }
    }

    public static void loadImages() throws Exception {
        icon = ImageIO.read(new File("Assets/Icon.png"));

        gameplaySceneImages[7] = ImageIO.read(new File("Assets/GameplayScene.png"));
        for (int i = 0; i < 7; i++)
            gameplaySceneImages[i] = ImageIO.read(new File("Assets/" + BlockID.values()[i] + " Piece.png"));

        for (int i = 0; i < ImageID.values().length; i++)
            menuImages[i] = ImageIO.read(new File("Assets/" + ImageID.values()[i] + ".png"));
    }

    public static void loadFonts() throws Exception {
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Silkscreen-Regular.ttf")));
    }

    public static void loadAudio() throws Exception {
        for (int i = 0; i < audioClips.length; i++) {
            audioClips[i] = AudioSystem.getClip();
            audioClips[i].open(AudioSystem.getAudioInputStream(new File("Assets/" + AudioID.values()[i] + ".wav")));
        }
    }
}