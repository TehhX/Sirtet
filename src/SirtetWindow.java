import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
class SirtetWindow {
    private JFrame frame;
    final int FRAME_SIZE_X = 600;
    final int FRAME_SIZE_Y = 800;
    private GameplayScene gameplayScene;
    private MenuScene menuScene;
    private HighScoreScene highScoreScene;
    private GameOverScene gameOverScene;
    private BufferedImage[] gameplaySceneImages = new BufferedImage[8];
    public SirtetWindow() {
        loadImages();
        frame = frameSetup();
        changeScene(0);
    }
    public void loadImages() {
        try {
            for(int i = 0; i < 7; i++) {
                gameplaySceneImages[i] = ImageIO.read(new File("Assets/piece" + i + ".png"));
            }
            gameplaySceneImages[7] = ImageIO.read(new File("Assets/gameplayScene.png"));
        } catch(Exception e) {
            System.out.println("Failed to load gameplaySceneImages.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public JFrame frameSetup() {
        frame = new JFrame("Sirtet");
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
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