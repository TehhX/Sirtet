import javax.swing.*;

/**
 * This class handles the main JFrame and all of its children. It can change the scene by an integer argument,
 * removing the old scene before adding the new one. The frame will only be made visible once all checks
 * done by the Sirtet class, such as loading media, are finished to prevent wasted resources.
 */
class SirtetWindow extends JFrame {
    public static JFrame frame = new JFrame("Sirtet");
    public static GameplayScene gameplayScene;
    public static MenuScene menuScene;
    public static HighScoreScene highScoreScene;
    public static GameOverScene gameOverScene;

    public SirtetWindow() {
        changeScene(SceneID.Menu);
        frame.setIconImage(Sirtet.icon);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void removePreviousScene() {
        frame.requestFocus();
        if (menuScene != null) {
            frame.getContentPane().remove(menuScene.getPanel());
            menuScene = null;
        } else if (gameplayScene != null) {
            frame.getContentPane().remove(gameplayScene.getPanel());
            frame.removeKeyListener(gameplayScene);
            gameplayScene.getGrid().stopTimer();
            gameplayScene = null;
        } else if (gameOverScene != null) {
            frame.getContentPane().remove(gameOverScene.getPanel());
            gameOverScene = null;
        } else if (highScoreScene != null) {
            frame.getContentPane().remove(highScoreScene.getPanel());
            frame.removeKeyListener(highScoreScene);
            highScoreScene = null;
        }
    }

    public static void changeScene(SceneID scene) {
        removePreviousScene();
        switch (scene) {
            case Menu:
                menuScene = new MenuScene();
                frame.getContentPane().add(menuScene.getPanel());
                break;
            case Gameplay:
                gameplayScene = new GameplayScene();
                frame.addKeyListener(gameplayScene);
                frame.getContentPane().add(gameplayScene.getPanel());
                break;
            case Highscore:
                highScoreScene = new HighScoreScene();
                frame.addKeyListener(highScoreScene);
                frame.getContentPane().add(highScoreScene.getPanel());
                break;
            case Gameover:
                gameOverScene = new GameOverScene();
                frame.getContentPane().add(gameOverScene.getPanel());
                gameOverScene.focusField();
                break;
        }
        frame.repaint();
    }
}