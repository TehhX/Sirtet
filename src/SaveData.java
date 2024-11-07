import java.io.*;
import java.util.*;
class SaveData {
    private int currentPoints;
    private String currentName;
    private HighScores[] highScores = new HighScores[10];
    private int volume;
    private int controlScheme;
    public SaveData() {
        currentPoints = 0;
        currentName = null;
        for(int i = 0; i < 10; i++) {
            highScores[i] = new HighScores();
        }
        controlScheme = 0;
        volume = 5;
        interperetString();
    }
    public void incrementPoints(int increment) {
        currentPoints += increment;
    }
    public void changeName(String nameChange) {
        currentName = nameChange;
    }
    public void interperetString() {
        String fileString = fileToString();
        controlScheme = Integer.parseInt(fileString.substring(0, 1));
        volume = Integer.parseInt(fileString.substring(1, 2));
        int previousIndex = 2;
        int indexLarge = 0;
        for(int i = 2; true; i++) {
            if(fileString.charAt(i) == ',') {
                highScores[indexLarge].setScore(Integer.valueOf(fileString.substring(previousIndex, i)));
                indexLarge++;
                previousIndex = i + 1;
            } else if(fileString.charAt(i) == '.') {
                highScores[9].setScore(Integer.valueOf(fileString.substring(previousIndex, i)));
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
    public String fileToString() {
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
            PrintWriter writer = new PrintWriter("Fella");
            writer.print(controlScheme);
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
            System.out.println("Writing finished");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}