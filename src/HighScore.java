/// Contains individual high scores and their associated names, along with getter/setter methods.
class HighScore {
    private int score;
    private String name;

    public HighScore() {
        score = 0;
        name = null;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}