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
            case HighScore:
                highScoreScene = new HighScoreScene();
                frame.addKeyListener(highScoreScene);
                frame.getContentPane().add(highScoreScene);
                break;
            case GameOver:
                gameOverScene = new GameOverScene();
                frame.getContentPane().add(gameOverScene);
                gameOverScene.focusField();
                break;
        }
        frame.repaint();
    }

    public static JLabel genericLabel(Font font, String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.black);
        label.setFont(font);
        return label;
    }

    public static JLabel labelSetupLeft(String text, Font font, int xPos, int yPos) {
        JLabel label = genericLabel(font, text);
        label.setBounds(xPos, yPos, label.getPreferredSize().width, label.getPreferredSize().height);
        return label;
    }

    public static JLabel labelSetupCenter(String text, Font font, int yPos) {
        JLabel label = genericLabel(font, text);
        int labelWidth = label.getPreferredSize().width;
        int labelHeight = label.getPreferredSize().height;
        label.setBounds((FRAME_SIZE_X - labelWidth) / 2, yPos, labelWidth, labelHeight);
        return label;
    }

    public static JLabel labelSetupRight(String text, Font font, int xPos, int yPos) {
        JLabel label = genericLabel(font, text);
        int labelWidth = label.getPreferredSize().width;
        int labelHeight = label.getPreferredSize().height;
        label.setBounds(xPos - labelWidth, yPos, labelWidth, labelHeight);
        return label;
    }

    public static void basicPanelSetup(JPanel panel, boolean opaque) {
        panel.setBackground(new Color(22, 207, 130));
        panel.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
        panel.setOpaque(opaque);
        panel.setLayout(null);
    }
}