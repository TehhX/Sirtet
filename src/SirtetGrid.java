import java.util.ArrayList;
class SirtetGrid {
    public boolean[][] grid;
    private char held;
    private int last;
    private GameplayScene parentScene;
    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    public SirtetGrid(GameplayScene parentScene) {
        grid = new boolean[10][16];
        held = 'S';
        last = -1;
        this.parentScene = parentScene;
    }
    public void addSonimortet(char type) {
        if(type == ' '){
            sonimortetList.add(new Sonimortet(randomChar(), this));
            last++;
            return;
        }
        sonimortetList.add(new Sonimortet(type, this));
    }
    public char randomChar() {
        char type;
        switch((int)Math.round(Math.random() * 6 + 1)) {
            case 1:
                type = 'O';
                break;
            case 2:
                type = 'I';
                break;
            case 3:
                type = 'S';
                break;
            case 4:
                type = 'Z';
                break;
            case 5:
                type = 'L';
                break;
            case 6:
                type = 'J';
                break;
            default:
                type = 'T';
        }
        return type;
    }
    public Sonimortet getLastSonimortet() {
        return sonimortetList.get(last);
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
        for(int outer = 15; outer >= 0; outer--) {
            int count = 0;
            for(int inner = 0; inner < 10; inner++) {
                if(grid[inner][outer]) count++;
            }
            if(count == 10) clearRow(outer);
        }
        parentScene.repaint();
    }
    public void clearRow(int y) {
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
                    sonimortetList.get(outer).shiftAll(0, 1, false);
                }
            }
        }
        updateGrid();
    }
    public void swapHeld() {
        int currentHigh = 15;
        for(SonimortetPositions position : getLastSonimortet().getPositions()) {
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