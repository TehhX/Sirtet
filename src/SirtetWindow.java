import javax.swing.*;
import java.awt.*;

/**
 * This class handles the main JFrame and all of its children. It can change the scene by an integer argument,
 * removing the old scene before adding the new one. The frame will only be made visible once all checks
 * done by the Sirtet class, such as loading media, are finished to prevent wasted resources. */
class SirtetWindow {
    static final int FRAME_SIZE_X = 600;
    static final int FRAME_SIZE_Y = 800;

    public static SceneID currentScene = null;
    static SirtetScene[] sceneArray = new SirtetScene[4];

    static final JFrame frame = new JFrame("Sirtet");

    public SirtetWindow() {
        sceneArray[SceneID.Menu.ordinal()] = new MenuScene();
        sceneArray[SceneID.Gameplay.ordinal()] = new GameplayScene();
        sceneArray[SceneID.HighScore.ordinal()] = new HighScoreScene();
        sceneArray[SceneID.GameOver.ordinal()] = new GameOverScene();

        for (SirtetScene sirtetScene : sceneArray)
            frame.getContentPane().add(sirtetScene);

        changeScene(SceneID.Menu);

        frame.setIconImage(Sirtet.icon);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void changeScene(SceneID scene) {
        if (currentScene != null)
            sceneArray[currentScene.ordinal()].removeScene();

        currentScene = scene;
        frame.requestFocus();
        sceneArray[currentScene.ordinal()].addScene();
    }

    public static Font getFont(int pixelSize) {
        return new Font("Silkscreen", Font.PLAIN, pixelSize);
    }
}