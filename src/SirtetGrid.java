import java.util.ArrayList;
class SirtetGrid {
    /**
     * This class handles the grid logic, and has the master list of all sonimortets within. Also contains the timer
     * object that moves sonimortets downwards at a set speed over time. Lets GameplayScene add and manipulate
     * sonimortets with key inputs, checks rows, gets last sonimortet or positions in the master list, contains the
     * held sonimortet  etc.
     */
    private boolean[][] grid;
    private BlockType held;
    private int rowsCleared = 0;
    private GameplayTimers timer;
    private GameplayScene parentScene;
    private int swapsTurn;
    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    public SirtetGrid(GameplayScene parentScene) {
        grid = new boolean[10][16];
        swapsTurn = 0;
        held = randomBlock();
        this.parentScene = parentScene;
        GameplayTimers.resetTimer();
        addSonimortet();
    }
    public void addSonimortet(BlockType type) {
        sonimortetList.add(new Sonimortet(type, this));
        SirtetAudio.playAudio("blockPlace.wav");
        updateGrid(true);
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
        grid = new boolean[10][16];
        if(sonimortetList.isEmpty()) return;
        populateGrid();
        checkRowsCleared();
        if(repaint) parentScene.repaint();
    }
    public void populateGrid() {
        for(Sonimortet sonimortet : sonimortetList) {
            for(int inner = 0; inner < sonimortet.getPositions().length; inner++) {
                int x = sonimortet.getPositions()[inner].getX();
                int y = sonimortet.getPositions()[inner].getY();
                grid[x][y] = true;
            }
        }
    }
    public void checkRowsCleared() {
        for(SonimortetPositions sonimortet : getLastPositions()) {
            if(sonimortet.getY() == 0) {
                checkRow();
                break;
            }
        }
    }
    public void checkRow() {
        for(int yPos = 15; yPos >= 0; yPos--) {
            int rowFilledCount = 0;
            for(int xPos = 0; xPos < 10; xPos++) {
                if(grid[xPos][yPos]) rowFilledCount++;
            }
            if(rowFilledCount == 10) {
                clearRow(yPos);
                shiftAbove(yPos);
                updateGrid(false);
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
            for (Sonimortet sonimortet : sonimortetList) {
                for (int posIndex = 0; posIndex < sonimortet.getPositions().length; posIndex++) {
                    if (sonimortet.getPositions()[posIndex].getY() == yPos) {
                        sonimortet.delete(posIndex);
                        deleted = true;
                    }
                }
            }
        } while(deleted);
    }
    public void shiftAbove(int yPos) {
        for(int soniIndex = 0; soniIndex < sonimortetList.size() - 1; soniIndex++) {
            for(int xPos = 0; xPos < sonimortetList.get(soniIndex).getPositions().length; xPos++) {
                if(sonimortetList.get(soniIndex).getPositions()[xPos].getY() < yPos) {
                    sonimortetList.get(soniIndex).getPositions()[xPos].shiftSingle(0, 1, false);
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