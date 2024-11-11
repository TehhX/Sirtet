import java.util.Timer;
import java.util.TimerTask;
class GameplayTimers {
    private Timer bigTimer;
    private TimerTask smallTimer;
    private static int tMinus = 1300;
    private static int timesDecremented = 0;
    public GameplayTimers() {}
    public GameplayTimers(GameplayScene parentScene) {
        bigTimer = new Timer();
        smallTimer = new TimerTask() {
            public void run() {
                if(parentScene.getGrid().getLastSonimortet().checkSurrounding(0, 1)) {
                    parentScene.getGrid().addSonimortet();
                    decrementTimer();
                } else {
                    parentScene.getGrid().getLastSonimortet().softDrop();
                }
                parentScene.getGrid().updateGrid();
                parentScene.repaint();
            }
        };
        bigTimer.schedule(smallTimer, tMinus);
    }
    public void decrementTimer() {
        if(tMinus >= 140) {
            tMinus = (int) (1300.0 * Math.pow(0.5, (timesDecremented / 21.0)) + 130.8);
            timesDecremented++;
        }
        System.out.println(tMinus);
    }
    public void stopTimer() {
        bigTimer.cancel();
    }
}