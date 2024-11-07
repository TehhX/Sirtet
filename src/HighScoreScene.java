import javax.swing.*;
class HighScoreScene {
    private JPanel panel;
    public HighScoreScene() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(950, 950);
    }
    public JPanel getPanel() {
        return panel;
    }
}