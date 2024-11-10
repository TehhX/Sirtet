import java.io.*;
import java.util.*;
class SaveData {
    private static String currentName = null;
    private final static HighScore[] highScores = new HighScore[10];
    private static int volume;
    public SaveData() {
        for(int i = 0; i < 10; i++) {
            highScores[i] = new HighScore();
        }
        volume = 5;
        inputString();
    }
    public void changeName(String nameChange) {
        currentName = nameChange;
    }
    public void inputString() {
        String fileString = readFile();
        volume = Integer.parseInt(fileString.substring(0, 1));
        int previousIndex = 1;
        int indexLarge = 0;
        for(int i = 1; true; i++) {
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
                previousIndex = i + 1;
                break;
            }
        }
    }
    public String readFile() {
        String fileContents = "";
        try {
            FileReader saveFile = new FileReader("Sirtet Data.txt");
            Scanner sc = new Scanner(saveFile);
            while(sc.hasNext()) {
                fileContents += sc.nextLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return fileContents;
    }
    public void writeFile() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.print(volume);
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
}