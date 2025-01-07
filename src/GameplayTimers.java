import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class handles the softDrop timers to move the game of Sirtet along. It prevents the player
 * from sitting idle without pausing. The timers reduce in execution time the more blocks have been placed. */
class GameplayTimers implements ActionListener {
    private static int tMinus = 1342;
    // n is the amount of moves that have been played since the last reset
    private static int n = 0;

    private static Timer timer;

    private SirtetGrid grid;

    /// Starts a new timer
    public GameplayTimers(SirtetGrid grid) {
        this.grid = grid;
        timer = new Timer(tMinus, this);

        timer.start();
    }

    /// Decrements the millisecond delay of the timer from the function: T(n) = 0.9822^(n - 392) + 200
    public static void decrementTimer() {
        n++;
        tMinus = (int) (Math.round(Math.pow(0.9822, n - 392) + 200));
    }

    /// Resets the timer data to start
    public static void resetTimer() {
        n = 0;
        tMinus = 1342;
    }

    public void stopTimer() {
        timer.stop();
    }

    /// Called when timer fires. Either soft or hard drops depending on height, updates grid
    public void actionPerformed(ActionEvent e) {
        if (grid.getLastSonimortet().getHeight() == 0)
            grid.getLastSonimortet().hardDrop();
        else
            grid.getLastSonimortet().softDrop();

        grid.updateGrid(true);
    }
}