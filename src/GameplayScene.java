import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ConcurrentModificationException;
class GameplayScene extends JPanel implements KeyListener {
    /**
     * This class handles the main gameplay scene, and all within. It also needs a SirtetGrid object to pass user
     * input to.
     */
    private int currentPoints;
    private JLabel score;
    private SirtetGrid grid;
    private JPanel panel;
    private JLabel paused;
    private boolean isPaused;
    private VolumeSlidersPanel volumeSlidersPanel;
    public GameplayScene() {
        volumeSlidersPanel = new VolumeSlidersPanel(VolumeSlidersPanel.VOLUME_CENTER_X, 350);
        isPaused = false;
        currentPoints = -25;
        score = new JLabel();
        grid = new SirtetGrid(this);
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        score.setFont(Sirtet.SILKSCREEN_60);
        score.setForeground(Color.black);
        paused = new JLabel("game paused", SwingConstants.CENTER);
        paused.setFont(Sirtet.SILKSCREEN_60);
        paused.setBounds(63, 250, 474, 77);
        paused.setForeground(Color.black);
        updateScoreLabel();
        panel.add(score);
        panel.add(this);
        panel.setLayout(null);
    }
    public void pauseGame() {
        grid.stopTimer();
        isPaused = true;
        panel.add(paused);
        panel.add(volumeSlidersPanel.getPanel());
        panel.remove(score);
        panel.remove(this);
        panel.add(this);
        repaint();
    }
    public void resumeGame() {
        grid.restartTimer();
        isPaused = false;
        panel.remove(volumeSlidersPanel.getPanel());
        panel.remove(paused);
        panel.add(score);
        panel.add(this);
        repaint();
    }
    public void pointIncrease(int rowsCleared) {
        switch (rowsCleared) {
            case -1:
                currentPoints += 50;
                break;
            case 0:
                return;
            case 1:
                currentPoints += 100;
                SirtetAudio.playAudio("oneRow.wav");
                break;
            case 2:
                currentPoints += 800;
                SirtetAudio.playAudio("twoRow.wav");
                break;
            case 3:
                currentPoints += 1200;
                SirtetAudio.playAudio("threeRow.wav");
                break;
            default:
                currentPoints += 1600;
                SirtetAudio.playAudio("fourRow.wav");
        }
        currentPoints -= 25;
        updateScoreLabel();
    }
    public void updateScoreLabel() {
        score.setText(currentPoints + "");
        int width = (int) score.getPreferredSize().getWidth();
        score.setBounds(550 - width, 35, width, 50);
    }
    /**
     * The paint method will first paint the panel with a green rectangle as the background. After this, it paints
     * the grid of blocks, the image being painted depending on which type of block is in each grid slot. It will
     * then paint the held grid on the side.
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Sirtet.SIRTET_GREEN);
        g.fillRect(0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        if(isPaused) return;
        Image currentImage;
        int yOffset = grid.getLastSonimortet().getHeight();
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
        } catch(ConcurrentModificationException e) {
            repaint();
            return;
        }
        boolean[][] heldGrid = getHeldGrid();
        currentImage = Sirtet.gameplaySceneImages[grid.getHeldType()];
        for(int xPos = 0; xPos < 3; xPos++) {
            for(int yPos = 0; yPos < 4; yPos++) {
                if(heldGrid[xPos][yPos]) {
                    g.drawImage(currentImage, 30 + 38 * xPos, 170 + 38 * yPos, 37, 38, Sirtet.observer);
                }
            }
        }
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
    // keyPressed handles user input for this scene, and calls the appropriate methods depending on the key pressed.
    public void keyPressed(KeyEvent e) {
        if(isPaused && e.getKeyCode() == 27) {
            resumeGame();
            return;
        } else if(isPaused) return;
        switch(e.getKeyCode()) {
            case 65:
                grid.getLastSonimortet().shiftAll(-1, 0);
                break;
            case 83:
                grid.getLastSonimortet().softDrop();
                break;
            case 68:
                grid.getLastSonimortet().shiftAll(1, 0);
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
                pauseGame();
        }
    }
    public SirtetGrid getGrid() {
        return grid;
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