import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
public class Sirtet {
    public static BufferedImage[] gameplaySceneImages = new BufferedImage[8];
    public static BufferedImage[] menuImages = new BufferedImage[7];
    public static ImageObserver observer;
    public static void main(String[]args) {
        loadFonts();
        loadImages();
        new SirtetWindow();
    }
    public static void loadImages() {
        observer = (img, infoflags, x, y, width, height) -> false;
        try {
            for(int i = 0; i < 7; i++) gameplaySceneImages[i] = ImageIO.read(new File("Assets/piece" + i + ".png"));
            gameplaySceneImages[7] = ImageIO.read(new File("Assets/gameplayScene.png"));
            menuImages[0] = ImageIO.read(new File("Assets/logo.png"));
            menuImages[1] = ImageIO.read(new File("Assets/playButton.png"));
            menuImages[2] = ImageIO.read(new File("Assets/highscoreButton.png"));
            menuImages[3] = ImageIO.read(new File("Assets/quitButton.png"));
            menuImages[4] = ImageIO.read(new File("Assets/playButtonActive.png"));
            menuImages[5] = ImageIO.read(new File("Assets/highscoreButtonActive.png"));
            menuImages[6] = ImageIO.read(new File("Assets/quitButtonActive.png"));
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