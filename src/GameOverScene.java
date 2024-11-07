import javax.swing.*;
class GameOverScene extends JPanel {
    private JPanel panel;
    public GameOverScene() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(950, 950);
    }
    public JPanel getPanel() {
        return panel;
    }
}