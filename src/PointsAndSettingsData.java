import java.io.*;
import java.util.*;
public class PointsAndSettingsData {
    private int currentPoints;
    private String currentName;
    private int[] highscorePoints = new int[10];
    private String[] highscoreNames = new String[10];
    private int volume;
    private int controlScheme;
    public PointsAndSettingsData() throws Exception {
        currentPoints = 0;
        currentName = null;
        controlScheme = 0;
        volume = 5;
        readFile();
    }
    public void readFile() {
        String fileContents = "";
        try {
            FileReader saveFile = new FileReader("Sirtet Data.txt");
            Scanner sc = new Scanner(saveFile);
            while(sc.hasNext()) {
                fileContents += sc.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        controlScheme = Integer.parseInt(fileContents.substring(0, 1));
        volume = Integer.parseInt(fileContents.substring(1, 2));
        int previousIndex = 2;
        int indexLarge = 0;
        for(int i = 2; true; i++) {
            if(fileContents.charAt(i) == ',') {
                highscorePoints[indexLarge] = Integer.valueOf(fileContents.substring(previousIndex, i));
                indexLarge++;
                previousIndex = i + 1;
            } else if(fileContents.charAt(i) == '.') {
                highscorePoints[9] = Integer.valueOf(fileContents.substring(previousIndex, i));
                previousIndex = i + 1;
                break;
            }
        }
        indexLarge = 0;
        for(int i = previousIndex; true; i++) {
            if(fileContents.charAt(i) == ',') {
                highscoreNames[indexLarge] = fileContents.substring(previousIndex, i);
                previousIndex = i + 1;
                indexLarge++;
            } else if(fileContents.charAt(i) == '.') {
                highscoreNames[indexLarge] = fileContents.substring(previousIndex, i);
                previousIndex = i + 1;
                break;
            }
        }
        System.out.println("Control Scheme: " + controlScheme);
        System.out.println("Volume: " + volume);
        for(int x : highscorePoints) {
            System.out.print(x + " ");
        }
        System.out.println();
        for(String x : highscoreNames) {
            System.out.print(x + " ");
        }
    }
    public void writeFile() {
        try {
            PrintWriter writer = new PrintWriter("Sirtet Data.txt");
            writer.print(controlScheme + ",");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}