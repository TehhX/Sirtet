class Sonimortet {
    private char type;
    private int rotation;
    private SonimortetPositions[] positions = new SonimortetPositions[4];
    public Sonimortet(char type) {
        rotation = 0;
        this.type = type;
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
                positions[2] = new SonimortetPositions(0, 1);
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
    public void moveDown() {
        for(int i = 0; i < 4; i++) {
            positions[i].move(0, -1);
        }
    }
    public void rotateClockwise() {

    }
    public SonimortetPositions[] getPositions() {
        return positions;
    }
}