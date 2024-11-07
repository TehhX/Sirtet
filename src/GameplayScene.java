import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GameplayScene extends JPanel implements KeyListener {
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
        for(int outer = 0; outer < 10; outer++) {
            for(int inner = 0; inner < 16; inner++) {
                if(grid.getGrid(outer, inner)) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.red);
                }
                g.fillRect(200 + 28 * outer, 50 + 28 * inner, 25, 25);
            }
        }
        // Hold window
        g.setColor(Color.green);
        boolean[][] heldGrid = new boolean[3][3];
        heldGrid[0][0] = true;
        switch(grid.getHeld()) {
            case 'O':
                heldGrid[1][0] = true;
                heldGrid[0][1] = true;
                heldGrid[1][1] = true;
                break;
            case 'I':

                break;
            case 'S':

                break;
            case 'Z':

                break;
            case 'L':

                break;
            case 'J':

                break;
            case 'T':

                break;
        }
        for(int outer = 0; outer < 3; outer++) {
            for(int inner = 0; inner < 3; inner++) {
                if(heldGrid[outer][inner]) {
                    g.fillRect(50 + 28 * outer, 50 + 28 * inner, 25, 25);
                }
            }
        }
    }
    public JPanel getPanel() {
        return panel;
    }
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
        switch(e.getKeyChar()) {
            case 'a':
                grid.shiftLeft();
                break;
            case 's':
                grid.softDrop();
                break;
            case 'd':
                grid.shiftRight();
                break;
            case ' ':
                grid.hardDrop();
                grid.addSonimortet();
            case 'f':
                grid.rotate();
        }
        this.repaint();
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}