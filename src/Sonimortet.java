class Sonimortet {
    private char type;
    private int rotation;
    private SirtetGrid parentGrid;
    private SonimortetPositions[] positions;
    public Sonimortet(char type, SirtetGrid parentGrid) {
        rotation = 0;
        this.type = type;
        this.parentGrid = parentGrid;
        positions = new SonimortetPositions[4];
        setStartingPositions();
    }
    public void setStartingPositions() {
        switch(type) {
            case 'O':
                positions[0] = new SonimortetPositions(4, 0);
                positions[1] = new SonimortetPositions(5, 0);
                positions[2] = new SonimortetPositions(4, 1);
                positions[3] = new SonimortetPositions(5, 1);
                break;
            case 'I':
                positions[0] = new SonimortetPositions(4, 0);
                positions[1] = new SonimortetPositions(4, 1);
                positions[2] = new SonimortetPositions(4, 2);
                positions[3] = new SonimortetPositions(4, 3);
                break;
            case 'S':
                positions[0] = new SonimortetPositions(4, 1);
                positions[1] = new SonimortetPositions(5, 1);
                positions[2] = new SonimortetPositions(5, 0);
                positions[3] = new SonimortetPositions(6, 0);
                break;
            case 'Z':
                positions[0] = new SonimortetPositions(4, 0);
                positions[1] = new SonimortetPositions(5, 0);
                positions[2] = new SonimortetPositions(5, 1);
                positions[3] = new SonimortetPositions(6, 1);
                break;
            case 'L':
                positions[0] = new SonimortetPositions(4, 0);
                positions[1] = new SonimortetPositions(4, 1);
                positions[2] = new SonimortetPositions(4, 2);
                positions[3] = new SonimortetPositions(5, 2);
                break;
            case 'J':
                positions[0] = new SonimortetPositions(5, 0);
                positions[1] = new SonimortetPositions(5, 1);
                positions[2] = new SonimortetPositions(5, 2);
                positions[3] = new SonimortetPositions(4, 2);
                break;
            case 'T':
                positions[0] = new SonimortetPositions(4, 0);
                positions[1] = new SonimortetPositions(5, 0);
                positions[2] = new SonimortetPositions(5, 1);
                positions[3] = new SonimortetPositions(6, 0);
                break;
        }
    }
    public boolean checkSurrounding(int xOffset, int yOffset) {
        for(int outer = 0; outer < positions.length; outer++) {
            int x = positions[outer].getX();
            int y = positions[outer].getY();
            if(positions[outer].getY() == 15 && yOffset > 0) return true;
            if(positions[outer].getX() == 0 && xOffset < 0) return true;
            if(positions[outer].getX() == 9 && xOffset > 0) return true;
            if(parentGrid.getGrid(x + xOffset, y + yOffset)) {
                boolean isOtherSonimortet = true;
                for(int inner = 0; inner < positions.length; inner++) {
                    if(positions[inner].getX() == x + xOffset && positions[inner].getY() == y + yOffset) {
                        isOtherSonimortet = false;
                        break;
                    }
                }
                if(isOtherSonimortet) return true;
            }
        }
        return false;
    }
    public void hardDrop() {
        while(!checkSurrounding(0, 1)) {
            for(SonimortetPositions positions : positions) {
                positions.move(0, 1, false);
            }
        }
        parentGrid.addSonimortet(' ');
    }
    public void softDrop() {
        if(checkSurrounding(0, 1)) return;
        shiftAll(0, 1, false);
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
        for(SonimortetPositions position : positions) position.move(x, y, invert);
    }
    public void rotateClockwise() {
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
        if(rotation == 3) {
            rotation = 0;
            return;
        }
        rotation++;
    }
    public void rotateCounterClockwise() {
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
        if(rotation == 0) {
            rotation = 3;
            return;
        }
        rotation--;
    }
    public void rotate0(boolean invert) {
        switch(type) {
            case 'I':
                positions[1].move(1, -1, invert);
                positions[2].move(2, -2, invert);
                positions[3].move(3, -3, invert);
                break;
            case 'S':
                positions[2].move(-1, 0, invert);
                positions[3].move(-1, 2, invert);
                break;
            case 'Z':
                positions[0].move(0, 1, invert);
                positions[3].move(-2, 1, invert);
                break;
            case 'L':
                positions[2].move(1, -2, invert);
                positions[3].move(1, -2, invert);
                break;
            case 'J':
                positions[0].move(-1, 0, invert);
                positions[2].move(1, -1, invert);
                positions[3].move(0, -1, invert);
                break;
            case 'T':
                positions[0].move(0, 1, invert);
                positions[3].move(-1, 2, invert);
        }
    }
    public void rotate1(boolean invert) {
        switch(type) {
            case 'I':
                positions[1].move(-1, 1, invert);
                positions[2].move(-2, 2, invert);
                positions[3].move(-3, 3, invert);
                break;
            case 'S':
                positions[2].move(1, 0, invert);
                positions[3].move(1, -2, invert);
                break;
            case 'Z':
                positions[0].move(0, -1, invert);
                positions[3].move(2, -1, invert);
                break;
            case 'L':
                positions[1].move(1, 1, invert);
                positions[3].move(-1, 1, invert);
                break;
            case 'J':
                positions[1].move(0, -1, invert);
                positions[2].move(-2, 1, invert);
                break;
            case 'T':
                positions[3].move(1, -1, invert);
        }
    }
    public void rotate2(boolean invert) {
        switch(type) {
            case 'I':
            case 'S':
            case 'Z':
                rotate0(invert);
                break;
            case 'L':
                positions[0].move(0, 1, invert);
                positions[1].move(1, -1, invert);
                positions[2].move(1, 0, invert);
                break;
            case 'J':
                positions[2].move(2, -1, invert);
                positions[3].move(2, -1, invert);
                break;
            case 'T':
                positions[1].move(-1, 0, invert);
                positions[3].move(-2, 1, invert);
        }
    }
    public void rotate3(boolean invert) {
        switch(type) {
            case 'I':
            case 'S':
            case 'Z':
                rotate1(invert);
                break;
            case 'L':
                positions[0].move(0, -1, invert);
                positions[1].move(-2, 0, invert);
                positions[2].move(-2, 2, invert);
                positions[3].move(0, 1, invert);
                break;
            case 'J':
                positions[0].move(1, 0, invert);
                positions[1].move(0, 1, invert);
                positions[2].move(-1, 1, invert);
                positions[3].move(-2, 2, invert);
                break;
            case 'T':
                positions[0].move(0, -1, invert);
                positions[1].move(1, 0, invert);
                positions[3].move(2, -2, invert);
        }
    }
    public char getType() {
        return type;
    }
    public SonimortetPositions[] getPositions() {
        return positions;
    }
}