import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class handles all high scores, and their associated names along with volume settings. It reads and
 * writes to and from the text file named Sirtet Data.txt. If Sirtet Data.txt does not exist or is corrupted,
 * a copy will be made with pre-made data. It can also insert new scores if the new score is above the
 * tenth-best high score. */
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

            for (int i = 0; i < 10; i++)
                highScores[i] = new HighScore(fileScanner.nextLine(), Integer.parseInt(fileScanner.nextLine()));

        /// If file does not exist or is corrupted, repair and retry
        } catch (FileNotFoundException | NumberFormatException e) {
            repairSave();
            readFile();
        }
    }

    /// Writes file given scores and names in global arrays
    public static void writeFile() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.println(bgmVolume + "\n" + sfxVolume);

            for (HighScore highScore : highScores)
                writer.println(highScore);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// Creates/resets save file to shown values
    public static void repairSave() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.print("3\n3\nJex\n45000\nPajitnov\n24700\nKazuma\n19000\nKitsuragi\n15000\nGenichiro\n12000\nRiebeck\n10000\nMundy\n8500\nWinston\n4200\nBlaidd\n3000\nFring\n1250");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// Inserts score at correct position, moves all names below down by one, starts a new thread to write the file
    public static void insertScore(String currentName) {
        if (currentScore < highScores[9].getScore())
            return;

        int newIndex = 0;
        for (int i = 0; i < 10; i++)
            if (currentScore > highScores[i].getScore()) {
                newIndex = i;
                break;
            }

        for (int i = 9; i > newIndex; i--)
            highScores[i] = highScores[i - 1];

        highScores[newIndex] = new HighScore(currentName, currentScore);

        new Thread(SaveData::writeFile).start();
    }
}