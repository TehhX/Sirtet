import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ConcurrentModificationException;

/**
 * This class handles the main gameplay scene, and all within. It also needs a SirtetGrid object to pass user
 * input to.
 */
class GameplayScene extends JPanel implements KeyListener {
    private boolean isPaused = false;
    private SirtetGrid grid;
    private JLabel score = new JLabel();
    private JLabel paused = new JLabel("Game Paused");
    private JPanel playPanel;
    private JPanel pausePanel;
    private ReactiveButton quitButton;
    private VolumeSlidersPanel volumeSlidersPanel;

    public GameplayScene() {
        SaveData.currentScore = -25;
        SirtetWindow.labelSetupLeft(score, Sirtet.SILKSCREEN_60, 0, 0);
        SirtetWindow.labelSetupCenter(paused, Sirtet.SILKSCREEN_60, 250);
        genericPanelSetup(this);
        setBackground(Sirtet.SIRTET_GREEN);
        setOpaque(true);
        volumeSlidersPanel = new VolumeSlidersPanel(VolumeSlidersPanel.VOLUME_CENTER_X, 350);
        quitButton = new ReactiveButton(Sirtet.menuImages[2], Sirtet.menuImages[5], 425) {
            public void actionPerformed(ActionEvent ignored) {
                SirtetWindow.changeScene(SceneID.Menu);
            }
        };
        grid = new SirtetGrid(this);
        playPanelSetup();
        pausePanelSetup();
    }

    public void genericPanelSetup(JPanel panel) {
        panel.setLayout(null);
        panel.setSize(SirtetWindow.FRAME_SIZE_X, SirtetWindow.FRAME_SIZE_Y);
        panel.setOpaque(false);
    }

    public void playPanelSetup() {
        playPanel = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(new Color(103, 215, 237));
                int lastHeight = grid.getLastSonimortet().getHeight();
                for (SonimortetPositions pos : grid.getLastPositions()) {
                    g.fillRect(173 + 38 * pos.getX(), 132 + 38 * (pos.getY() + lastHeight), 36, 36);
                }
                Image currentImage;
                try {
                    for (Sonimortet currentSonimortet : grid.getSonimortetList()) {
                        currentImage = Sirtet.gameplaySceneImages[currentSonimortet.getType().ordinal()];
                        for (SonimortetPositions currentPosition : currentSonimortet.getPositions()) {
                            g.drawImage(currentImage, 173 + 38 * currentPosition.getX(), 132 + 38 * currentPosition.getY(), 36, 36, Sirtet.observer);
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    repaint();
                    return;
                }
                boolean[][] heldGrid = getHeldGrid();
                currentImage = Sirtet.gameplaySceneImages[grid.getHeldType().ordinal()];
                for (int xPos = 0; xPos < 3; xPos++) {
                    for (int yPos = 0; yPos < 4; yPos++) {
                        if (heldGrid[xPos][yPos]) {
                            g.drawImage(currentImage, 30 + 38 * xPos, 170 + 38 * yPos, 37, 37, Sirtet.observer);
                        }
                    }
                }
                currentImage = Sirtet.gameplaySceneImages[7];
                g.drawImage(currentImage, 0, 0, Sirtet.observer);
            }
        };
        genericPanelSetup(playPanel);
        playPanel.add(score);
        add(playPanel);
    }

    public void pausePanelSetup() {
        pausePanel = new JPanel();
        genericPanelSetup(pausePanel);
        pausePanel.add(quitButton);
        pausePanel.add(paused);
        pausePanel.add(volumeSlidersPanel);
    }

    public void pauseGame() {
        grid.stopTimer();
        isPaused = true;
        remove(playPanel);
        add(pausePanel);
        repaint();
    }

    public void resumeGame() {
        grid.restartTimer();
        isPaused = false;
        remove(pausePanel);
        add(playPanel);
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
                SaveData.currentScore += 250;
                SirtetAudio.playAudio("twoRow.wav");
                break;
            case 3:
                SaveData.currentScore += 600;
                SirtetAudio.playAudio("threeRow.wav");
                break;
            default:
                SaveData.currentScore += 1650;
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

    public boolean[][] getHeldGrid() {
        boolean[][] heldGrid = new boolean[3][4];
        int[][] startPos = Sonimortet.getStartingPositions(grid.getHeldType());
        for (int inner = 0; inner < 4; inner++) {
            heldGrid[startPos[0][inner] - 4][startPos[1][inner]] = true;
        }
        return heldGrid;
    }

    public void keyPressed(KeyEvent e) {
        if (isPaused) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) resumeGame();
            return;
        }
        switch (e.getKeyCode()) {
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
        try {
            grid.updateGrid(true);
        } catch (NullPointerException ignored) {}
    }

    public SirtetGrid getGrid() {
        return grid;
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}