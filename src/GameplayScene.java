import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ConcurrentModificationException;

/**
 * This class handles the main gameplay scene, and all within. It also needs a SirtetGrid object to pass user
 * input to.
 */
class GameplayScene extends SirtetPanel implements KeyListener {
    private JLabel score = SirtetWindow.labelLeft("", Sirtet.SILKSCREEN_60, 0, 0);
    private JPanel pausePanel = pausePanelSetup();
    private JPanel playPanel = playPanelSetup();
    private boolean isPaused = false;
    private SirtetGrid grid = new SirtetGrid(this);

    public GameplayScene() {
        super(true);
        SaveData.currentScore = 0;
        updateScoreLabel();
        add(playPanel);
        add(pausePanel);
    }

    public SirtetPanel playPanelSetup() {
        SirtetPanel panel = new SirtetPanel(false, g -> {
            drawPremonition(g);
            drawReal(g);
            drawHeld(g);
            g.drawImage(Sirtet.gameplaySceneImages[7], 0, 0, Sirtet.observer);
        });
        panel.add(score);
        return panel;
    }

    public void drawPremonition(Graphics g) {
        g.setColor(new Color(87, 223, 255));
        int height = grid.getLastSonimortet().getHeight();
        for (SonimortetPositions pos : grid.getLastPositions()) {
            g.fillRect(173 + 38 * pos.getX(), 132 + 38 * (pos.getY() + height), 36, 36);
        }
    }

    public void drawReal(Graphics g) {
        try {
            for (Sonimortet currentSonimortet : grid.getSonimortetList()) {
                Image currentImage = Sirtet.gameplaySceneImages[currentSonimortet.getType().ordinal()];
                for (SonimortetPositions currentPosition : currentSonimortet.getPositions()) {
                    g.drawImage(currentImage, 173 + 38 * currentPosition.getX(), 132 + 38 * currentPosition.getY(), Sirtet.observer);
                }
            }
        } catch (ConcurrentModificationException ignored) {
            drawReal(g);
        }
    }

    public void drawHeld(Graphics g) {
        boolean[][] heldGrid = new boolean[3][4];
        int[][] startPos = Sonimortet.getStartingPositions(grid.getHeldType());
        for (int inner = 0; inner < 4; inner++) {
            heldGrid[startPos[0][inner] - 4][startPos[1][inner]] = true;
        }
        for (int xPos = 0; xPos < 3; xPos++) {
            for (int yPos = 0; yPos < 4; yPos++) {
                if (heldGrid[xPos][yPos]) {
                    g.drawImage(Sirtet.gameplaySceneImages[grid.getHeldType().ordinal()], 30 + 38 * xPos, 170 + 38 * yPos, 37, 37, Sirtet.observer);
                }
            }
        }
    }

    public SirtetPanel pausePanelSetup() {
        SirtetPanel panel = new SirtetPanel(false);
        panel.add(new ReactiveButton(Sirtet.menuImages[3], 400, e -> SirtetWindow.changeScene(SceneID.Menu)));
        panel.add(SirtetWindow.labelCenter("Game Paused", Sirtet.SILKSCREEN_60, 250));
        panel.add(new VolumeSliders());
        panel.setVisible(false);
        return panel;
    }

    public void invertPause() {
        isPaused = !isPaused;
        if (isPaused) grid.stopTimer();
        else grid.restartTimer();
        pausePanel.setVisible(isPaused);
        playPanel.setVisible(!isPaused);
        grid.updateGrid(true);
    }

    public void pointIncrease(int rowsCleared) {
        SirtetAudio.playAudio(AudioID.values()[rowsCleared + 1]);
        switch (rowsCleared) {
            case 0:
                SaveData.currentScore += 25;
                break;
            case 1:
                SaveData.currentScore += 100;
                break;
            case 2:
                SaveData.currentScore += 250;
                break;
            case 3:
                SaveData.currentScore += 600;
                break;
            case 4:
                SaveData.currentScore += 1650;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rowsCleared);
        }
        updateScoreLabel();
    }

    public void updateScoreLabel() {
        score.setText(SaveData.currentScore + "");
        int width = (int) score.getPreferredSize().getWidth();
        score.setBounds(550 - width, 35, width, 50);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) invertPause();
        if (isPaused) return;
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
        }
        grid.updateGrid(true);
    }

    public SirtetGrid getGrid() {
        return grid;
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}