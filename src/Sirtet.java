import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
public class Sirtet {
    /**
     * This class loads all data, images, fonts, and audio files for use in the game before even
     * making the main JFrame visible. This ensures that no time or resources are wasted mid-game
     * to load assets. It also initializes and declares static final variables FRAME_SIZE_X and Y
     * for use with JPanels and the main JFrame. If any of these assets cannot be loaded, the program
     * force-quits before even displaying the frame, to not waste time.
     */
    static final int FRAME_SIZE_X = 600;
    static final int FRAME_SIZE_Y = 800;
    static final Color SIRTET_GREEN = new Color(135, 232, 155);
    static final Font SILKSCREEN_60 = new Font("Silkscreen", Font.PLAIN, 60);
    static final Font SILKSCREEN_40 = new Font("Silkscreen", Font.PLAIN, 40);
    static final Font SILKSCREEN_30 = new Font("Silkscreen", Font.PLAIN, 30);
    static BufferedImage[] gameplaySceneImages = new BufferedImage[8];
    static BufferedImage[] menuImages = new BufferedImage[8];
    static BufferedImage icon;
    static ImageObserver observer;
    public static void main(String[]args) {
        loadFonts();
        loadImages();
        new SaveData();
        new SirtetAudio();
        new SirtetWindow();
    }
    public static void loadImages() {
        observer = new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };
        try {
            icon = ImageIO.read(new File("Assets/Icon.png"));
            for(int pieceIndex = 0; pieceIndex < 7; pieceIndex++) {
                gameplaySceneImages[pieceIndex] = ImageIO.read(new File("Assets/piece" + pieceIndex + ".png"));
            }
            gameplaySceneImages[7] = ImageIO.read(new File("Assets/gameplayScene.png"));
            menuImages[0] = ImageIO.read(new File("Assets/playButton.png"));
            menuImages[1] = ImageIO.read(new File("Assets/highscoreButton.png"));
            menuImages[2] = ImageIO.read(new File("Assets/quitButton.png"));
            menuImages[3] = ImageIO.read(new File("Assets/playButtonActive.png"));
            menuImages[4] = ImageIO.read(new File("Assets/highscoreButtonActive.png"));
            menuImages[5] = ImageIO.read(new File("Assets/quitButtonActive.png"));
            menuImages[6] = ImageIO.read(new File("Assets/menuScene.png"));
            menuImages[7] = ImageIO.read(new File("Assets/controlsImage.png"));
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void loadFonts() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Silkscreen-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}