import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
class GameplayScene extends JPanel implements KeyListener {
    private int currentPoints;
    private SirtetWindow frame;
    private SirtetGrid grid;
    private JLabel scoreLabel;
    private BufferedImage[] images;
    private ImageObserver observer;
    public GameplayScene(SirtetWindow frame, BufferedImage[] images) {
        this.frame = frame;
        this.images = images;
        currentPoints = -25;
        grid = new SirtetGrid(this);
        scoreLabel = labelSetup();
        this.setLayout(null);
        this.add(scoreLabel);
        observer = new ImageObserver() {
            public boolean imageUpdate(Image img, int infoFlags, int x, int y, int width, int height) {
                return false;
            }
        };
    }
    public JLabel labelSetup() {
        JLabel label = new JLabel();
        label.setBounds(500, 100, 500, 200);
        return label;
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
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Sonimortet sonimortet : grid.getSonimortetList()) {
            Image currentImage = images[sonimortet.getType()];
            for(SonimortetPositions pos : sonimortet.getPositions()) {
                g.drawImage(currentImage, 200 + 28 * pos.getX(), 50 + 28 * pos.getY(), 25, 25, observer);
            }
        }
        g.setColor(Color.green);
        boolean[][] heldGrid = getHeldGrid();
        Image currentImage = images[grid.getHeldType()];
        for(int outer = 0; outer < 3; outer++) {
            for(int inner = 0; inner < 4; inner++) {
                if(heldGrid[outer][inner]) {
                    g.drawImage(currentImage, 50 + 28 * outer, 50 + 28 * inner, 25, 25, observer);
                }
            }
        }
        scoreLabel.setText("" + currentPoints);
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