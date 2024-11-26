class Sonimortet {
    /**
     * This class handles the positions, rotation, and type of given sonimortet of the parent ArrayList in
     * SirtetGrid. It also accepts movement and rotation requests from GameplayScene's input method.
     * Any changes made to a sonimortet or its individual blocks go through this class. When a new sonimortet
     * is called for placement, but its starting positions are blocked by a preexisting sonimortet, it will
     * call the GameOver() method, which handles removing the GameplayScene, SirtetGrid, and all object
     * instances within from memory, and their panels/frames.
     */
    private int type;
    private int rotation;
    private SirtetGrid parentGrid;
    private SonimortetPositions[] positions;
    public Sonimortet(int type, SirtetGrid parentGrid) {
        rotation = 0;
        this.type = type;
        this.parentGrid = parentGrid;
        positions = new SonimortetPositions[4];
        setStartingPositions();
    }
    public void setStartingPositions() {
        int[][] startingPositions = getStartingPositions(type);
        if(!canPlace(startingPositions)) gameOver();
        else parentGrid.restartTimer();
        for(int posIndex = 0; posIndex < 4; posIndex++) {
            positions[posIndex] = new SonimortetPositions(startingPositions[0][posIndex], startingPositions[1][posIndex]);
        }
    }
    public void gameOver() {
        parentGrid.stopTimer();
        SaveData.currentScore = parentGrid.getParentScene().getCurrentPoints();
        SirtetAudio.playAudio("gameOver.wav");
        if(SaveData.currentScore > SaveData.highScores[9].getScore()) SirtetWindow.changeScene(2);
        else SirtetWindow.changeScene(3);
    }
    public boolean canPlace(int[][] positions) {
        for(int positionIndex = 0; positionIndex < 4; positionIndex++) {
            if(parentGrid.getGrid(positions[0][positionIndex], positions[1][positionIndex])) return false;
        }
        return true;
    }
    public static int[][] getStartingPositions(int type) {
        switch(type) {
            case 0:
                return new int[][]{{4, 5, 4, 5}, {0, 0, 1, 1}};
            case 1:
                return new int[][]{{4, 4, 4, 4}, {0, 1, 2, 3}};
            case 2:
                return new int[][]{{4, 5, 5, 6}, {1, 1, 0, 0}};
            case 3:
                return new int[][]{{4, 5, 5, 6}, {0, 0, 1, 1}};
            case 4:
                return new int[][]{{4, 4, 4, 5}, {0, 1, 2, 2}};
            case 5:
                return new int[][]{{5, 5, 5, 4}, {0, 1, 2, 2}};
            case 6:
                return new int[][]{{4, 5, 5, 6}, {0, 0, 1, 0}};
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
    public boolean allCanMove(int xOffset, int yOffset) {
        for(int posIndex = 0; posIndex < positions.length; posIndex++) {
            if(!singleCanMove(posIndex, xOffset, yOffset, false)) return false;
        }
        return true;
    }
    public boolean singleCanMove(int index, int xOffset, int yOffset, boolean invert) {
        parentGrid.updateGrid(false);
        if(invert) {
            xOffset *= -1;
            yOffset *= -1;
        }
        int x = positions[index].getX();
        int y = positions[index].getY();
        if(isEdge(x, xOffset, y, yOffset)) return false;
        if(parentGrid.getGrid(x + xOffset, y + yOffset)) {
            return singleIsSameSonimortet(x, xOffset, y, yOffset);
        }
        return true;
    }
    public boolean isEdge(int x, int xOffset, int y, int yOffset) {
        if(y + yOffset > 15) return true;
        if(x + xOffset < 0 && xOffset < 0) return true;
        if(x + xOffset > 9 && xOffset > 0) return true;
        return false;
    }
    public boolean singleIsSameSonimortet(int thisX, int xOffset, int thisY, int yOffset) {
        for(SonimortetPositions position : positions) {
            if(position.getX() == thisX + xOffset && position.getY() == thisY + yOffset) return true;
        }
        return false;
    }
    public void delete(int index) {
        SonimortetPositions[] currentPositions = positions;
        positions = new SonimortetPositions[positions.length - 1];
        int newIndex = 0;
        for(int posIndex = 0; posIndex < currentPositions.length; posIndex++) {
            if(posIndex != index) {
                positions[newIndex] = currentPositions[posIndex];
                newIndex++;
            }
        }
    }
    public int getHeight() {
        int height = 0;
        while(allCanMove(0, height)) {
            height++;
        }
        return height - 1;
    }
    public void hardDrop() {
        shiftAll(0, getHeight());
        parentGrid.addSonimortet();
        GameplayTimers.decrementTimer();
    }
    public void softDrop() {
        shiftAll(0, 1);
        parentGrid.restartTimer();
    }
    public void shiftAll(int shiftX, int shiftY) {
        if(!allCanMove(shiftX, shiftY)) return;
        for(SonimortetPositions position : positions) {
            position.shiftSingle(shiftX, shiftY, false);
        }
        parentGrid.updateGrid(true);
    }
    public void rotateClock() {
        if(type == 0) return;
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
        parentGrid.updateGrid(true);
    }
    public void rotateCounter() {
        if(type == 0) return;
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
        parentGrid.updateGrid(true);
    }
    public void rotate0(boolean invert) {
        int[] shiftX;
        int[] shiftY;
        switch(type) {
            case 1:
                shiftX = new int[]{0, 1, 2, 3};
                shiftY = new int[]{0, -1, -2, -3};
                break;
            case 2:
                shiftX = new int[]{0, 0, -1, -1};
                shiftY = new int[]{0, 0, 0, 2};
                break;
            case 3:
                shiftX = new int[]{0, 0, 0, -2};
                shiftY = new int[]{1, 0, 0, 1};
                break;
            case 4:
                shiftX = new int[]{0, 0, 1, 1};
                shiftY = new int[]{0, 0, -2, -2};
                break;
            case 5:
                shiftX = new int[]{-1, 0, 1, 0};
                shiftY = new int[]{0, 0, -1, -1};
                break;
            default:
                shiftX = new int[]{0, 0, 0, -1};
                shiftY = new int[]{1, 0, 0, 2};
        }
        executeRotate(shiftX, shiftY, invert);
    }
    public void rotate1(boolean invert) {
        int[] shiftX;
        int[] shiftY;
        switch(type) {
            case 1:
                shiftX = new int[]{0, -1, -2, -3};
                shiftY = new int[]{0, 1, 2, 3};
                break;
            case 2:
                shiftX = new int[]{0, 0, 1, 1};
                shiftY = new int[]{0, 0, 0, -2};
                break;
            case 3:
                shiftX = new int[]{0, 0, 0, 2};
                shiftY = new int[]{-1, 0, 0, -1};
                break;
            case 4:
                shiftX = new int[]{0, 1, 0, -1};
                shiftY = new int[]{0, 1, 0, 1};
                break;
            case 5:
                shiftX = new int[]{0, 0, -2, 0};
                shiftY = new int[]{0, -1, 1, 0};
                break;
            default:
                shiftX = new int[]{0, 0, 0, 1};
                shiftY = new int[]{0, 0, 0, -1};
        }
        executeRotate(shiftX, shiftY, invert);
    }
    public void rotate2(boolean invert) {
        int[] shiftX;
        int[] shiftY;
        switch(type) {
            case 1:
            case 2:
            case 3:
                rotate0(invert);
                return;
            case 4:
                shiftX = new int[]{0, 1, 1, 0};
                shiftY = new int[]{1, -1, 0, 0};
                break;
            case 5:
                shiftX = new int[]{0, 0, 2, 2};
                shiftY = new int[]{0, 0, -1, -1};
                break;
            default:
                shiftX = new int[]{0, -1, 0, -2};
                shiftY = new int[]{0, 0, 0, 1};
        }
        executeRotate(shiftX, shiftY, invert);
    }
    public void rotate3(boolean invert) {
        int[] shiftX;
        int[] shiftY;
        switch(type) {
            case 1:
            case 2:
            case 3:
                rotate1(invert);
                return;
            case 4:
                shiftX = new int[]{0, -2, -2, 0};
                shiftY = new int[]{-1, 0, 2, 1};
                break;
            case 5:
                shiftX = new int[]{1, 0, -1, -2};
                shiftY = new int[]{0, 1, 1, 2};
                break;
            default:
                shiftX = new int[]{0, 1, 0, 2};
                shiftY = new int[]{-1, 0, 0, -2};
        }
        executeRotate(shiftX, shiftY, invert);
    }
    public void executeRotate(int[] shiftX, int[] shiftY, boolean invert) {
        if(!canRotate(shiftX, shiftY, invert)) return;
        for(int posIndex = 0; posIndex < 4; posIndex++) {
            positions[posIndex].shiftSingle(shiftX[posIndex], shiftY[posIndex], invert);
        }
        if(rotation == (invert ? 0 : 3)) rotation = (invert ? 3 : 0);
        else rotation += (invert ? -1 : 1);
    }
    public boolean canRotate(int[] shiftX, int[] shiftY, boolean invert) {
        for(int posIndex = 0; posIndex < 4; posIndex++) {
            if(!singleCanMove(posIndex, shiftX[posIndex], shiftY[posIndex], invert)) {
                if(allCanMove(-1, 0)) {
                    shiftAll(-1, 0);
                    executeRotate(shiftX, shiftY, invert);
                } return false;
            }
        }
        return true;
    }
    public int getType() {
        return type;
    }
    public SonimortetPositions[] getPositions() {
        return positions;
    }
}