class Sonimortet {
    private char type;
    private int rotation;
    private SirtetGrid parentGrid;
    private SonimortetPositions[] positions = new SonimortetPositions[4];
    public Sonimortet(char type, SirtetGrid parentGrid) {
        rotation = 0;
        this.type = type;
        this.parentGrid = parentGrid;
        setStartingPositions();
    }
    public void setStartingPositions() {
        switch(type) {
            case 'O':
                positions[0] = new SonimortetPositions(0, 0);
                positions[1] = new SonimortetPositions(1, 0);
                positions[2] = new SonimortetPositions(0, 1);
                positions[3] = new SonimortetPositions(1, 1);
                break;
            case 'I':
                positions[0] = new SonimortetPositions(0, 0);
                positions[1] = new SonimortetPositions(0, 1);
                positions[2] = new SonimortetPositions(0, 2);
                positions[3] = new SonimortetPositions(0, 3);
                break;
            case 'S':
                positions[0] = new SonimortetPositions(0, 1);
                positions[1] = new SonimortetPositions(1, 1);
                positions[2] = new SonimortetPositions(1, 0);
                positions[3] = new SonimortetPositions(2, 0);
                break;
            case 'Z':
                positions[0] = new SonimortetPositions(0, 0);
                positions[1] = new SonimortetPositions(1, 0);
                positions[2] = new SonimortetPositions(1, 1);
                positions[3] = new SonimortetPositions(2, 1);
                break;
            case 'L':
                positions[0] = new SonimortetPositions(0, 0);
                positions[1] = new SonimortetPositions(0, 1);
                positions[2] = new SonimortetPositions(0, 2);
                positions[3] = new SonimortetPositions(1, 2);
                break;
            case 'J':
                positions[0] = new SonimortetPositions(1, 0);
                positions[1] = new SonimortetPositions(1, 1);
                positions[2] = new SonimortetPositions(1, 2);
                positions[3] = new SonimortetPositions(0, 2);
                break;
            case 'T':
                positions[0] = new SonimortetPositions(0, 0);
                positions[1] = new SonimortetPositions(1, 0);
                positions[2] = new SonimortetPositions(1, 1);
                positions[3] = new SonimortetPositions(2, 0);
                break;
        }
    }
    public void hardDrop() {
        while(true) {
            boolean toBreak = false;
            for(int i = 0; i < 4; i++) {
                if(positions[i].getY() == 14) toBreak = true;
            }
            for(int i = 0; i < 4; i++) {
                positions[i].move(0, 1, false);
            }
            if(toBreak) break;
        }
        parentGrid.addSonimortet(' ');
    }
    public void softDrop() {
        for(int i = 0; i < 4; i++) {
            positions[i].move(0, 1, false);
        }
        for(int i = 0; i < 4; i++) {
            if(positions[i].getY() == 15) {
                parentGrid.addSonimortet(' ');
                break;
            }
        }
    }
    public void shiftLeft() {
        for(int i = 0; i < 4; i++) {
            if(positions[i].getX() == 0) return;
        }
        for(int i = 0; i < 4; i++) {
            positions[i].move(-1, 0, false);
        }
    }
    public void shiftRight() {
        for(int i = 0; i < 4; i++) {
            if(positions[i].getX() == 9) return;
        }
        for(int i = 0; i < 4; i++) {
            positions[i].move(1, 0, false);
        }
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
    public void rotateCounterwise() {
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