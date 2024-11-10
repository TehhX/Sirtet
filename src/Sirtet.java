public class Sirtet {
    public static void main(String[]args) {
        SirtetWindow window = new SirtetWindow();
        new SaveData();
        // Temporary main-menu bypass into GameplayScene until MenuScene is implemented
        window.changeScene(1);
    }
}