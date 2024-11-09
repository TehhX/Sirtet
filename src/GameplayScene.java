import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
class GameplayScene extends JPanel implements KeyListener {
    private JPanel panel;
    private SirtetGrid grid;
    public GameplayScene() {
        grid = new SirtetGrid(this);
        panel = new JPanel();
        panel.setBounds(0, 0, 966, 989);
        panel.add(this);
        panel.setLayout(null);
        grid.addSonimortet(' ');
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        grid.updateGrid();
        for(int outer = 0; outer < 10; outer++) {
            for(int inner = 0; inner < 16; inner++) {
                g.setColor(grid.getGrid(outer, inner) ? Color.green : Color.red);
                g.fillRect(200 + 28 * outer, 50 + 28 * inner, 25, 25);
            }
        }
        g.setColor(Color.green);
        if(grid.getHeld() == ' ') return;
        boolean[][] heldGrid = getHeldGrid();
        for(int outer = 0; outer < 3; outer++) {
            for(int inner = 0; inner < 4; inner++) {
                if(heldGrid[outer][inner]) {
                    g.fillRect(50 + 28 * outer, 50 + 28 * inner, 25, 25);
                }
            }
        }
    }
    public boolean[][] getHeldGrid() {
        boolean[][] heldGrid = new boolean[3][4];
        switch(grid.getHeld()) {
            case 'O':
                heldGrid[0][0] = true;
                heldGrid[1][0] = true;
                heldGrid[0][1] = true;
                heldGrid[1][1] = true;
                break;
            case 'I':
                heldGrid[0][0] = true;
                heldGrid[0][1] = true;
                heldGrid[0][2] = true;
                heldGrid[0][3] = true;
                break;
            case 'S':
                heldGrid[0][1] = true;
                heldGrid[1][1] = true;
                heldGrid[1][0] = true;
                heldGrid[2][0] = true;
                break;
            case 'Z':
                heldGrid[0][0] = true;
                heldGrid[1][0] = true;
                heldGrid[1][1] = true;
                heldGrid[2][1] = true;
                break;
            case 'L':
                heldGrid[0][0] = true;
                heldGrid[0][1] = true;
                heldGrid[0][2] = true;
                heldGrid[1][2] = true;
                break;
            case 'J':
                heldGrid[1][0] = true;
                heldGrid[1][1] = true;
                heldGrid[1][2] = true;
                heldGrid[0][2] = true;
                break;
            case 'T':
                heldGrid[0][0] = true;
                heldGrid[1][0] = true;
                heldGrid[1][1] = true;
                heldGrid[2][0] = true;
                break;
        }
        return heldGrid;
    }
    public JPanel getPanel() {
        return panel;
    }
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 'a':
                grid.getLastSonimortet().shiftLeft();
                break;
            case 's':
                grid.getLastSonimortet().softDrop();
                break;
            case 'd':
                grid.getLastSonimortet().shiftRight();
                break;
            case ' ':
                grid.getLastSonimortet().hardDrop();
                break;
            case 'e':
                grid.getLastSonimortet().rotateClockwise();
                break;
            case 'q':
                grid.getLastSonimortet().rotateCounterClockwise();
                break;
            case 'f':
                grid.swapHeld();
                break;
        }
        this.repaint();
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}