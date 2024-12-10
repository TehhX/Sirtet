/**
 * This class handles the individual positions of blocks within a Sonimortet. It can move them, and get/set
 * the x and y variables of a block as well.
 */
class SonimortetPositions {
    private int x;
    private int y;

    public SonimortetPositions(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void shiftSingle(int x, int y, boolean invert) {
        if (invert) {
            this.x -= x;
            this.y -= y;
        } else {
            this.x += x;
            this.y += y;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}