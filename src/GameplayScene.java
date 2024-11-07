import javax.swing.*;
import java.awt.*;
class GameplayScene extends JPanel {
    private JPanel panel;
    private SirtetGrid grid;
    public GameplayScene() {
        grid = new SirtetGrid();
        panel = new JPanel();
        panel.setBounds(0, 0, 966, 989);
        this.setSize(966, 989);
        panel.add(this);
        panel.setLayout(null);
        grid.addSonimortet();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        grid.updateGrid();
        for(int outerIndex = 0; outerIndex < 10; outerIndex++) {
            for(int innerIndex = 0; innerIndex < 16; innerIndex++) {
                if(grid.getGrid(outerIndex, innerIndex)) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.red);
                }
                g.fillRect(200 + 50 * outerIndex, 50 + 50 * innerIndex, 40, 40);
            }
        }
    }
    public JPanel getPanel() {
        return panel;
    }
}