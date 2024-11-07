import javax.swing.*;
class MenuScene extends JPanel {
    private JPanel panel;
    public MenuScene() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(950, 950);
    }
    public JPanel getPanel() {
        return panel;
    }
}