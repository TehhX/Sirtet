import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
class GameplayScene extends JPanel implements KeyListener {
    private int currentPoints;
    private SirtetWindow frame;
    private SirtetGrid grid;
    private BufferedImage[] images;
    private ImageObserver observer;
    private final Font font = new Font("Bahnschrift", Font.PLAIN, 87);
    public GameplayScene(SirtetWindow frame, BufferedImage[] images) {
        this.frame = frame;
        this.images = images;
        currentPoints = -25;
        grid = new SirtetGrid(this);
        this.setLayout(null);
        observer = new ImageObserver() {
            public boolean imageUpdate(Image img, int infoFlags, int x, int y, int width, int height) {
                return false;
            }
        };
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
        Image currentImage = images[7];
        g.drawImage(currentImage, 0, 0, observer);
        int yOffset = grid.getLastSonimortet().getDropCount();
        for (SonimortetPositions pos : grid.getLastPositions()) {
            g.setColor(new Color(184, 255, 230));
            g.fillRect(173 + 38 * pos.getX(), 132 + 38 * (pos.getY() + yOffset), 36, 36);
        }
        for (Sonimortet sonimortet : grid.getSonimortetList()) {
            currentImage = images[sonimortet.getType()];
            for(SonimortetPositions pos : sonimortet.getPositions()) {
                g.drawImage(currentImage, 173 + 38 * pos.getX(), 132 + 38 * pos.getY(), 36, 36, observer);
            }
        }
        boolean[][] heldGrid = getHeldGrid();
        currentImage = images[grid.getHeldType()];
        for(int outer = 0; outer < 3; outer++) {
            for(int inner = 0; inner < 4; inner++) {
                if(heldGrid[outer][inner]) {
                    g.drawImage(currentImage, 30 + 38 * outer, 132 + 38 * inner, 37, 38, observer);
                }
            }
        }
        int stringWidth = (int) font.getStringBounds(currentPoints + "", new FontRenderContext(null, true, true)).getWidth();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.setColor(Color.black);
        g2d.drawString(currentPoints + "", 550 - stringWidth, 100);
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
            case 79:
                frame.changeScene(0);
                break;
            case 27:
                System.exit(0);
                break;
        }
    }
    public SirtetGrid getGrid() {
        return grid;
    }
    public SirtetWindow getFrame() {
        return frame;
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}