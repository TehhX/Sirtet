import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ConcurrentModificationException;

/**
 * This class handles the main gameplay scene, and all within. It also needs a SirtetGrid object to pass user
 * input to. */
class GameplayScene extends SirtetScene implements KeyListener {
    private static SirtetLabel score = new SirtetLabel(60);
    public static SirtetGrid grid;
    private boolean isPaused;

    private SirtetPanel pausePanel = pausePanelSetup();
    private SirtetPanel playPanel = playPanelSetup();

    private SirtetButton returnButton;

    public GameplayScene() {
        super(false);

        updateScoreLabel();
        add(playPanel);
        add(pausePanel);
    }

    /// Sets up and returns the play panel
    private SirtetPanel playPanelSetup() {
        SirtetPanel panel = new SirtetPanel(true, g -> {
            drawPremonition(g);
            drawReal(g);
            drawHeld(g);
            g.drawImage(Sirtet.gameplaySceneImages[7], 0, 0, Sirtet.observer);
        });
        panel.add(score);

        return panel;
    }

    /// Method to draw the translucent cyan sonimortet used to hard-drop
    private void drawPremonition(Graphics g) {
        g.setColor(new Color(87, 223, 255));
        int height = grid.getLastSonimortet().getHeight();
        for (SonimortetPositions pos : grid.getLastPositions())
            g.fillRect(173 + 38 * pos.getX(), 132 + 38 * (pos.getY() + height), 36, 36);
    }

    /// Method to draw all sonimortets in SirtetGrid's master list
    private void drawReal(Graphics g) {
        try {
            for (Sonimortet currentSonimortet : grid.getSonimortetList()) {
                for (SonimortetPositions currentPosition : currentSonimortet.getPositions())
                    g.drawImage(
                        Sirtet.gameplaySceneImages[currentSonimortet.getType().ordinal()],
                        173 + 38 * currentPosition.getX(),
                        132 + 38 * currentPosition.getY(),
                        36,
                        36,
                        Sirtet.observer
                    );
            }
        }
        // Will keep recursively calling itself if the list is being modified until the list is ready
        catch (ConcurrentModificationException ignored) {
            drawReal(g);
        }
    }

    /// Method to draw the held piece
    private void drawHeld(Graphics g) {
        boolean[][] heldGrid = new boolean[3][4];
        int[][] startPos = Sonimortet.getStartingPositions(grid.getHeldType());

        for (int i = 0; i < 4; i++)
            heldGrid[startPos[0][i] - 4][startPos[1][i]] = true;

        for (int xPos = 0; xPos < 3; xPos++)
            for (int yPos = 0; yPos < 4; yPos++)
                if (heldGrid[xPos][yPos])
                    g.drawImage(
                        Sirtet.gameplaySceneImages[grid.getHeldType().ordinal()],
                        30 + 38 * xPos,
                        170 + 38 * yPos,
                        37,
                        37,
                        Sirtet.observer
                    );
    }

    /// Sets up and returns the pause panel
    private SirtetPanel pausePanelSetup() {
        SirtetPanel panel = new SirtetPanel(true);
        returnButton = new SirtetButton(
            "RETURN",
            () -> SirtetWindow.changeScene(SceneID.Menu),
            350,
            45
        );
        panel.add(returnButton);
        panel.add(new LabelCenter("Game Paused", 60, 250));
        panel.add(new VolumeSliders());
        panel.setVisible(false);
        return panel;
    }

    /// Updates the score label with new score, keeps it justified right with respect to new width
    public static void updateScoreLabel() {
        score.setText(SaveData.currentScore + "");
        int width = (int) score.getPreferredSize().getWidth();
        score.setBounds(550 - width, 35, width, 50);
    }

    /// If paused, play. If playing, pause. Repaints and updates as well
    private void invertPause() {
        isPaused = !isPaused;

        if (isPaused) {
            grid.stopTimer();
            returnButton.addButtonListener();
        }
        else {
            grid.restartTimer();
            returnButton.removeButtonListener();
        }

        pausePanel.setVisible(isPaused);
        playPanel.setVisible(!isPaused);

        grid.updateGrid(true);
    }

    /// Plays score audio, increases points given an amount of rows cleared in a single turn, updates score label
    public static void pointIncrease(int rowsCleared) {
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

    /// Takes user input, calls methods in accordance. If paused && input != esc, do nothing
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            invertPause();
        if (isPaused)
            return;

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

        if (grid != null)
            grid.updateGrid(true);
    }

    public void addScene() {
        grid = new SirtetGrid();

        if (isPaused)
            invertPause();

        SaveData.currentScore = 0;
        updateScoreLabel();

        SirtetWindow.frame.addKeyListener(this);
        SirtetWindow.frame.getContentPane().add(this);
        setVisible(true);
    }

    public void removeScene() {
        grid.stopTimer();
        grid = null;

        SirtetWindow.frame.removeKeyListener(this);
        SirtetWindow.frame.getContentPane().remove(this);
        setVisible(false);
    }

    public void keyTyped(KeyEvent ignored) {}
    public void keyReleased(KeyEvent ignored) {}
}