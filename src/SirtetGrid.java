import java.util.ArrayList;
class SirtetGrid {
    private boolean[][] grid = new boolean[10][16];
    private char held;
    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    public SirtetGrid() {

    }
    public void addSonimortet() {
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
        sonimortetList.add(new Sonimortet(type));
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
    }
    public void addSonimortet(char type) {
        sonimortetList.add(new Sonimortet(type));
    }
    public boolean getGrid(int outerIndex, int innerIndex) {
        return grid[outerIndex][innerIndex];
    }
}