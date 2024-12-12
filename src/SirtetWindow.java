import javax.swing.*;
import java.awt.*;

/**
 * This class handles the main JFrame and all of its children. It can change the scene by an integer argument,
 * removing the old scene before adding the new one. The frame will only be made visible once all checks
 * done by the Sirtet class, such as loading media, are finished to prevent wasted resources.
 */
class SirtetWindow {
    static JFrame frame = new JFrame("Sirtet");
    static GameplayScene gameplayScene;
    static MenuScene menuScene;
    static HighScoreScene highScoreScene;
    static GameOverScene gameOverScene;
    static final int FRAME_SIZE_X = 600;
    static final int FRAME_SIZE_Y = 800;

    public SirtetWindow() {
        changeScene(SceneID.Menu);
        frame.setIconImage(Sirtet.icon);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void removePreviousScene() {
        if (menuScene != null) {
            frame.getContentPane().remove(menuScene);
            menuScene = null;
        } else if (gameplayScene != null) {
            frame.getContentPane().remove(gameplayScene);
            frame.removeKeyListener(gameplayScene);
            gameplayScene.getGrid().stopTimer();
            gameplayScene = null;
        } else if (gameOverScene != null) {
            frame.getContentPane().remove(gameOverScene);
            gameOverScene = null;
        } else if (highScoreScene != null) {
            frame.getContentPane().remove(highScoreScene);
            frame.removeKeyListener(highScoreScene);
            highScoreScene = null;
        }
    }

    public static void changeScene(SceneID scene) {
        frame.requestFocus();
        removePreviousScene();
        switch (scene) {
            case Menu:
                menuScene = new MenuScene();
                frame.getContentPane().add(menuScene);
                break;
            case Gameplay:
                gameplayScene = new GameplayScene();
                frame.addKeyListener(gameplayScene);
                frame.getContentPane().add(gameplayScene);
                break;
            case Highscore:
                highScoreScene = new HighScoreScene();
                frame.addKeyListener(highScoreScene);
                frame.getContentPane().add(highScoreScene);
                break;
            case Gameover:
                gameOverScene = new GameOverScene();
                frame.getContentPane().add(gameOverScene);
                gameOverScene.focusField();
                break;
        }
        frame.repaint();
    }

    public static void genericLabel(JLabel label, Font font) {
        label.setForeground(Color.black);
        label.setFont(font);
    }

    public static void labelSetupLeft(JLabel label, Font font, int xPos, int yPos) {
        genericLabel(label, font);
        label.setBounds(xPos, yPos, label.getPreferredSize().width, label.getPreferredSize().height);
    }

    public static void labelSetupCenter(JLabel label, Font font, int yPos) {
        genericLabel(label, font);
        int labelWidth = label.getPreferredSize().width;
        int labelHeight = label.getPreferredSize().height;
        label.setBounds((FRAME_SIZE_X - labelWidth) / 2, yPos, labelWidth, labelHeight);
    }

    public static void labelSetupRight(JLabel label, Font font, int xPos, int yPos) {
        genericLabel(label, font);
        int labelWidth = label.getPreferredSize().width;
        int labelHeight = label.getPreferredSize().height;
        label.setBounds(xPos - labelWidth, yPos, labelWidth, labelHeight);
    }
}