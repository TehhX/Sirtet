import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class holds the JPanel and everything within needed for the high score menu to be made visible and
 * populated with the necessary data. It uses 3 JLabel arrays, each with a part of the data gathered by
 * SaveData.java. A given name label will have the same index as the score and placement as those associated
 * with it.
 */
class HighScoreScene extends JPanel implements KeyListener {
    private JPanel panel;
    private JLabel[] indexLabels = new JLabel[10];
    private JLabel[] nameLabels = new JLabel[10];
    private JLabel[] scoreLabels = new JLabel[10];

    public HighScoreScene() {
        loadLabels();
        JLabel titleLabel = new JLabel("Highscores");
        JLabel returnLabel = new JLabel("ESC to Return");
        titleLabel.setFont(Sirtet.SILKSCREEN_60);
        titleLabel.setForeground(Color.black);
        titleLabel.setBounds(90, 50, 421, 77);
        returnLabel.setFont(Sirtet.SILKSCREEN_30);
        returnLabel.setForeground(Color.black);
        returnLabel.setBounds(165, 725, 270, 39);
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel.setLayout(null);
        panel.add(titleLabel);
        panel.add(returnLabel);
        for (int labelIndex = 0; labelIndex < 10; labelIndex++) {
            panel.add(nameLabels[labelIndex]);
            panel.add(scoreLabels[labelIndex]);
            panel.add(indexLabels[labelIndex]);
        }
        panel.add(this);
    }

    public void loadLabels() {
        for (int labelIndex = 0; labelIndex < 10; labelIndex++) {
            indexLabels[labelIndex] = scoreLabelSetup(labelIndex + 1 + ": ");
            nameLabels[labelIndex] = scoreLabelSetup(SaveData.highScores[labelIndex].getName());
            scoreLabels[labelIndex] = scoreLabelSetup("" + SaveData.highScores[labelIndex].getScore());
            indexLabels[labelIndex].setBounds(20, 160 + labelIndex * 50, 100, 50);
            nameLabels[labelIndex].setBounds(80, 160 + labelIndex * 50, 300, 50);
            scoreLabels[labelIndex].setBounds(230, 160 + labelIndex * 50, 300, 50);
        }
    }

    public JLabel scoreLabelSetup(String text) {
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setFont(Sirtet.SILKSCREEN_30);
        label.setForeground(Color.black);
        return label;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Sirtet.SIRTET_GREEN);
        g.fillRect(0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27) SirtetWindow.changeScene(SceneID.Menu);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}