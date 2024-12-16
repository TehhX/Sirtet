import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class handles the softDrop timers to move the game of Sirtet along. It prevents the player
 * from sitting idle without pausing. The timers reduce in execution time the more blocks have been placed.
 * It follows a function of round(0.9822 ^ (timesDecremented - 392) + 200), where timesDecremented is incremented
 * by one every time a new Sonimortet is placed.
 */
class GameplayTimers implements ActionListener {
    private static Timer timer;
    private static int tMinus = 1342;
    private static int timesDecremented = 0;
    private SirtetGrid grid;

    public GameplayTimers(SirtetGrid grid) {
        this.grid = grid;
        timer = new Timer(tMinus, this);
        timer.start();
    }

    public static void decrementTimer() {
        timesDecremented++;
        tMinus = (int) (Math.round(Math.pow(0.9822, timesDecremented - 392) + 200));
    }

    public static void resetTimer() {
        timesDecremented = 0;
        tMinus = 1342;
    }

    public void stopTimer() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        if (grid.getLastSonimortet().getHeight() == 0) grid.getLastSonimortet().hardDrop();
        else grid.getLastSonimortet().softDrop();
        try { grid.updateGrid(true); } catch (NullPointerException ignored) {}
    }
}