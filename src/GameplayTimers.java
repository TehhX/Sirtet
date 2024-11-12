import java.util.Timer;
import java.util.TimerTask;
class GameplayTimers {
    private Timer bigTimer;
    private TimerTask smallTimer;
    private static int tMinus = 1300;
    private static int timesDecremented = 0;
    public GameplayTimers() {}
    public GameplayTimers(SirtetGrid grid) {
        bigTimer = new Timer();
        smallTimer = new TimerTask() {
            public void run() {
                if(grid.getLastSonimortet().checkSurrounding(0, 1)) {
                    grid.getLastSonimortet().hardDrop();
                    decrementTimer();
                } else {
                    grid.getLastSonimortet().softDrop();
                }
            }
        };
        bigTimer.schedule(smallTimer, tMinus);
    }
    public void decrementTimer() {
        if(tMinus >= 140) {
            tMinus = (int) (1300.0 * Math.pow(0.5, (timesDecremented / 21.0)) + 130.8);
            timesDecremented++;
        }
    }
    public void stopTimer() {
        bigTimer.cancel();
    }
}