import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

/**
 * This class loads all data, images, fonts, and audio files for use in the game before even
 * making the main JFrame visible. This ensures that no time or resources are wasted mid-game
 * to load assets. It also initializes and declares static final variables FRAME_SIZE_X and Y
 * for use with JPanels and the main JFrame. If any of these assets cannot be loaded, the program
 * force-quits before even displaying the frame, to not waste time.
 */
public class Sirtet {
    static final Font SILKSCREEN_60 = new Font("Silkscreen", Font.PLAIN, 60);
    static final Font SILKSCREEN_40 = new Font("Silkscreen", Font.PLAIN, 40);
    static final Font SILKSCREEN_30 = new Font("Silkscreen", Font.PLAIN, 30);
    static BufferedImage[] gameplaySceneImages = new BufferedImage[8];
    static BufferedImage[] menuImages = new BufferedImage[7];
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
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void loadImages() throws Exception {
        icon = ImageIO.read(new File("Assets/Icon.png"));
        loadGameplayImages();
        loadMenuImages();
    }

    public static void loadGameplayImages() throws Exception {
        for (int imageIndex = 0; imageIndex < 7; imageIndex++) {
            gameplaySceneImages[imageIndex] = ImageIO.read(new File("Assets/" + BlockID.values()[imageIndex] + " Piece.png"));
        }
        gameplaySceneImages[7] = ImageIO.read(new File("Assets/GameplayScene.png"));
    }

    public static void loadMenuImages() throws Exception {
        for(int imageIndex = 0; imageIndex < ImageID.values().length; imageIndex++) {
            menuImages[imageIndex] = ImageIO.read(new File("Assets/" + ImageID.values()[imageIndex] + ".png"));
        }
    }

    public static void loadFonts() throws Exception {
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
                Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Silkscreen-Regular.ttf"))
        );
    }

    public static void loadAudio() throws Exception {
        for (int clipIndex = 0; clipIndex < audioClips.length; clipIndex++) {
            audioClips[clipIndex] = AudioSystem.getClip();
            audioClips[clipIndex].open(AudioSystem.getAudioInputStream(new File("Assets/" + AudioID.values()[clipIndex] + ".wav")));
        }
    }
}