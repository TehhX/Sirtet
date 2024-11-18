import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
public class Sirtet {
    static final int FRAME_SIZE_X = 600;
    static final int FRAME_SIZE_Y = 800;
    static final Font SILKSCREEN_60 = new Font("Silkscreen", Font.PLAIN, 60);
    static final Font SILKSCREEN_40 = new Font("Silkscreen", Font.PLAIN, 40);
    static final Font SILKSCREEN_30 = new Font("Silkscreen", Font.PLAIN, 30);
    static BufferedImage[] gameplaySceneImages = new BufferedImage[8];
    static BufferedImage[] menuImages = new BufferedImage[7];
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
        observer = (img, infoflags, x, y, width, height) -> false;
        try {
            icon = ImageIO.read(new File("Assets/Icon.png"));
            for(int i = 0; i < 7; i++) gameplaySceneImages[i] = ImageIO.read(new File("Assets/piece" + i + ".png"));
            gameplaySceneImages[7] = ImageIO.read(new File("Assets/gameplayScene.png"));
            menuImages[0] = ImageIO.read(new File("Assets/playButton.png"));
            menuImages[1] = ImageIO.read(new File("Assets/highscoreButton.png"));
            menuImages[2] = ImageIO.read(new File("Assets/quitButton.png"));
            menuImages[3] = ImageIO.read(new File("Assets/playButtonActive.png"));
            menuImages[4] = ImageIO.read(new File("Assets/highscoreButtonActive.png"));
            menuImages[5] = ImageIO.read(new File("Assets/quitButtonActive.png"));
            menuImages[6] = ImageIO.read(new File("Assets/menuScene.png"));
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