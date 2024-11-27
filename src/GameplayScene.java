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
    private JLabel score;
    private SirtetGrid grid;
    private JPanel panel;
    private JLabel paused;
    private boolean isPaused = false;
    private VolumeSlidersPanel volumeSlidersPanel;
    public GameplayScene() {
        volumeSlidersPanel = new VolumeSlidersPanel(VolumeSlidersPanel.VOLUME_CENTER_X, 350);
        SaveData.currentScore = -25;
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
                SaveData.currentScore += 50;
                break;
            case 0:
                return;
            case 1:
                SaveData.currentScore += 100;
                SirtetAudio.playAudio("oneRow.wav");
                break;
            case 2:
                SaveData.currentScore += 800;
                SirtetAudio.playAudio("twoRow.wav");
                break;
            case 3:
                SaveData.currentScore += 1200;
                SirtetAudio.playAudio("threeRow.wav");
                break;
            default:
                SaveData.currentScore += 1600;
                SirtetAudio.playAudio("fourRow.wav");
        }
        SaveData.currentScore -= 25;
        updateScoreLabel();
    }
    public void updateScoreLabel() {
        score.setText(SaveData.currentScore + "");
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
        g.setColor(new Color(103, 215, 237));
        int yOffset = grid.getLastSonimortet().getHeight();
        for (SonimortetPositions pos : grid.getLastPositions()) {
            g.fillRect(173 + 38 * pos.getX(), 132 + 38 * (pos.getY() + yOffset), 36, 36);
        }
        Image currentImage;
        try {
            for (Sonimortet currentSonimortet : grid.getSonimortetList()) {
                currentImage = Sirtet.gameplaySceneImages[currentSonimortet.getType().ordinal()];
                for(SonimortetPositions currentPosition : currentSonimortet.getPositions()) {
                    g.drawImage(currentImage, 173 + 38 * currentPosition.getX(), 132 + 38 * currentPosition.getY(), 36, 36, Sirtet.observer);
                }
            }
        } catch(ConcurrentModificationException e) {
            repaint();
            return;
        }
        boolean[][] heldGrid = getHeldGrid();
        currentImage = Sirtet.gameplaySceneImages[grid.getHeldType().ordinal()];
        for(int xPos = 0; xPos < 3; xPos++) {
            for(int yPos = 0; yPos < 4; yPos++) {
                if(heldGrid[xPos][yPos]) {
                    g.drawImage(currentImage, 30 + 38 * xPos, 170 + 38 * yPos, 37, 37, Sirtet.observer);
                }
            }
        }
        currentImage = Sirtet.gameplaySceneImages[7];
        g.drawImage(currentImage, 0, 0, Sirtet.observer);
    }
    public boolean[][] getHeldGrid() {
        boolean[][] heldGrid = new boolean[3][4];
        int[][] startPos = Sonimortet.getStartingPositions(grid.getHeldType());
        for(int inner = 0; inner < 4; inner++) {
            heldGrid[startPos[0][inner] - 4][startPos[1][inner]] = true;
        }
        return heldGrid;
    }
    public void keyPressed(KeyEvent e) {
        if(isPaused && e.getKeyCode() == 27) {
            resumeGame();
            return;
        } else if(isPaused) return;
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
                grid.getLastSonimortet().shiftAll(-1, 0);
                break;
            case KeyEvent.VK_S:
                grid.getLastSonimortet().softDrop();
                break;
            case KeyEvent.VK_D:
                grid.getLastSonimortet().shiftAll(1, 0);
                break;
            case KeyEvent.VK_SPACE:
                grid.getLastSonimortet().hardDrop();
                break;
            case KeyEvent.VK_E:
                grid.getLastSonimortet().rotateClock();
                break;
            case KeyEvent.VK_Q:
                grid.getLastSonimortet().rotateCounter();
                break;
            case KeyEvent.VK_F:
                grid.swapHeld();
                break;
            case KeyEvent.VK_ESCAPE:
                pauseGame();
        }
    }
    public SirtetGrid getGrid() {
        return grid;
    }
    public JPanel getPanel() {
        return panel;
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}