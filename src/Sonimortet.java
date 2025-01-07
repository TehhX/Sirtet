/**
 * This class handles the positions, rotation, and type of given sonimortet of the parent ArrayList in
 * SirtetGrid. It also accepts movement and rotation requests from GameplayScene's input method.
 * Any changes made to a sonimortet or its individual blocks go through this class. When a new sonimortet
 * is called for placement, but its starting positions are blocked by a preexisting sonimortet, it will
 * call the GameOver() method, which handles removing the GameplayScene, SirtetGrid, and all object
 * instances within from memory, and their panels/frames. */
class Sonimortet {
    private int rotation;

    private BlockID type;

    private SirtetGrid parentGrid;

    private SonimortetPositions[] positions;

    public Sonimortet(BlockID type, SirtetGrid parentGrid) {
        rotation = 0;
        this.type = type;
        this.parentGrid = parentGrid;
        positions = new SonimortetPositions[4];

        setStartingPositions();
    }

    /// Gets a starting position two-dimensional array given a BlockID
    public static int[][] getStartingPositions(BlockID type) {
        switch (type) {
        case O:
            return new int[][]{{4, 5, 4, 5}, {0, 0, 1, 1}};
        case I:
            return new int[][]{{4, 4, 4, 4}, {0, 1, 2, 3}};
        case S:
            return new int[][]{{4, 5, 5, 6}, {1, 1, 0, 0}};
        case Z:
            return new int[][]{{4, 5, 5, 6}, {0, 0, 1, 1}};
        case L:
            return new int[][]{{4, 4, 4, 5}, {0, 1, 2, 2}};
        case J:
            return new int[][]{{5, 5, 5, 4}, {0, 1, 2, 2}};
        case T:
            return new int[][]{{4, 5, 5, 6}, {0, 0, 1, 0}};
        default:
            throw new IllegalStateException("Unexpected type: " + type);
        }
    }

    public void setStartingPositions() {
        int[][] startingPositions = getStartingPositions(type);

        /// When any of the starting positions are occluded by an existing sonimortet, game over
        if (!canPlace(startingPositions)) {
            gameOver();
            return;
        }

        for (int i = 0; i < 4; i++)
            positions[i] = new SonimortetPositions(startingPositions[0][i], startingPositions[1][i]);

        parentGrid.restartTimer();
    }

    /// Goes to game over etc.
    public void gameOver() {
        parentGrid.stopTimer();
        SirtetAudio.playAudio(AudioID.GameOver);
        SirtetWindow.changeScene(SceneID.GameOver);
    }

    /// Checks to see if sonimortet can be placed at given position array
    public boolean canPlace(int[][] positions) {
        for (int i = 0; i < 4; i++)
            if (parentGrid.getGrid(positions[0][i], positions[1][i]))
                return false;

        return true;
    }

    /// Checks to see if the whole sonimortet can move to an offset
    public boolean allCanMove(int xOffset, int yOffset) {
        for (int i = 0; i < positions.length; i++)
            if (!singleCanMove(i, xOffset, yOffset, false))
                return false;

        return true;
    }

    /// Checks to see if a single piece of this sonimortet can move to an offset
    public boolean singleCanMove(int index, int xOffset, int yOffset, boolean invert) {
        if (invert) {
            xOffset *= -1;
            yOffset *= -1;
        }

        parentGrid.updateGrid(false);

        int x = positions[index].getX();
        int y = positions[index].getY();

        if (isEdge(x, xOffset, y, yOffset))
            return false;

        if (parentGrid.getGrid(x + xOffset, y + yOffset))
            return isSameSonimortet(x, xOffset, y, yOffset);

        return true;
    }

    /// Checks to see if moving to an offset would move the sonimortet off the grid, aka is at the edge
    public boolean isEdge(int x, int xOffset, int y, int yOffset) {
        // If going off bottom
        if (y + yOffset > 15)
            return true;

        // If going off left
        if (x + xOffset < 0 && xOffset < 0)
            return true;

        // If going off right
        return x + xOffset > 9 && xOffset > 0;
    }

    /// Checks to see if an occupied offset is the same piece as this sonimortet
    public boolean isSameSonimortet(int thisX, int xOffset, int thisY, int yOffset) {
        for (SonimortetPositions position : positions)
            if (position.getX() == thisX + xOffset && position.getY() == thisY + yOffset)
                return true;

        return false;
    }

    /// Deletes a part of the sonimortet given the index of the part
    public void delete(int index) {
        SonimortetPositions[] currentPositions = positions;
        positions = new SonimortetPositions[positions.length - 1];
        int newIndex = 0;

        for (int i = 0; i < currentPositions.length; i++)
            if (i != index) {
                positions[newIndex] = currentPositions[i];
                newIndex++;
            }
    }

    /// Gets the height from nearest downwards collision
    public int getHeight() {
        int height = 0;

        while (allCanMove(0, height))
            height++;

        return height - 1;
    }

    /// Drops to bottommost possible position
    public void hardDrop() {
        shiftAll(0, getHeight());

        parentGrid.addSonimortet();
        GameplayTimers.decrementTimer();
    }

    /// Drops piece by one tile
    public void softDrop() {
        shiftAll(0, 1);
        parentGrid.restartTimer();
    }

    /// Moves entire sonimortet by shiftX, shiftY. If collision, do nothing
    public void shiftAll(int shiftX, int shiftY) {
        if (!allCanMove(shiftX, shiftY))
            return;

        for (SonimortetPositions position : positions)
            position.shiftSingle(shiftX, shiftY, false);

        parentGrid.updateGrid(false);
    }

