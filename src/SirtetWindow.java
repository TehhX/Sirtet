import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
class SirtetWindow {
    private JFrame frame;
    private final int FRAME_SIZE_X;
    private final int FRAME_SIZE_Y;
    private GameplayScene gameplayScene;
    private MenuScene menuScene;
    private HighScoreScene highScoreScene;
    private GameOverScene gameOverScene;
    private BufferedImage[] gameplaySceneImages = new BufferedImage[7];
    public SirtetWindow() {
        loadImages();
        FRAME_SIZE_X = 966;
        FRAME_SIZE_Y = 989;
        frame = frameSetup();
        changeScene(0);
    }
    public void loadImages() {
        try {
            gameplaySceneImages[0] = ImageIO.read(new File("Assets/ImageO.png"));
            gameplaySceneImages[1] = ImageIO.read(new File("Assets/ImageI.png"));
            gameplaySceneImages[2] = ImageIO.read(new File("Assets/ImageS.png"));
            gameplaySceneImages[3] = ImageIO.read(new File("Assets/ImageZ.png"));
            gameplaySceneImages[4] = ImageIO.read(new File("Assets/ImageL.png"));
            gameplaySceneImages[5] = ImageIO.read(new File("Assets/ImageJ.png"));
            gameplaySceneImages[6] = ImageIO.read(new File("Assets/ImageT.png"));
        } catch(Exception e) {
            System.out.println("Failed to load gameplaySceneImages.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public JFrame frameSetup() {
        frame = new JFrame("Sirtet");
        frame.setLayout(null);
        frame.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
        return frame;
    }
    public void removeAll() {
        if(menuScene != null) {
            frame.remove(menuScene);
            frame.removeKeyListener(menuScene);
            menuScene = null;
        } else if(gameplayScene != null) {
            frame.remove(gameplayScene);
            frame.removeKeyListener(gameplayScene);
            gameplayScene.getGrid().stopTimer();
            gameplayScene = null;
        } else if(gameOverScene != null) {
            frame.remove(gameOverScene);
            frame.removeKeyListener(gameOverScene);
            gameOverScene = null;
        } else if (highScoreScene != null){
            frame.remove(highScoreScene);
            frame.removeKeyListener(highScoreScene);
            highScoreScene = null;
        }
    }
    public void changeScene(int sceneNum) {
        removeAll();
        switch(sceneNum) {
            case 0:
                menuScene = new MenuScene(this);
                menuScene.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
                frame.addKeyListener(menuScene);
                frame.add(menuScene);
                break;
            case 1:
                gameplayScene = new GameplayScene(this, gameplaySceneImages);
                gameplayScene.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
                frame.addKeyListener(gameplayScene);
                frame.add(gameplayScene);
                break;
            case 2:
                gameOverScene = new GameOverScene(this);
                gameOverScene.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
                frame.addKeyListener(gameOverScene);
                frame.add(gameOverScene);
                break;
            case 3:
                highScoreScene = new HighScoreScene(this);
                highScoreScene.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
                frame.addKeyListener(highScoreScene);
                frame.add(highScoreScene);
                break;
            default:
        }
        frame.repaint();
    }
}