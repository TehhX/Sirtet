import java.util.ArrayList;
class SirtetGrid {
    public static boolean[][] grid;
    private char held;
    private int last;
    private ArrayList<Sonimortet> sonimortetList = new ArrayList<>();
    public SirtetGrid() {
        grid = new boolean[10][16];
        held = 'O';
        last = -1;
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
        last++;
    }
    public Sonimortet getLastSoni() {
        return sonimortetList.get(getLast());
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
    public int getLast() {
        return last;
    }
    public void hardDrop() {
        while(true) {
            for(int i = 0; i < 4; i++) {
                if(sonimortetList.get(getLast()).getPositions()[i].getY() == 15) return;
            }
            for(int i = 0; i < 4; i++) {
                sonimortetList.get(getLast()).getPositions()[i].move(0, 1);
            }
        }
    }
    public void softDrop() {
        for(int i = 0; i < 4; i++) {
            sonimortetList.get(getLast()).getPositions()[i].move(0, 1);
        }
        for(int i = 0; i < 4; i++) {
            if(sonimortetList.get(getLast()).getPositions()[i].getY() == 15) {
                addSonimortet();
            }
        }
    }
    public void shiftLeft() {
        for(int i = 0; i < 4; i++) {
            if(sonimortetList.get(getLast()).getPositions()[i].getX() == 0) return;
        }
        for(int i = 0; i < 4; i++) {
            sonimortetList.get(getLast()).getPositions()[i].move(-1, 0);
        }
    }
    public void shiftRight() {
        for(int i = 0; i < 4; i++) {
            if(sonimortetList.get(getLast()).getPositions()[i].getX() == 9) return;
        }
        for(int i = 0; i < 4; i++) {
            sonimortetList.get(getLast()).getPositions()[i].move(1, 0);
        }
    }
    public void rotate() {

    }
    public char getHeld() {
        return held;
    }
    public void addSonimortet(char type) {
        sonimortetList.add(new Sonimortet(type));
    }
    public boolean getGrid(int outerIndex, int innerIndex) {
        return grid[outerIndex][innerIndex];
    }
}