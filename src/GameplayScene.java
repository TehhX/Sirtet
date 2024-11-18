import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.util.ConcurrentModificationException;
class GameplayScene extends JPanel implements KeyListener {
    private int currentPoints;
    private SirtetWindow frame;
    private SirtetGrid grid;
    private JPanel panel;
    public GameplayScene(SirtetWindow frame) {
        this.frame = frame;
        currentPoints = -25;
        grid = new SirtetGrid(this);
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel.add(this);
        panel.setLayout(null);
    }
    public void pointIncrease(int rowsCleared) {
        if(rowsCleared == 0) return;
        switch (rowsCleared) {
            case 1:
                currentPoints += 75;
                break;
            case 2:
                currentPoints += 775;
                break;
            case 3:
                currentPoints += 1175;
                break;
            default:
                currentPoints += 1575;
        }
    }
    public void pointIncrease() {
        currentPoints += 25;
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(135, 232, 155));
        g.fillRect(0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        Image currentImage;
        int yOffset = grid.getLastSonimortet().getDropCount();
        for (SonimortetPositions pos : grid.getLastPositions()) {
            g.setColor(new Color(103, 215, 237));
            g.fillRect(173 + 38 * pos.getX(), 132 + 38 * (pos.getY() + yOffset), 36, 36);
        }
        try {
            for (Sonimortet sonimortet : grid.getSonimortetList()) {
                currentImage = Sirtet.gameplaySceneImages[sonimortet.getType()];
                for(SonimortetPositions pos : sonimortet.getPositions()) {
                    g.drawImage(currentImage, 173 + 38 * pos.getX(), 132 + 38 * pos.getY(), 36, 36, Sirtet.observer);
                }
            }
        } catch (ConcurrentModificationException e) {
            repaint();
            return;
        }
        boolean[][] heldGrid = getHeldGrid();
        currentImage = Sirtet.gameplaySceneImages[grid.getHeldType()];
        for(int outer = 0; outer < 3; outer++) {
            for(int inner = 0; inner < 4; inner++) {
                if(heldGrid[outer][inner]) {
                    g.drawImage(currentImage, 30 + 38 * outer, 170 + 38 * inner, 37, 38, Sirtet.observer);
                }
            }
        }
        int stringWidth = (int) Sirtet.SILKSCREEN_60.getStringBounds(currentPoints + "", new FontRenderContext(null, true, true)).getWidth();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(Sirtet.SILKSCREEN_60);
        g2d.setColor(Color.black);
        g2d.drawString(currentPoints + "", 550 - stringWidth, 85);
        currentImage = Sirtet.gameplaySceneImages[7];
        g.drawImage(currentImage, 0, 0, Sirtet.observer);
    }
    public boolean[][] getHeldGrid() {
        boolean[][] heldGrid = new boolean[3][4];
        int[][] startPos = Sonimortet.getStartingPositions(grid.getHeldType());
        for(int outer = 0; outer < 4; outer++) {
            for(int inner = 0; inner < 4; inner++) {
                heldGrid[startPos[0][inner] - 4][startPos[1][inner]] = true;
            }
        }
        return heldGrid;
    }
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 65:
                grid.getLastSonimortet().shiftLeft();
                break;
            case 83:
                grid.getLastSonimortet().softDrop();
                break;
            case 68:
                grid.getLastSonimortet().shiftRight();
                break;
            case 32:
                grid.getLastSonimortet().hardDrop();
                break;
            case 69:
                grid.getLastSonimortet().rotateClock();
                break;
            case 81:
                grid.getLastSonimortet().rotateCounter();
                break;
            case 70:
                grid.swapHeld();
                break;
            case 27:
                frame.changeScene(0);
                break;
        }
    }
    public SirtetGrid getGrid() {
        return grid;
    }
    public SirtetWindow getFrame() {
        return frame;
    }
    public JPanel getPanel() {
        return panel;
    }
    public int getCurrentPoints() {
        return currentPoints;
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}