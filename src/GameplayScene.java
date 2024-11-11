import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
class GameplayScene extends JPanel implements KeyListener {
    private int currentPoints;
    private SirtetGrid grid;
    private final JLabel scoreLabel;
    public GameplayScene() {
        currentPoints = -25;
        grid = new SirtetGrid(this);
        scoreLabel = labelSetup();
        this.setLayout(null);
        this.add(scoreLabel);
        grid.addSonimortet();
    }
    public JLabel labelSetup() {
        JLabel label = new JLabel();
        label.setBounds(500, 100, 500, 200);
        label.setFont(new Font("Impact", Font.BOLD, 70));
        label.setForeground(Color.blue);
        return label;
    }
    public void pointIncrease(int rowsCleared) {
        if(rowsCleared == 0) return;
        currentPoints -= 25;
        switch (rowsCleared) {
            case 1:
                currentPoints += 100;
                break;
            case 2:
                currentPoints += 800;
                break;
            case 3:
                currentPoints += 1200;
                break;
            default:
                currentPoints += 1600;
        }
    }
    public void pointIncrease() {
        currentPoints += 25;
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
        if(grid.getHeld() != ' ') {
            boolean[][] heldGrid = getHeldGrid();
            for(int outer = 0; outer < 3; outer++) {
                for(int inner = 0; inner < 4; inner++) {
                    if(heldGrid[outer][inner]) {
                        g.fillRect(50 + 28 * outer, 50 + 28 * inner, 25, 25);
                    }
                }
            }
        }
        scoreLabel.setText("" + currentPoints);
    }
    public boolean[][] getHeldGrid() {
        boolean[][] heldGrid = new boolean[3][4];
        int[][] startPos = Sonimortet.getStartingPositions(grid.getHeld());
        for(int outer = 0; outer < 4; outer++) {
            for(int inner = 0; inner < 4; inner++) {
                heldGrid[startPos[0][inner] - 4][startPos[1][inner]] = true;
            }
        }
        return heldGrid;
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
                grid.getLastSonimortet().rotate(false);
                break;
            case 'q':
                grid.getLastSonimortet().rotate(true);
                break;
            case 'f':
                grid.swapHeld();
                break;
        }
        repaint();
    }
    public SirtetGrid getGrid() {
        return grid;
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}