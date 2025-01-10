import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class holds the JPanel and everything within needed for the high score menu to be made visible and
 * populated with the necessary data. It uses 3 JLabel arrays, each with a part of the data gathered by
 * SaveData.java. A given name label will have the same index as the score label associated with it. */
class HighScoreScene extends SirtetPanel implements KeyListener {
    private JLabel[] indexLabels = new JLabel[10];
    private JLabel[] nameLabels = new JLabel[10];
    private JLabel[] scoreLabels = new JLabel[10];

    public HighScoreScene() {
        super(true);

        add(SirtetWindow.labelCenter("Highscores", Sirtet.SILKSCREEN_60, 50));
        add(SirtetWindow.labelCenter("ESC to Return", Sirtet.SILKSCREEN_30, 725));

        loadLabels();

        for (int i = 0; i < 10; i++) {
            add(nameLabels[i]);
            add(scoreLabels[i]);
            add(indexLabels[i]);
        }
    }

    /// Sets up label arrays for use in the scene
    public void loadLabels() {
        for (int i = 0; i < 10; i++) {
            HighScore currentHS = SaveData.highScores[i];

            indexLabels[i] = SirtetWindow.labelRight(i + 1 + ": ", Sirtet.SILKSCREEN_30, 110, 160 + i * 50);
            nameLabels[i] = SirtetWindow.labelRight(currentHS.getName(), Sirtet.SILKSCREEN_30, 360, 160 + i * 50);
            scoreLabels[i] = SirtetWindow.labelRight("" + currentHS.getScore(), Sirtet.SILKSCREEN_30, 550, 160 + i * 50);
        }
    }

    /// When escape is pressed go to main menu, else do nothing
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            SirtetWindow.changeScene(SceneID.Menu);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}