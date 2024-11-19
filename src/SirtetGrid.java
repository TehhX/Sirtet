import java.util.ArrayList;
import java.util.ConcurrentModificationException;
class SirtetGrid {
    /**
     * This class handles the grid logic, and has the master list of all sonimortets within. Also contains the timer
     * object that moves sonimortets downwards at a set speed over time. Lets GameplayScene add and manipulate
     * sonimortets with key inputs, checks rows, gets last sonimortet or positions in the master list, contains the
     * held sonimortet  etc.
     */
    private boolean[][] grid;
    private int held;
    private int rowsCleared;
    private GameplayTimers timer;
    private GameplayScene parentScene;
    private int swapsTurn;
    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    public SirtetGrid(GameplayScene parentScene) {
        rowsCleared = 0;
        grid = new boolean[10][16];
        swapsTurn = 0;
        held = (int) (Math.random() * 7);
        this.parentScene = parentScene;
        new GameplayTimers().resetTimer();
        addSonimortet();
    }
    public void addSonimortet(int type) {
        sonimortetList.add(new Sonimortet(type, this));
        SirtetAudio.playAudio("blockPlace.wav");
        updateGrid(true);
    }
    public void addSonimortet() {
        addSonimortet((int) (Math.random() * 7));
        parentScene.pointIncrease(-1);
        swapsTurn = 0;
    }
    public Sonimortet getLastSonimortet() {
        return sonimortetList.get(sonimortetList.size() - 1);
    }
    public SonimortetPositions[] getLastPositions() {
        return getLastSonimortet().getPositions();
    }
    public void restartTimer() {
        if(timer != null) timer.stopTimer();
        timer = new GameplayTimers(this);
    }
    public void stopTimer() {
        if(timer == null) return;
        timer.stopTimer();
        timer = null;
    }
    public void updateGrid(boolean repaint) {
        try {
            grid = new boolean[10][16];
            for (Sonimortet sonimortet : sonimortetList) {
                for (int inner = 0; inner < sonimortet.getPositions().length; inner++) {
                    int x = sonimortet.getPositions()[inner].getX();
                    int y = sonimortet.getPositions()[inner].getY();
                    grid[x][y] = true;
                }
            }
            if (!sonimortetList.isEmpty()) {
                for (SonimortetPositions sonimortet : getLastPositions()) {
                    if (sonimortet.getY() == 0) {
                        checkRows();
                        break;
                    }
                }
            }
            if (repaint) parentScene.repaint();
        } catch (ConcurrentModificationException e) {
            updateGrid(repaint);
        }
    }
    public void checkRows() {
        for(int outer = 15; outer >= 0; outer--) {
            int count = 0;
            for(int inner = 0; inner < 10; inner++) {
                if(grid[inner][outer]) count++;
            }
            if(count == 10) clearRow(outer);
        }
        parentScene.pointIncrease(rowsCleared);
        rowsCleared = 0;
    }
    public void clearRow(int y) {
        rowsCleared++;
        int deleted;
        do {
            deleted = 0;
            for (Sonimortet sonimortet : sonimortetList) {
                for (int i = 0; i < sonimortet.getPositions().length; i++) {
                    if (sonimortet.getPositions()[i].getY() == y) {
                        sonimortet.delete(i);
                        deleted++;
                    }
                }
            }
        } while(deleted != 0);
        for(int outer = 0; outer < sonimortetList.size() - 1; outer++) {
            for(int inner = 0; inner < sonimortetList.get(outer).getPositions().length; inner++) {
                if(sonimortetList.get(outer).getPositions()[inner].getY() < y) {
                    sonimortetList.get(outer).getPositions()[inner].shiftSingle(0, 1, false);
                }
            }
        }
        updateGrid(false);
    }
    public void swapHeld() {
        if(swapsTurn == 3) return;
        swapsTurn++;
        int tempType = held;
        held = getLastSonimortet().getType();
        sonimortetList.remove(getLastSonimortet());
        updateGrid(false);
        addSonimortet(tempType);
        updateGrid(true);
    }
    public GameplayScene getParentScene() {
        return parentScene;
    }
    public int getHeldType() {
        return held;
    }
    public boolean getGrid(int outerIndex, int innerIndex) {
        return grid[outerIndex][innerIndex];
    }

    public ArrayList<Sonimortet> getSonimortetList() {
        return sonimortetList;
    }
}