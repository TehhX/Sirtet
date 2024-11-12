class Sonimortet {
    private final char type;
    private int rotation;
    private final SirtetGrid parentGrid;
    private SonimortetPositions[] positions;
    public Sonimortet(char type, SirtetGrid parentGrid) {
        rotation = 0;
        this.type = type;
        this.parentGrid = parentGrid;
        positions = new SonimortetPositions[4];
        setStartingPositions();
    }
    public void setStartingPositions() {
        int[][] startingPositions = getStartingPositions(type);
        parentGrid.updateGrid();
        for(int i = 0; i < 4; i++) {
            if(parentGrid.getGrid(startingPositions[0][i], startingPositions[1][i])) {
                parentGrid.getParentScene().getFrame().changeScene(2);
            }
        }
        for(int i = 0; i < 4; i++) {
            positions[i] = new SonimortetPositions(startingPositions[0][i], startingPositions[1][i]);
        }
    }
    public static int[][] getStartingPositions(char type) {
        switch(type) {
            case 'O':
                return new int[][]{{4, 5, 4, 5}, {0, 0, 1, 1}};
            case 'I':
                return new int[][]{{4, 4, 4, 4}, {0, 1, 2, 3}};
            case 'S':
                return new int[][]{{4, 5, 5, 6}, {1, 1, 0, 0}};
            case 'Z':
                return new int[][]{{4, 5, 5, 6}, {0, 0, 1, 1}};
            case 'L':
                return new int[][]{{4, 4, 4, 5}, {0, 1, 2, 2}};
            case 'J':
                return new int[][]{{5, 5, 5, 4}, {0, 1, 2, 2}};
        }
        return new int[][]{{4, 5, 5, 6}, {0, 0, 1, 0}};
    }
    public boolean checkSurrounding(int xOffset, int yOffset) {
        for(int i = 0; i < positions.length; i++) {
            if(checkSurrounding(i, xOffset, yOffset, false)) return true;
        }
        return false;
    }
    public boolean checkSurrounding(int index, int xOffset, int yOffset, boolean invert) {
        if(invert) {
            xOffset *= -1;
            yOffset *= -1;
        }
        int x = positions[index].getX();
        int y = positions[index].getY();
        if(y + yOffset > 15) return true;
        if(x + xOffset < 0 && xOffset < 0) return true;
        if(x + xOffset > 9 && xOffset > 0) return true;
        if(parentGrid.getGrid(x + xOffset, y + yOffset)) {
            boolean isOtherSonimortet = true;
            for(SonimortetPositions position : positions) {
                if(position.getX() == x + xOffset && position.getY() == y + yOffset) {
                    isOtherSonimortet = false;
                    break;
                }
            }
            return isOtherSonimortet;
        }
        return false;
    }
    public void delete(int index) {
        SonimortetPositions[] currentPositions = positions;
        positions = new SonimortetPositions[positions.length - 1];
        int newIndex = 0;
        for(int i = 0; i < currentPositions.length; i++) {
            if(i != index) {
                positions[newIndex] = currentPositions[i];
                newIndex++;
            }
        }
        // if(positions.length == 0) If the whole sonimortet is deleted.
    }
    public void hardDrop() {
        while(!checkSurrounding(0, 1)) {
            for(SonimortetPositions positions : positions) {
                positions.shiftSingle(0, 1, false);
            }
        }
        parentGrid.addSonimortet();
        new GameplayTimers().decrementTimer();
    }
    public void softDrop() {
        if(checkSurrounding(0, 1)) return;
        shiftAll(0, 1, false);
        parentGrid.restartTimer();
    }
    public void shiftLeft() {
        if(checkSurrounding(-1, 0)) return;
        shiftAll(-1, 0, false);
    }
    public void shiftRight() {
        if(checkSurrounding(1, 0)) return;
        shiftAll(1, 0, false);
    }
    public void shiftAll(int x, int y, boolean invert) {
        for(SonimortetPositions position : positions) {
            position.shiftSingle(x, y, invert);
        }
        parentGrid.updateGrid();
    }
    public void rotateClock() {
        if(type == 'O') return;
        switch(rotation) {
            case 0:
                rotate0(false);
                break;
            case 1:
                rotate1(false);
                break;
            case 2:
                rotate2(false);
                break;
            default:
                rotate3(false);
        }
        parentGrid.updateGrid();
    }
    public void rotateCounter() {
        if(type == 'O') return;
        switch(rotation) {
            case 0:
                rotate3(true);
                break;
            case 1:
                rotate0(true);
                break;
            case 2:
                rotate1(true);
                break;
            default:
                rotate2(true);
        }
        parentGrid.updateGrid();
    }
    public void rotate0(boolean invert) {
        int[] x;
        int[] y;
        switch(type) {
            case 'I':
                x = new int[]{0, 1, 2, 3};
                y = new int[]{0, -1, -2, -3};
                break;
            case 'S':
                x = new int[]{0, 0, -1, -1};
                y = new int[]{0, 0, 0, 2};
                break;
            case 'Z':
                x = new int[]{0, 0, 0, -2};
                y = new int[]{1, 0, 0, 1};
                break;
            case 'L':
                x = new int[]{0, 0, 1, 1};
                y = new int[]{0, 0, -2, -2};
                break;
            case 'J':
                x = new int[]{-1, 0, 1, 0};
                y = new int[]{0, 0, -1, -1};
                break;
            default:
                x = new int[]{0, 0, 0, -1};
                y = new int[]{1, 0, 0, 2};
        }
        executeRotate(x, y, invert);
    }
    public void rotate1(boolean invert) {
        int[] x;
        int[] y;
        switch(type) {
            case 'I':
                x = new int[]{0, -1, -2, -3};
                y = new int[]{0, 1, 2, 3};
                break;
            case 'S':
                x = new int[]{0, 0, 1, 1};
                y = new int[]{0, 0, 0, -2};
                break;
            case 'Z':
                x = new int[]{0, 0, 0, 2};
                y = new int[]{-1, 0, 0, -1};
                break;
            case 'L':
                x = new int[]{0, 1, 0, -1};
                y = new int[]{0, 1, 0, 1};
                break;
            case 'J':
                x = new int[]{0, 0, -2, 0};
                y = new int[]{0, -1, 1, 0};
                break;
            default:
                x = new int[]{0, 0, 0, 1};
                y = new int[]{0, 0, 0, -1};
        }
        executeRotate(x, y, invert);
    }
    public void rotate2(boolean invert) {
        int[] x;
        int[] y;
        switch(type) {
            case 'I':
            case 'S':
            case 'Z':
                rotate0(invert);
                return;
            case 'L':
                x = new int[]{0, 1, 1, 0};
                y = new int[]{1, -1, 0, 0};
                break;
            case 'J':
                x = new int[]{0, 0, 2, 2};
                y = new int[]{0, 0, -1, -1};
                break;
            default:
                x = new int[]{0, -1, 0, -2};
                y = new int[]{0, 0, 0, 1};
        }
        executeRotate(x, y, invert);
    }
    public void rotate3(boolean invert) {
        int[] x;
        int[] y;
        switch(type) {
            case 'I':
            case 'S':
            case 'Z':
                rotate1(invert);
                return;
            case 'L':
                x = new int[]{0, -2, -2, 0};
                y = new int[]{-1, 0, 2, 1};
                break;
            case 'J':
                x = new int[]{1, 0, -1, -2};
                y = new int[]{0, 1, 1, 2};
                break;
            default:
                x = new int[]{0, 1, 0, 2};
                y = new int[]{-1, 0, 0, -2};
        }
        executeRotate(x, y, invert);
    }
    public void executeRotate(int[] x, int[] y, boolean invert) {
        for(int i = 0; i < 4; i++) if(checkSurrounding(i, x[i], y[i], invert)) return;
        for(int i = 0; i < 4; i++) positions[i].shiftSingle(x[i], y[i], invert);
        if(rotation == (invert ? 0 : 3)) rotation = (invert ? 3 : 0);
        else rotation += (invert ? -1 : 1);
    }
    public char getType() {
        return type;
    }
    public SonimortetPositions[] getPositions() {
        return positions;
    }
}