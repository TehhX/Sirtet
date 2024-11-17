import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
class HighScoreScene extends JPanel implements KeyListener {
    private SirtetWindow frame;
    private JPanel panel;
    private JLabel[] indexLabels = new JLabel[10];
    private JLabel[] nameLabels = new JLabel[10];
    private JLabel[] scoreLabels = new JLabel[10];
    public HighScoreScene(SirtetWindow frame) {
        this.frame = frame;
        loadLabels();
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel.setLayout(null);
        for(int i = 0; i < 10; i++) {
            panel.add(nameLabels[i]);
            panel.add(scoreLabels[i]);
            panel.add(indexLabels[i]);
        }
        panel.add(this);
    }
    public void loadLabels() {
        for(int i = 0; i < 10; i++) {
            indexLabels[i] = new JLabel(i + 1 + ": ", SwingConstants.RIGHT);
            nameLabels[i] = new JLabel(SaveData.highScores[i].getName(), SwingConstants.RIGHT);
            scoreLabels[i] = new JLabel("" + SaveData.highScores[i].getScore(), SwingConstants.RIGHT);
            indexLabels[i].setFont(Sirtet.SILKSCREEN_40);
            nameLabels[i].setFont(Sirtet.SILKSCREEN_40);
            scoreLabels[i].setFont(Sirtet.SILKSCREEN_40);
            indexLabels[i].setForeground(Color.black);
            nameLabels[i].setForeground(Color.black);
            scoreLabels[i].setForeground(Color.black);
            indexLabels[i].setBounds(30, 120 + i * 50, 100, 50);
            nameLabels[i].setBounds(140, 120 + i * 50, 200, 50);
            scoreLabels[i].setBounds(230, 120 + i * 50, 300, 50);
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(135, 232, 155));
        g.fillRect(0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
    }
    public JPanel getPanel() {
        return panel;
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 27) frame.changeScene(0);
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}