class SonimortetPositions {
    private int x;
    private int y;
    public SonimortetPositions(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void move(int x, int y, boolean invert) {
        if(invert) {
            this.x -= x;
            this.y -= y;
            return;
        }
        this.x += x;
        this.y += y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}