import sun.nio.ch.sctp.SctpNet;

import javax.swing.*;
import java.awt.*;

/**
 * This class handles the main JFrame and all of its children. It can change the scene by an integer argument,
 * removing the old scene before adding the new one. The frame will only be made visible once all checks
 * done by the Sirtet class, such as loading media, are finished to prevent wasted resources. */
class SirtetWindow {
    static final int FRAME_SIZE_X = 600;
    static final int FRAME_SIZE_Y = 800;

    static JFrame frame = new JFrame("Sirtet");

    static SceneID currentScene = null;
    static SirtetScene[] sceneArray = new SirtetScene[4];

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
            sceneArray[currentScene.ordinal()].removeScene(frame);

        currentScene = scene;
        sceneArray[currentScene.ordinal()].addScene(frame);
        frame.requestFocus();
        frame.repaint();
    }

    public static Font getFont(FontID fontID) {
        switch (fontID) {
        case Silk30:
            return new Font("Silkscreen", Font.PLAIN, 30);
        case Silk40:
            return new Font("Silkscreen", Font.PLAIN, 40);
        case Silk60:
            return new Font("Silkscreen", Font.PLAIN, 60);
        default:
            throw new RuntimeException("Unexpected Value: " + fontID);
        }
    }
}