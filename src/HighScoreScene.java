import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
class HighScoreScene extends JPanel implements KeyListener {
    /**
     * This class holds the JPanel and everything within needed for the high score menu to be made visible and
     * populated with the necessary data. It uses 3 JLabel arrays, each with a part of the data gathered by
     * SaveData.java. A given name label will have the same index as the score and placement as those associated
     * with it.
     */
    private JPanel panel;
    private JLabel title;
    private JLabel[] indexLabels = new JLabel[10];
    private JLabel[] nameLabels = new JLabel[10];
    private JLabel[] scoreLabels = new JLabel[10];
    public HighScoreScene() {
        loadLabels();
        title = new JLabel("Highscores");
        title.setBounds(0, 0, 0, 0);
        title.setFont(Sirtet.SILKSCREEN_60);
        title.setForeground(Color.black);
        title.setBounds(90, 50, 421, 77);
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel.setLayout(null);
        panel.add(title);
        for(int i = 0; i < 10; i++) {
            panel.add(nameLabels[i]);
            panel.add(scoreLabels[i]);
            panel.add(indexLabels[i]);
        }
        panel.add(this);
    }
    public void loadLabels() {
        for(int i = 0; i < 10; i++) {
            indexLabels[i] = labelSetup(i + 1 + ": ");
            nameLabels[i] = labelSetup(SaveData.highScores[i].getName());
            scoreLabels[i] = labelSetup("" + SaveData.highScores[i].getScore());
            indexLabels[i].setBounds(20, 160 + i * 50, 100, 50);
            nameLabels[i].setBounds(80, 160 + i * 50, 300, 50);
            scoreLabels[i].setBounds(230, 160 + i * 50, 300, 50);
        }
    }
    public JLabel labelSetup(String text) {
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
        if(e.getKeyCode() == 27) SirtetWindow.changeScene(0);
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}