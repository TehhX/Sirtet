import java.util.Timer;
import java.util.TimerTask;
class GameplayTimers {
    /**
     * This class handles the softDrop timers to move the game of Sirtet along. It prevents the player
     * from sitting idle without pausing. The timers reduce in execution time the more blocks have been placed.
     * It follows a function of 0.9822 ^ (timesDecremented - 392) + 200, where timesDecremented is incremented
     * by one every time a new sonimortet is placed. An anonymous TimerTask class is used for the run
     * method to help with code readability, while keeping the class concise.
     */
    private Timer bigTimer;
    private static int tMinus = 1300;
    private static int timesDecremented = 0;
    public GameplayTimers(SirtetGrid grid) {
        bigTimer = new Timer();
        TimerTask smallTimer = new TimerTask() {
            public void run() {
                grid.getLastSonimortet().softDrop();
            }
        };
        bigTimer.schedule(smallTimer, tMinus);
    }
    public static void decrementTimer() {
        tMinus = (int) (Math.round(Math.pow(0.9822, timesDecremented - 392) + 200));
        timesDecremented++;
    }
    public static void resetTimer() {
        timesDecremented = 0;
        tMinus = 1300;
    }
    public void stopTimer() {
        bigTimer.cancel();
    }
}