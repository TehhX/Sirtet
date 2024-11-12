import javax.swing.*;
class SirtetWindow {
    private JFrame frame;
    private final int sizeX;
    private final int sizeY;
    private GameplayScene gameplayScene;
    private MenuScene menuScene;
    private HighScoreScene highScoreScene;
    private GameOverScene gameOverScene;
    public SirtetWindow() {
        sizeX = 966;
        sizeY = 989;
        frame = frameSetup();
        changeScene(0);
    }
    public JFrame frameSetup() {
        frame = new JFrame("Sirtet");
        frame.setLayout(null);
        frame.setSize(sizeX, sizeY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
        return frame;
    }
    public void removeAll() {
        if(menuScene != null) {
            frame.remove(menuScene);
            frame.removeKeyListener(menuScene);
            menuScene = null;
        } else if(gameplayScene != null) {
            frame.remove(gameplayScene);
            frame.removeKeyListener(gameplayScene);
            gameplayScene = null;
        } else if(gameOverScene != null) {
            frame.remove(gameOverScene);
            frame.removeKeyListener(gameOverScene);
            gameOverScene = null;
        } else if (highScoreScene != null){
            frame.remove(highScoreScene);
            frame.removeKeyListener(highScoreScene);
            highScoreScene = null;
        }
    }
    public void changeScene(int sceneNum) {
        removeAll();
        switch(sceneNum) {
            case 0:
                menuScene = new MenuScene(this);
                menuScene.setSize(sizeX, sizeY);
                frame.addKeyListener(menuScene);
                frame.add(menuScene);
                break;
            case 1:
                gameplayScene = new GameplayScene(this);
                gameplayScene.setSize(sizeX, sizeY);
                frame.addKeyListener(gameplayScene);
                frame.add(gameplayScene);
                break;
            case 2:
                gameOverScene = new GameOverScene(this);
                gameOverScene.setSize(sizeX, sizeY);
                frame.addKeyListener(gameOverScene);
                frame.add(gameOverScene);
                break;
            case 3:
                highScoreScene = new HighScoreScene(this);
                highScoreScene.setSize(sizeX, sizeY);
                frame.addKeyListener(highScoreScene);
                frame.add(highScoreScene);
                break;
            default:
        }
        frame.repaint();
    }
}