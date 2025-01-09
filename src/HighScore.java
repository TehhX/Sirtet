/**
 * Data class containing individual high scores and their associated names, along with getter
 * methods and toString override. The toString override is in the format used by SaveData class. */
class HighScore {
    private String name;
    private int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return name + "\n" + score;
    }
}