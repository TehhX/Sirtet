import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class holds the JPanel and everything within needed for the high score menu to be made visible and
 * populated with the necessary data. It uses 3 JLabel arrays, each with a part of the data gathered by
 * SaveData.java. A given name label will have the same index as the score label associated with it. */
class HighScoreScene extends SirtetScene implements KeyListener {
    private JLabel[] indexLabels = new JLabel[10];
    private JLabel[] nameLabels = new JLabel[10];
    private JLabel[] scoreLabels = new JLabel[10];

    public HighScoreScene() {
        super(true);
        add(new LabelCenter("Highscores", FontID.Silk60, 50));
        add(new LabelCenter("ESC to Return", FontID.Silk30, 725));
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
            indexLabels[i] = new LabelRight(i + 1 + ": ", FontID.Silk30, 110, 160 + i * 50);
            nameLabels[i] = new LabelRight("", FontID.Silk30, 0, 0);
            scoreLabels[i] = new LabelRight("", FontID.Silk30, 0, 0);
        }
    }

    /// Updates labels to reflect changes with the help of method "reloadLabel()"
    public void reloadAllLabels() {
        for (int i = 0; i < 10; i++) {
            scoreLabels[i].setText("" + SaveData.highScores[i].getScore());
            int width = scoreLabels[i].getPreferredSize().width;
            int height = scoreLabels[i].getPreferredSize().height;
            scoreLabels[i].setBounds(550 - width, 160 + i * 50, width, height);

            nameLabels[i].setText(SaveData.highScores[i].getName());
            width = nameLabels[i].getPreferredSize().width;
            height = nameLabels[i].getPreferredSize().height;
            nameLabels[i].setBounds(360 - width, 160 + i * 50, width, height);
        }
    }

    void addScene() {
        SirtetWindow.frame.addKeyListener(this);
        reloadAllLabels();
        setVisible(true);
    }

    void removeScene() {
        SirtetWindow.frame.removeKeyListener(this);
        setVisible(false);
    }

    /// When escape is pressed go to main menu, else do nothing
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            SirtetWindow.changeScene(SceneID.Menu);
    }

    public void keyTyped(KeyEvent ignored) {}
    public void keyReleased(KeyEvent ignored) {}
}