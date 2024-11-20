import java.util.Timer;
import java.util.TimerTask;
class GameplayTimers {
    private Timer bigTimer;
    private static int tMinus = 1300;
    private static int timesDecremented = 0;
    public GameplayTimers() {}
    public GameplayTimers(SirtetGrid grid) {
        bigTimer = new Timer();
        TimerTask smallTimer = new TimerTask() {
            public void run() {
                if(!grid.getLastSonimortet().allCanMove(0, 1)) {
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
        tMinus = (int) (Math.round(Math.pow(0.9822, timesDecremented - 392) + 200));
        timesDecremented++;
    }
    public void resetTimer() {
        timesDecremented = 0;
        tMinus = 1300;
    }
    public void stopTimer() {
        bigTimer.cancel();
    }
}