    /// Tries to rotate piece clockwise, if O piece do nothing as it has 90deg. symmetry
    public void rotateClock() {
        if (type == BlockID.O)
            return;

        switch (rotation) {
        case 0:
            rotate0(false);
            break;
        case 1:
            rotate1(false);
            break;
        case 2:
            rotate2(false);
            break;
        case 3:
            rotate3(false);
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + rotation);
        }
    }

    /// Tries to rotate piece counter-clockwise, if O piece do nothing as it has 90deg. symmetry
    public void rotateCounter() {
        if (type == BlockID.O)
            return;

        switch (rotation) {
        case 0:
            rotate3(true);
            break;
        case 1:
            rotate0(true);
            break;
        case 2:
            rotate1(true);
            break;
        case 3:
            rotate2(true);
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + rotation);
        }
    }

    /// Rotate for rotation count 0
    public void rotate0(boolean invert) {
        int[] shiftX;
        int[] shiftY;

        switch (type) {
        case I:
            shiftX = new int[]{0, 1, 2, 3};
            shiftY = new int[]{0, -1, -2, -3};
            break;
        case S:
            shiftX = new int[]{0, 0, -1, -1};
            shiftY = new int[]{0, 0, 0, 2};
            break;
        case Z:
            shiftX = new int[]{0, 0, 0, -2};
            shiftY = new int[]{1, 0, 0, 1};
            break;
        case L:
            shiftX = new int[]{0, 0, 1, 1};
            shiftY = new int[]{0, 0, -2, -2};
            break;
        case J:
            shiftX = new int[]{-1, 0, 1, 0};
            shiftY = new int[]{0, 0, -1, -1};
            break;
        case T:
            shiftX = new int[]{0, 0, 0, -1};
            shiftY = new int[]{1, 0, 0, 2};
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + type);
        }

        executeRotate(shiftX, shiftY, invert);
    }

    /// Rotate for rotation count 1
    public void rotate1(boolean invert) {
        int[] shiftX;
        int[] shiftY;

        switch (type) {
        case I:
            shiftX = new int[]{0, -1, -2, -3};
            shiftY = new int[]{0, 1, 2, 3};
            break;
        case S:
            shiftX = new int[]{0, 0, 1, 1};
            shiftY = new int[]{0, 0, 0, -2};
            break;
        case Z:
            shiftX = new int[]{0, 0, 0, 2};
            shiftY = new int[]{-1, 0, 0, -1};
            break;
        case L:
            shiftX = new int[]{0, 1, 0, -1};
            shiftY = new int[]{0, 1, 0, 1};
            break;
        case J:
            shiftX = new int[]{0, 0, -2, 0};
            shiftY = new int[]{0, -1, 1, 0};
            break;
        case T:
            shiftX = new int[]{0, 0, 0, 1};
            shiftY = new int[]{0, 0, 0, -1};
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + type);
        }

        executeRotate(shiftX, shiftY, invert);
    }

    /// Rotate for rotation count 2
    public void rotate2(boolean invert) {
        int[] shiftX;
        int[] shiftY;

        switch (type) {
        case I:
        case S:
        case Z:
            rotate0(invert);
            return;
        case L:
            shiftX = new int[]{0, 1, 1, 0};
            shiftY = new int[]{1, -1, 0, 0};
            break;
        case J:
            shiftX = new int[]{0, 0, 2, 2};
            shiftY = new int[]{0, 0, -1, -1};
            break;
        case T:
            shiftX = new int[]{0, -1, 0, -2};
            shiftY = new int[]{0, 0, 0, 1};
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + type);
        }

        executeRotate(shiftX, shiftY, invert);
    }

    /// Rotate for rotation count 3
    public void rotate3(boolean invert) {
        int[] shiftX;
        int[] shiftY;

        switch (type) {
        case I:
        case S:
        case Z:
            rotate1(invert);
            return;
        case L:
            shiftX = new int[]{0, -2, -2, 0};
            shiftY = new int[]{-1, 0, 2, 1};
            break;
        case J:
            shiftX = new int[]{1, 0, -1, -2};
            shiftY = new int[]{0, 1, 1, 2};
            break;
        case T:
            shiftX = new int[]{0, 1, 0, 2};
            shiftY = new int[]{-1, 0, 0, -2};
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + type);
        }

        executeRotate(shiftX, shiftY, invert);
    }

    public void executeRotate(int[] shiftX, int[] shiftY, boolean invert) {
        if (!canRotate(shiftX, shiftY, invert))
            return;

        for (int i = 0; i < 4; i++)
            positions[i].shiftSingle(shiftX[i], shiftY[i], invert);

        if (invert)
            rotation = rotation == 0 ? 3 : --rotation;
        else
            rotation = rotation == 3 ? 0 : ++rotation;
    }

    public boolean canRotate(int[] shiftX, int[] shiftY, boolean invert) {
        for (int i = 0; i < 4; i++) {
            if (!singleCanMove(i, shiftX[i], shiftY[i], invert)) {
                if (allCanMove(-1, 0)) {
                    shiftAll(-1, 0);
                    executeRotate(shiftX, shiftY, invert);
                }

                return false;
            }
        }

        return true;
    }

    public BlockID getType() {
        return type;
    }

    public SonimortetPositions[] getPositions() {
        return positions;
    }
}