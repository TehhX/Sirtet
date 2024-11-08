import java.util.ArrayList;
class SirtetGrid {
    public boolean[][] grid;
    private char held;
    private int last;
    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    public SirtetGrid() {
        grid = new boolean[10][16];
        held = ' ';
        last = -1;
    }
    public void addSonimortet() {
        last++;
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
        sonimortetList.add(new Sonimortet('I', this));
    }
    public Sonimortet getLastSonimortet() {
        return sonimortetList.get(last);
    }
    public void updateGrid() {
        grid = new boolean[10][16];
        for(int outer = 0; outer < sonimortetList.size(); outer++) {
            for(int inner = 0; inner < 4; inner++) {
                int x = sonimortetList.get(outer).getPositions()[inner].getX();
                int y = sonimortetList.get(outer).getPositions()[inner].getY();
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
    public char getHeld() {
        return held;
    }
    public void addSonimortet(char type) {
        sonimortetList.add(new Sonimortet(type, this));
    }
    public boolean getGrid(int outerIndex, int innerIndex) {
        return grid[outerIndex][innerIndex];
    }
}