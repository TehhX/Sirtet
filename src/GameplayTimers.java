import java.util.Timer;
import java.util.TimerTask;
class GameplayTimers {
    private Timer bigTimer;
    private TimerTask smallTimer;
    public GameplayTimers(GameplayScene gameplayScene, int performTask) {
        bigTimer = new Timer();
        smallTimer = new TimerTask() {
            public void run() {
                switch(performTask) {
                    case 0:
                        moveDownTimer();
                        break;
                    case 1:
                        placeTimer();
                        break;
                }
                bigTimer.cancel();
            }
        };
    }
    public void moveDownTimer() {

    }
    public void placeTimer() {

    }
    public void stopTimer() {
        bigTimer.cancel();
    }
}