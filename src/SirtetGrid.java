import java.util.ConcurrentModificationException;
import java.util.LinkedList;
class SirtetGrid {
    /**
     * This class handles the grid logic, and has the master list of all sonimortets within. Also contains the timer
     * object that moves sonimortets downwards at a set speed over time. Lets GameplayScene add and manipulate
     * sonimortets with key inputs, checks rows, gets last sonimortet or positions in the master list, contains the
     * held sonimortet  etc.
     */
    static final int gridX = 10;
    static final int gridY = 16;
    private boolean[][] grid;
    private BlockType held = randomBlock();
    private int rowsCleared = 0;
    private GameplayTimers timer;
    private GameplayScene parentScene;
    private int swapsTurn = 0;
    private LinkedList<Sonimortet> sonimortetList = new LinkedList<>();
    public SirtetGrid(GameplayScene parentScene) {
        grid = new boolean[gridX][gridY];
        this.parentScene = parentScene;
        GameplayTimers.resetTimer();
        addSonimortet();
    }
    public void addSonimortet(BlockType type) {
        checkRows();
        restartTimer();
        sonimortetList.add(new Sonimortet(type, this));
        SirtetAudio.playAudio("blockPlace.wav");
        updateGrid(true);
        new RuntimeException().printStackTrace();
    }
    public void addSonimortet() {
        addSonimortet(randomBlock());
        parentScene.pointIncrease(-1);
        swapsTurn = 0;
    }
    public BlockType randomBlock() {
        BlockType[] blockTypes = BlockType.values();
        return blockTypes[(int) (Math.random() * blockTypes.length)];
    }
    public Sonimortet getLastSonimortet() {
        return sonimortetList.getLast();
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
        grid = new boolean[gridX][gridY];
        if(sonimortetList.isEmpty()) return;
        try {
            populateGrid();
            if (repaint) parentScene.repaint();
        } catch(ConcurrentModificationException cme) {
            updateGrid(repaint);
        }
    }
    public void populateGrid() throws ConcurrentModificationException {
        for(Sonimortet sonimortet : sonimortetList) {
            for(SonimortetPositions positions : sonimortet.getPositions()) {
                grid[positions.getX()][positions.getY()] = true;
            }
        }
    }
    public void checkRows() {
        for(int yPos = gridY - 1; yPos >= 0; yPos--) {
            int rowFilledCount = 0;
            for(int xPos = 0; xPos < gridX; xPos++) {
                if(grid[xPos][yPos]) rowFilledCount++;
            }
            if(rowFilledCount == gridX) {
                clearRow(yPos);
                shiftAbove(yPos);
                updateGrid(false);
                checkRows();
            }
        }
        parentScene.pointIncrease(rowsCleared);
        rowsCleared = 0;
    }
    public void clearRow(int yPos) {
        rowsCleared++;
        boolean deleted;
        do {
            deleted = false;
            for(Sonimortet sonimortet : sonimortetList) {
                for(int posIndex = 0; posIndex < sonimortet.getPositions().length; posIndex++) {
                    if(sonimortet.getPositions()[posIndex].getY() == yPos) {
                        sonimortet.delete(posIndex);
                        deleted = true;
                    }
                }
            }
        } while(deleted);
    }
    public void shiftAbove(int yPos) {
        for(Sonimortet sonimortet : sonimortetList) {
            for(SonimortetPositions positions : sonimortet.getPositions()) {
                if(positions.getY() < yPos) {
                    positions.shiftSingle(0, 1, false);
                }
            }
        }
    }
    public void swapHeld() {
        if(swapsTurn == 3) return;
        swapsTurn++;
        BlockType tempType = held;
        held = getLastSonimortet().getType();
        sonimortetList.remove(getLastSonimortet());
        updateGrid(false);
        addSonimortet(tempType);
        updateGrid(true);
    }
    public BlockType getHeldType() {
        return held;
    }
    public boolean getGrid(int outerIndex, int innerIndex) {
        return grid[outerIndex][innerIndex];
    }
    public LinkedList<Sonimortet> getSonimortetList() {
        return sonimortetList;
    }
}