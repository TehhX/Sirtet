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
        for(int i = 0; i < positions.length; i++) {
            if(checkSurrounding(i, xOffset, yOffset, false)) {
                return true;
            }
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
        if(y + yOffset > 15 && yOffset > 0) return true;
        if(x + xOffset < 0 && xOffset < 0) return true;
        if(x + xOffset > 9 && xOffset > 0) return true;
        if(parentGrid.getGrid(x + xOffset, y + yOffset)) {
            boolean isOtherSonimortet = true;
            for (SonimortetPositions position : positions) {
                if (position.getX() == x + xOffset && position.getY() == y + yOffset) {
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
    }
    public void hardDrop() {
        while(!checkSurrounding(0, 1)) {
            for(SonimortetPositions positions : positions) {
                positions.shiftSingle(0, 1, false);
            }
        }
        parentGrid.addSonimortet(' ');
    }
    public void softDrop() {
        if(checkSurrounding(0, 1)) return;
        shiftAll(0, 1, false);
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
    }
    public void updateRotation(boolean counterClockwise) {
        if(counterClockwise) {
            if(rotation == 0) {
                rotation = 3;
                System.out.println(rotation);
                return;
            }
            rotation--;
        } else {
            if(rotation == 3) {
                rotation = 0;
                System.out.println(rotation);
                return;
            }
            rotation++;
        }
        System.out.println(rotation);
    }
    public void rotate0(boolean invert) {
        int[] xOffset = new int[]{};
        int[] yOffset = new int[]{};
        switch(type) {
            case 'I':
                xOffset = new int[]{0, 1, 2, 3};;
                yOffset = new int[]{0, -1, -2, -3};
                break;
            case 'S':
                xOffset = new int[]{0, 0, -1, -1};
                yOffset = new int[]{0, 0, 0, 2};
                break;
            case 'Z':
                xOffset = new int[]{0, 0, 0, -2};
                yOffset = new int[]{1, 0, 0, 1};
                break;
            case 'L':
                xOffset = new int[]{0, 0, 1, 1};
                yOffset = new int[]{0, 0, -2, -2};
                break;
            case 'J':
                xOffset = new int[]{-1, 0, 1, 0};
                yOffset = new int[]{0, 0, -1, -1};
                break;
            case 'T':
                xOffset = new int[]{0, 0, 0, -1};
                yOffset = new int[]{1, 0, 0, 2};
        }
        for(int i = 0; i < 4; i++) if(checkSurrounding(i, xOffset[i], yOffset[i], invert)) return;
        for(int i = 0; i < 4; i++) positions[i].shiftSingle(xOffset[i], yOffset[i], invert);
        updateRotation(invert);
    }
    public void rotate1(boolean invert) {
        int[] xOffset = new int[]{};
        int[] yOffset = new int[]{};
        switch(type) {
            case 'I':
                xOffset = new int[]{0, -1, -2, -3};
                yOffset = new int[]{0, 1, 2, 3};
                break;
            case 'S':
                xOffset = new int[]{0, 0, 1, 1};
                yOffset = new int[]{0, 0, 0, -2};
                break;
            case 'Z':
                xOffset = new int[]{0, 0, 0, 2};
                yOffset = new int[]{-1, 0, 0, -1};
                break;
            case 'L':
                xOffset = new int[]{0, 1, 0, -1};
                yOffset = new int[]{0, 1, 0, 1};
                break;
            case 'J':
                xOffset = new int[]{0, 0, -2, 0};
                yOffset = new int[]{0, -1, 1, 0};
                break;
            case 'T':
                xOffset = new int[]{0, 0, 0, 1};
                yOffset = new int[]{0, 0, 0, -1};
        }
        for(int i = 0; i < 4; i++) if(checkSurrounding(i, xOffset[i], yOffset[i], invert)) return;
        for(int i = 0; i < 4; i++) positions[i].shiftSingle(xOffset[i], yOffset[i], invert);
        updateRotation(invert);
    }
    public void rotate2(boolean invert) {
        int[] xOffset = new int[]{};
        int[] yOffset = new int[]{};
        switch(type) {
            case 'I':
            case 'S':
            case 'Z':
                rotate0(invert);
                return;
            case 'L':
                xOffset = new int[]{0, 1, 1, 0};
                yOffset = new int[]{1, -1, 0, 0};
                break;
            case 'J':
                xOffset = new int[]{0, 0, 2, 2};
                yOffset = new int[]{0, 0, -1, -1};
                break;
            case 'T':
                xOffset = new int[]{0, -1, 0, -2};
                yOffset = new int[]{0, 0, 0, 1};
        }
        for(int i = 0; i < 4; i++) if(checkSurrounding(i, xOffset[i], yOffset[i], invert)) return;
        for(int i = 0; i < 4; i++) positions[i].shiftSingle(xOffset[i], yOffset[i], invert);
        updateRotation(invert);
    }
    public void rotate3(boolean invert) {
        int[] xOffset = new int[]{};
        int[] yOffset = new int[]{};
        switch(type) {
            case 'I':
            case 'S':
            case 'Z':
                rotate1(invert);
                return;
            case 'L':
                xOffset = new int[]{0, -2, -2, 0};
                yOffset = new int[]{-1, 0, 2, 1};
                break;
            case 'J':
                xOffset = new int[]{1, 0, -1, -2};
                yOffset = new int[]{0, 1, 1, 2};
                break;
            case 'T':
                xOffset = new int[]{0, 1, 0, 2};
                yOffset = new int[]{-1, 0, 0, -2};
        }
        for(int i = 0; i < 4; i++) if(checkSurrounding(i, xOffset[i], yOffset[i], invert)) return;
        for(int i = 0; i < 4; i++) positions[i].shiftSingle(xOffset[i], yOffset[i], invert);
        updateRotation(invert);
    }
    public char getType() {
        return type;
    }
    public SonimortetPositions[] getPositions() {
        return positions;
    }
}