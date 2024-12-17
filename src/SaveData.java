import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class handles all high scores, and their associated names along with volume settings. It reads and
 * writes to and from the text file named Sirtet Data.txt. If Sirtet Data.txt does not exist or is corrupted,
 * a copy will be made with pre-made data. It can also insert new scores if the new score is above the tenth-best high score.
 */
class SaveData {
    static HighScore[] highScores = new HighScore[10];
    static int bgmVolume;
    static int sfxVolume;
    static int currentScore;

    public SaveData() {
        currentScore = 0;
        readFile();
    }

    public static void readFile() {
        try {
            Scanner fileScanner = new Scanner(new FileReader("Sirtet Data.txt"));
            bgmVolume = Integer.parseInt(fileScanner.nextLine());
            sfxVolume = Integer.parseInt(fileScanner.nextLine());
            for (int index = 0; index < 10; index++) {
                highScores[index] = new HighScore(fileScanner.nextLine(), Integer.parseInt(fileScanner.nextLine()));
            }
        } catch (Exception e) {
            repairSave();
            readFile();
        }
    }

    public static void writeFile() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.println(bgmVolume + "\n" + sfxVolume);
            for (HighScore highScore : highScores) writer.println(highScore);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void repairSave() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.print("3\n3\nJex\n45000\nPajitnov\n24700\nFreeman\n22500\nKitsuragi\n21025\nBlaidd\n19990\nRiebeck\n16570\nMundy\n14000\nWinston\n11050\nNito\n8500\nFring\n4350");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertScore(String currentName) {
        currentScore -= 25;
        if (currentScore < highScores[9].getScore()) return;
        int newIndex = 0;
        for (int index = 0; index < 10; index++) {
            if (currentScore > highScores[index].getScore()) {
                newIndex = index;
                break;
            }
        }
        for (int index = 9; index > newIndex; index--) highScores[index] = highScores[index - 1];
        highScores[newIndex] = new HighScore(currentName, currentScore);
        writeFile();
    }
}