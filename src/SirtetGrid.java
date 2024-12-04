import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    private ArrayList<Sonimortet> sonimortetsToDelete = new ArrayList<>();
    public SirtetGrid(GameplayScene parentScene) {
        grid = new boolean[gridX][gridY];
        this.parentScene = parentScene;
        GameplayTimers.resetTimer();
        addSonimortet();
    }
    public void addSonimortet(BlockType type) {
        checkRows();
        sonimortetList.add(new Sonimortet(type, this));
        SirtetAudio.playAudio("blockPlace.wav");
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
        grid = new boolean[gridX][gridY];
        if(sonimortetList.isEmpty()) return;
        try {
            populateGrid();
        } catch (ConcurrentModificationException ignored) {
            updateGrid(repaint);
            return;
        }
        deleteMissing();
        sonimortetsToDelete.clear();
        if (repaint) parentScene.repaint();
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
            for(int i = 0; i < sonimortetList.size(); i++) {
                Sonimortet current = sonimortetList.get(i);
                for(int posIndex = 0; posIndex < current.getPositions().length; posIndex++) {
                    if(current.getPositions()[posIndex].getY() == yPos) {
                        current.delete(posIndex);
                        if(current.getPositions().length == 0) {
                            sonimortetList.remove(current);
                            sonimortetsToDelete.add(current);
                        }
                        deleted = true;
                    }
                }
            }
        } while(deleted);
    }
    public void deleteMissing() {
        for(Sonimortet current : sonimortetList) {
            for(Sonimortet currentDelete : sonimortetsToDelete) {
                if(current == currentDelete) sonimortetList.remove(current);
            }
        }
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
    public ArrayList<Sonimortet> getSonimortetList() {
        return sonimortetList;
    }
}