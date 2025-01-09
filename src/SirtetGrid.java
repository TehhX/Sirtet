import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * This class handles the grid logic, and has the master list of all sonimortets within. Also contains the timer
 * object that moves sonimortets downwards at a set speed over time. Lets GameplayScene add and manipulate
 * sonimortets with key inputs, checks rows, gets last sonimortet or positions in the master list, contains the
 * held sonimortet  etc. */
class SirtetGrid {
    static final int gridSizeX = 10;
    static final int gridSizeY = 16;

    private boolean[][] grid = new boolean[gridSizeX][gridSizeY];

    private BlockID held = randomBlock();

    private int rowsCleared = 0;
    private int swapsTurn = 0;

    private GameplayTimers timer;

    protected GameplayScene parentScene;

    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();

    public SirtetGrid(GameplayScene parentScene) {
        this.parentScene = parentScene;

        GameplayTimers.resetTimer();
        addSonimortet(randomBlock());
    }

    /// Adds a new sonimortet of a given type
    public void addSonimortet(BlockID type) {
        sonimortetList.add(new Sonimortet(type, this));
    }

    /// Checks for row clears, adds a new random sonimortet, sets swaps/turn to none
    public void addSonimortet() {
        checkRows();
        addSonimortet(randomBlock());
        swapsTurn = 0;
    }

    /// Returns a random BlockID
    public BlockID randomBlock() {
        final int IDCount = BlockID.values().length;

        return BlockID.values()[(int) (Math.random() * IDCount)];
    }

    /// Returns last sonimortet, if list is empty return null
    public Sonimortet getLastSonimortet() {
        return sonimortetList.isEmpty() ? null : sonimortetList.get(sonimortetList.size() - 1);
    }

    public SonimortetPositions[] getLastPositions() {
        return getLastSonimortet().getPositions();
    }

    public void restartTimer() {
        if (timer != null)
            timer.stopTimer();

        timer = new GameplayTimers(this);
    }

    public void stopTimer() {
        timer.stopTimer();
    }

    public void updateGrid(boolean repaint) {
        try {
            populateGrid();
        /// Recursively calls itself if list is being modified by other process
        } catch (ConcurrentModificationException ignored) {
            updateGrid(repaint);
            return;
        /// If given index not present i.e. deleted recently do nothing
        } catch (NullPointerException ignored) {}

        if (repaint)
            parentScene.repaint();
    }

    /// Resets grid, fills from list
    public void populateGrid() throws ConcurrentModificationException {
        grid = new boolean[gridSizeX][gridSizeY];

        for (Sonimortet sonimortet : sonimortetList)
            for (SonimortetPositions positions : sonimortet.getPositions())
                grid[positions.getX()][positions.getY()] = true;
    }

    /// Checks for row clears
    public void checkRows() {
        for (int yPos = gridSizeY - 1; yPos >= 0; yPos--)
            if (rowFilled(yPos)) {
                clearRow(yPos);
                shiftAbove(yPos);
                updateGrid(false);
                checkRows();

                return;
            }

        parentScene.pointIncrease(rowsCleared);
        rowsCleared = 0;
    }

    /// Checks to see if a row has been filled
    public boolean rowFilled(int yPos) {
        int rowFilledCount = 0;

        for (int xPos = 0; xPos < gridSizeX; xPos++)
            if (grid[xPos][yPos])
                rowFilledCount++;

        return rowFilledCount == gridSizeX;
    }

    /// Handles what happens when a row is cleared i.e. deleting, moving, removing
    public void clearRow(int yPos) {
        rowsCleared++;

        boolean deleted;
        do {
            deleted = false;

            for (int i = 0; i < sonimortetList.size(); i++) {
                Sonimortet current = sonimortetList.get(i);

                for (int ii = 0; ii < current.getPositions().length; ii++) {
                    if (current.getPositions()[ii].getY() == yPos) {
                        deleted = true;
                        current.delete(ii);

                        if (current.getPositions().length == 0)
                            sonimortetList.remove(current);
                    }
                }
            }
        } while (deleted);
    }

    /// Moving all pieces above a given yPos down by one grid
    public void shiftAbove(int yPos) {
        for (Sonimortet sonimortet : sonimortetList)
            for (SonimortetPositions positions : sonimortet.getPositions())
                if (positions.getY() < yPos)
                    positions.shiftSingle(0, 1, false);
    }

    /// Swaps help piece with piece in play. Can only be done three times in one turn
    public void swapHeld() {
        if (swapsTurn > 2)
            return;

        swapsTurn++;
        BlockID tempType = held;
        held = getLastSonimortet().getType();
        sonimortetList.remove(getLastSonimortet());
        updateGrid(false);
        addSonimortet(tempType);
    }

    public BlockID getHeldType() {
        return held;
    }

    public boolean getGrid(int outerIndex, int innerIndex) {
        return grid[outerIndex][innerIndex];
    }

    public ArrayList<Sonimortet> getSonimortetList() {
        return sonimortetList;
    }
}