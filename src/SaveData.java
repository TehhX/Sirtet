import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
class SaveData {
    static HighScore[] highScores = new HighScore[10];
    static int bgmVolume;
    static int sfxVolume;
    static int currentScore;
    public SaveData() {
        for(int i = 0; i < 10; i++) {
            highScores[i] = new HighScore();
        }
        bgmVolume = 5;
        sfxVolume = 5;
        currentScore = 0;
        try {
            inputString();
        } catch(NumberFormatException e) {
            System.out.println("Save file corrupted, inspect or delete it. (Sirtet Data.txt)");
            System.exit(1);
        }
    }
    public static void inputString() {
        String fileString = readFile();
        bgmVolume = Integer.parseInt(fileString.substring(0, 1));
        sfxVolume = Integer.parseInt(fileString.substring(1, 2));
        int previousIndex = 2;
        int indexLarge = 0;
        for(int i = 2; true; i++) {
            if(fileString.charAt(i) == ',') {
                highScores[indexLarge].setScore(Integer.parseInt(fileString.substring(previousIndex, i)));
                indexLarge++;
                previousIndex = i + 1;
            } else if(fileString.charAt(i) == '.') {
                highScores[9].setScore(Integer.parseInt(fileString.substring(previousIndex, i)));
                previousIndex = i + 1;
                break;
            }
        }
        indexLarge = 0;
        for(int i = previousIndex; true; i++) {
            if(fileString.charAt(i) == ',') {
                highScores[indexLarge].setName(fileString.substring(previousIndex, i));
                previousIndex = i + 1;
                indexLarge++;
            } else if(fileString.charAt(i) == '.') {
                highScores[9].setName(fileString.substring(previousIndex, i));
                break;
            }
        }
    }
    public static String readFile() {
        String fileContents = "";
        try {
            FileReader saveFile = new FileReader("Sirtet Data.txt");
            Scanner sc = new Scanner(saveFile);
            while(sc.hasNext()) {
                fileContents += sc.nextLine();
            }
            return fileContents;
        } catch(FileNotFoundException e) {
            return "731000,900,800,700,600,500,400,300,200,100.timmy,wally,billy,joseph,karen,oregon,bishop,india,echo,alpha.";
        }
    }
    public static void writeFile() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.print(bgmVolume);
            writer.print(sfxVolume);
            for(int i = 0; i < 9; i++) {
                writer.print(highScores[i].getScore() + ",");
            }
            writer.print(highScores[9].getScore() + ".");
            for(int i = 0; i < 9; i++) {
                writer.print(highScores[i].getName() + ",");
            }
            writer.print(highScores[9].getName() + ".");
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void insertScore(String currentName) {
        if(currentScore < highScores[9].getScore()) return;
        if(currentScore > highScores[9].getScore() && currentScore < highScores[8].getScore()) {
            highScores[9].setScore(currentScore);
            highScores[9].setName(currentName);
            return;
        }
        int index = -1;
        for(int i = 9; i >= 0; i--) {
            if(currentScore > highScores[i].getScore()) {
                index = i;
            }
        }
        for(int i = 9; i > index; i--) {
            highScores[i].setScore(highScores[i - 1].getScore());
            highScores[i].setName(highScores[i - 1].getName());
        }
        highScores[index].setScore(currentScore);
        highScores[index].setName(currentName);
        writeFile();
    }
}