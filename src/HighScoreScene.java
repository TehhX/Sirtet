import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class holds the JPanel and everything within needed for the high score menu to be made visible and
 * populated with the necessary data. It uses 3 JLabel arrays, each with a part of the data gathered by
 * SaveData.java. A given name label will have the same index as the score and placement as those associated
 * with it.
 */
class HighScoreScene extends JPanel implements KeyListener {
    private JLabel[] indexLabels = new JLabel[10];
    private JLabel[] nameLabels = new JLabel[10];
    private JLabel[] scoreLabels = new JLabel[10];

    public HighScoreScene() {
        SirtetWindow.basicPanelSetup(this, true);
        loadLabels();
        JLabel titleLabel = SirtetWindow.labelSetupCenter("Highscores", Sirtet.SILKSCREEN_60, 50);
        JLabel returnLabel = SirtetWindow.labelSetupCenter("ESC to Return", Sirtet.SILKSCREEN_30, 725);
        add(titleLabel);
        add(returnLabel);
        for (int labelIndex = 0; labelIndex < 10; labelIndex++) {
            add(nameLabels[labelIndex]);
            add(scoreLabels[labelIndex]);
            add(indexLabels[labelIndex]);
        }
    }

    public void loadLabels() {
        for (int labelIndex = 0; labelIndex < 10; labelIndex++) {
            HighScore currentHS = SaveData.highScores[labelIndex];
            indexLabels[labelIndex] = SirtetWindow.labelSetupRight(labelIndex + 1 + ": ", Sirtet.SILKSCREEN_30, 110, 160 + labelIndex * 50);
            nameLabels[labelIndex] = SirtetWindow.labelSetupRight(currentHS.getName(), Sirtet.SILKSCREEN_30, 360, 160 + labelIndex * 50);
            scoreLabels[labelIndex] = SirtetWindow.labelSetupRight("" + currentHS.getScore(), Sirtet.SILKSCREEN_30, 550, 160 + labelIndex * 50);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27) SirtetWindow.changeScene(SceneID.Menu);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}