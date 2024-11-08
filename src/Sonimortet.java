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
            for(int i = 0; i < 4; i++) {
                if(positions[i].getY() == 15) return;
            }
            for(int i = 0; i < 4; i++) {
                positions[i].move(0, 1);
            }
        }
    }
    public void softDrop() {
        for(int i = 0; i < 4; i++) {
            positions[i].move(0, 1);
        }
        for(int i = 0; i < 4; i++) {
            if(positions[i].getY() == 15) {
                parentGrid.addSonimortet();
            }
        }
    }
    public void shiftLeft() {
        for(int i = 0; i < 4; i++) {
            if(positions[i].getX() == 0) return;
        }
        for(int i = 0; i < 4; i++) {
            positions[i].move(-1, 0);
        }
    }
    public void shiftRight() {
        for(int i = 0; i < 4; i++) {
            if(positions[i].getX() == 9) return;
        }
        for(int i = 0; i < 4; i++) {
            positions[i].move(1, 0);
        }
    }
    public void rotateClockwise() {
        switch(rotation) {
            case 0:
                rotate0();
                break;
            case 1:
                rotate1();
                break;
            case 2:
                rotate2();
                break;
            default:
                rotate3();
        }
        if(rotation == 3) {
            rotation = 0;
            return;
        }
        rotation++;
    }
    public void rotate0() {
        switch(type) {
            case 'I':
                positions[1].move(1, -1);
                positions[2].move(2, -2);
                positions[3].move(3, -3);
                break;
            case 'S':

                break;
            case 'Z':

                break;
            case 'L':
                positions[2].move(1, -2);
                positions[3].move(1, -2);
                break;
            case 'J':

                break;
            case 'T':

        }
    }
    public void rotate1() {
        switch(type) {
            case 'I':
                positions[1].move(-1, 1);
                positions[2].move(-2, 2);
                positions[3].move(-3, 3);
                break;
            case 'S':

                break;
            case 'Z':

                break;
            case 'L':
                positions[1].move(1, 1);
                positions[3].move(-1, 1);
                break;
            case 'J':

                break;
            case 'T':

        }
    }
    public void rotate2() {
        switch(type) {
            case 'I':
                rotate0();
                break;
            case 'S':

                break;
            case 'Z':

                break;
            case 'L':
                positions[0].move(0, 1);
                positions[1].move(1, -1);
                positions[2].move(1, 0);
                break;
            case 'J':

                break;
            case 'T':

        }
    }
    public void rotate3() {
        switch(type) {
            case 'I':
                rotate1();
                break;
            case 'S':

                break;
            case 'Z':

                break;
            case 'L':
                positions[0].move(0, -1);
                positions[1].move(-2, 0);
                positions[2].move(-2, 2);
                positions[3].move(0, 1);
                break;
            case 'J':

                break;
            case 'T':

        }
    }
    public int getRotation() {
        return rotation;
    }
    public char getType() {
        return type;
    }
    public SonimortetPositions[] getPositions() {
        return positions;
    }
}