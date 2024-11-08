import java.util.ArrayList;
class SirtetGrid {
    public boolean[][] grid;
    private char held;
    private int last;
    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    public SirtetGrid() {
        grid = new boolean[10][16];
        held = 'S';
        last = -1;
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
            for (int inner = 0; inner < 4; inner++) {
                int x = sonimortet.getPositions()[inner].getX();
                int y = sonimortet.getPositions()[inner].getY();
                grid[x][y] = true;
            }
        }
        int toClear = -1;
        for(int outer = 0; outer < 10; outer++) {
            int count = 0;
            for(int inner = 15; inner >= 0; inner--) {
                if(grid[outer][inner]) count++;
            }
            if(count == 10) {
                
            }
        }
    }
    public void swapHeld() {
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