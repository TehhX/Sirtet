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
            HighScore currentHS = SaveData.highScores[i];

            indexLabels[i] = new LabelRight(i + 1 + ": ", FontID.Silk30, 110, 160 + i * 50);
            nameLabels[i] = new LabelRight(currentHS.getName(), FontID.Silk30, 360, 160 + i * 50);
            scoreLabels[i] = new LabelRight("" + currentHS.getScore(), FontID.Silk30, 550, 160 + i * 50);
        }
    }

    /// When escape is pressed go to main menu, else do nothing
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            SirtetWindow.changeScene(SceneID.Menu);
    }

    public void reloadLabel(JLabel label, String text, int xPos, int yPos) {
        label.setText(text);
        final int preferredWidth = label.getPreferredSize().width;
        final int preferredHeight = label.getPreferredSize().height;
        label.setBounds(xPos - preferredWidth, yPos, preferredWidth, preferredHeight);
    }

    void addScene(JFrame parentFrame) {
        parentFrame.addKeyListener(this);
        for (int i = 0; i < 10; i++) {
            reloadLabel(scoreLabels[i], "" + SaveData.highScores[i].getScore(), 550, 160 + i * 50);
            reloadLabel(nameLabels[i], SaveData.highScores[i].getName(), 360, 160 + i * 50);
        }
        setVisible(true);
    }

    void removeScene(JFrame parentFrame) {
        parentFrame.removeKeyListener(this);
        setVisible(false);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}