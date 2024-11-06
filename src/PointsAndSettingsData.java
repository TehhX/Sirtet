import java.io.*;
import java.util.Scanner;
public class PointsAndSettingsData {
    private int currentPoints;
    private String currentName;
    private int[] highscorePoints;
    private String[] highscoreNames;
    public PointsAndSettingsData() throws Exception {
        currentPoints = 0;
        currentName = null;
        highscorePoints = new int[10];
        highscoreNames = new String[10];
        readFile();
    }
    public void readFile() {
        try {
            FileReader saveFile = new FileReader("sirtet data.txt");
            Scanner sc = new Scanner(saveFile);
            String fileContents = "";
            while(sc.hasNext()) {
                fileContents += sc.nextLine();
            }
            System.out.println(fileContents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeFile() {
        
    }
}