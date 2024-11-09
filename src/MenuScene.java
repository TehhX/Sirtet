import javax.swing.*;
class MenuScene {
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