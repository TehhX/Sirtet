import java.util.ArrayList;
class SirtetGrid {
    public boolean[][] grid;
    private char held;
    private int rowsCleared;
    GameplayScene parentScene;
    private final ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    public SirtetGrid(GameplayScene parentScene) {
        rowsCleared = 0;
        grid = new boolean[10][16];
        held = randomChar();
        this.parentScene = parentScene;
    }
    public void addSonimortet(char type) {
        sonimortetList.add(new Sonimortet(type, this));
    }
    public void addSonimortet() {
        sonimortetList.add(new Sonimortet(randomChar(), this));
        parentScene.pointIncrease();
        getLastSonimortet().restartTimer();
    }
    public char randomChar() {
        switch((int) (Math.random() * 7)) {
            case 0:
                return  'O';
            case 1:
                return  'I';
            case 2:
                return  'S';
            case 3:
                return  'Z';
            case 4:
                return  'L';
            case 5:
                return  'J';
            default:
                return 'T';
        }
    }
    public Sonimortet getLastSonimortet() {
        return sonimortetList.get(sonimortetList.size() - 1);
    }
    public SonimortetPositions[] getLastPositions() {
        return getLastSonimortet().getPositions();
    }
    public void updateGrid() {
        grid = new boolean[10][16];
        for (Sonimortet sonimortet : sonimortetList) {
            for (int inner = 0; inner < sonimortet.getPositions().length; inner++) {
                int x = sonimortet.getPositions()[inner].getX();
                int y = sonimortet.getPositions()[inner].getY();
                grid[x][y] = true;
            }
        }
        if(!sonimortetList.isEmpty()) {
            for(SonimortetPositions sonimortet : getLastPositions()) {
                if(sonimortet.getY() == 0) {
                    checkRows();
                    return;
                }
            }
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
        parentScene.repaint();
        updateGrid();
    }
    public void swapHeld() {
        int currentHigh = 15;
        for(SonimortetPositions position : getLastPositions()) {
            if(position.getY() < currentHigh) currentHigh = position.getY();
        }
        if(currentHigh >= 2) return;
        char tempType = held;
        held = getLastSonimortet().getType();
        sonimortetList.remove(getLastSonimortet());
        addSonimortet(tempType);
    }
    public char getHeld() {
        return held;
    }
    public boolean getGrid(int outerIndex, int innerIndex) {
        return grid[outerIndex][innerIndex];
    }
}