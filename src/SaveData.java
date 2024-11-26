import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
class SaveData {
    /**
     * This class handles all high scores, and their associated names, along with volume settings. It reads and
     * writes to and from the text file named Sirtet Data.txt. If Sirtet Data.txt does not exist on read,
     * a copy will be made with pre-made data. However, if Sirtet Data.txt does exist and is corrupted,
     * it will fail to load, and close the program until the user fixes or deletes the file.
     */
    static HighScore[] highScores = new HighScore[10];
    static int bgmVolume;
    static int sfxVolume;
    static int currentScore;
    public SaveData() {
        for(int highScoreIndex = 0; highScoreIndex < 10; highScoreIndex++) {
            highScores[highScoreIndex] = new HighScore();
        }
        bgmVolume = 5;
        sfxVolume = 5;
        currentScore = 0;
        readFile();
    }
    public static void readFile() {
        String fileString;
        try {
            FileReader saveFile = new FileReader("Sirtet Data.txt");
            fileString = new Scanner(saveFile).nextLine();
        } catch(FileNotFoundException e) {
            fileString = "551000,900,800,700,600,500,400,300,200,100.a,b,c,d,e,f,g,h,i,j.";
        }
        try {
            bgmVolume = Integer.parseInt(fileString.substring(0, 1));
            sfxVolume = Integer.parseInt(fileString.substring(1, 2));
            int previousIndex = setScores(fileString, 2);
            setNames(fileString, previousIndex);
        } catch(NumberFormatException nfe) {
            System.out.println("Inspect or Delete Sirtet Data.txt, file is corrupted.");
            System.exit(1);
        }
    }
    public static int setScores(String fileString, int previousIndex) {
        int scoresIndex = 0;
        int stringIndex = 2;
        while(fileString.charAt(stringIndex) != '.') {
            if(fileString.charAt(stringIndex) == ',') {
                highScores[scoresIndex].setScore(Integer.parseInt(fileString.substring(previousIndex, stringIndex)));
                scoresIndex++;
                previousIndex = stringIndex + 1;
            }
            stringIndex++;
        }
        highScores[9].setScore(Integer.parseInt(fileString.substring(previousIndex, stringIndex)));
        return stringIndex + 1;
    }
    public static void setNames(String fileString, int previousIndex) {
        int namesIndex = 0;
        int stringIndex = previousIndex;
        while(fileString.charAt(stringIndex) != '.') {
            if(fileString.charAt(stringIndex) == ',') {
                highScores[namesIndex].setName(fileString.substring(previousIndex, stringIndex));
                previousIndex = stringIndex + 1;
                namesIndex++;
            }
            stringIndex++;
        }
        highScores[9].setName(fileString.substring(previousIndex, stringIndex));
    }
    public static void writeFile() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.print(bgmVolume);
            writer.print(sfxVolume);
            for(int scoreIndex = 0; scoreIndex < 9; scoreIndex++) {
                writer.print(highScores[scoreIndex].getScore() + ",");
            }
            writer.print(highScores[9].getScore() + ".");
            for(int nameIndex = 0; nameIndex < 9; nameIndex++) {
                writer.print(highScores[nameIndex].getName() + ",");
            }
            writer.print(highScores[9].getName() + ".");
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void insertScore(String currentName) {
        int scoreIndex = 0;
        for(int listIndex = 0; listIndex < 10; listIndex++) {
            if(currentScore > highScores[listIndex].getScore()) {
                scoreIndex = listIndex;
                break;
            }
        }
        for(int listIndex = 9; listIndex > scoreIndex; listIndex--) {
            highScores[listIndex].setScore(highScores[listIndex - 1].getScore());
            highScores[listIndex].setName(highScores[listIndex - 1].getName());
        }
        highScores[scoreIndex].setScore(currentScore);
        highScores[scoreIndex].setName(currentName);
    }
}