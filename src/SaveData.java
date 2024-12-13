import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class handles all high scores, and their associated names along with volume settings. It reads and
 * writes to and from the text file named Sirtet Data.txt. If Sirtet Data.txt does not exist or is corrupted,
 * a copy will be made with pre-made data.
 */
class SaveData {
    static HighScore[] highScores = new HighScore[10];
    static int bgmVolume;
    static int sfxVolume;
    static int currentScore;

    public SaveData() {
        for (int highScoreIndex = 0; highScoreIndex < 10; highScoreIndex++) {
            highScores[highScoreIndex] = new HighScore();
        }
        currentScore = 0;
        readFile();
    }

    public static void readFile() {
        try {
            FileReader saveFile = new FileReader("Sirtet Data.txt");
            Scanner fileScanner = new Scanner(saveFile);
            bgmVolume = Integer.parseInt(fileScanner.nextLine());
            sfxVolume = Integer.parseInt(fileScanner.nextLine());
            for (int scoreIndex = 0; scoreIndex < 10; scoreIndex++) {
                highScores[scoreIndex].setScore(Integer.parseInt(fileScanner.nextLine()));
            }
            for (int nameIndex = 0; nameIndex < 10; nameIndex++) {
                highScores[nameIndex].setName(fileScanner.nextLine());
            }
        } catch (Exception e) {
            repairSave();
            readFile();
        }
    }

    public static void writeFile() {
        new Thread(() -> {
            try {
                PrintWriter writer = new PrintWriter("Sirtet Data.txt");
                writer.println(bgmVolume);
                writer.println(sfxVolume);
                for (int scoreIndex = 0; scoreIndex < 10; scoreIndex++) {
                    writer.println(highScores[scoreIndex].getScore());
                }
                for (int nameIndex = 0; nameIndex < 10; nameIndex++) {
                    writer.println(highScores[nameIndex].getName());
                }
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void repairSave() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.print("3\n3\n1000\n900\n800\n700\n600\n500\n400\n300\n200\n100\na\nb\nc\nd\ne\nf\ng\nh\ni\nj");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertScore(String currentName) {
        currentScore -= 25;
        if (currentScore < highScores[9].getScore()) return;
        int scoreIndex = 0;
        for (int listIndex = 0; listIndex < 10; listIndex++) {
            if (currentScore > highScores[listIndex].getScore()) {
                scoreIndex = listIndex;
                break;
            }
        }
        for (int listIndex = 9; listIndex > scoreIndex; listIndex--) {
            highScores[listIndex].setScore(highScores[listIndex - 1].getScore());
            highScores[listIndex].setName(highScores[listIndex - 1].getName());
        }
        highScores[scoreIndex].setScore(currentScore);
        highScores[scoreIndex].setName(currentName);
        writeFile();
    }
}