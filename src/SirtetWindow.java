import javax.swing.*;
class SirtetWindow extends JFrame {
    private JFrame frame;
    final int FRAME_SIZE_X = 600;
    final int FRAME_SIZE_Y = 800;
    private GameplayScene gameplayScene;
    private MenuScene menuScene;
    private HighScoreScene highScoreScene;
    private GameOverScene gameOverScene;
    public SirtetWindow() {
        frame = frameSetup();
        changeScene(0);
    }
    public JFrame frameSetup() {
        frame = new JFrame("Sirtet");
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        return frame;
    }
    public void removeAll() {
        if(menuScene != null) {
            frame.getContentPane().remove(menuScene.getPanel());
            menuScene = null;
        } else if(gameplayScene != null) {
            frame.remove(gameplayScene);
            frame.removeKeyListener(gameplayScene);
            gameplayScene.getGrid().stopTimer();
            gameplayScene = null;
        } else if(gameOverScene != null) {
            frame.remove(gameOverScene);
            frame.removeKeyListener(gameOverScene);
            gameOverScene = null;
        } else if (highScoreScene != null){
            frame.remove(highScoreScene);
            highScoreScene = null;
        }
    }
    public void changeScene(int sceneNum) {
        removeAll();
        switch(sceneNum) {
            case 0:
                menuScene = new MenuScene(this);
                menuScene.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
                frame.getContentPane().add(menuScene.getPanel());
                break;
            case 1:
                gameplayScene = new GameplayScene(this);
                gameplayScene.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
                frame.addKeyListener(gameplayScene);
                frame.add(gameplayScene);
                break;
            case 2:
                gameOverScene = new GameOverScene(this);
                gameOverScene.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
                frame.addKeyListener(gameOverScene);
                frame.add(gameOverScene);
                break;
            case 3:
                highScoreScene = new HighScoreScene(this);
                highScoreScene.setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
                frame.add(highScoreScene);
                break;
            default:
        }
        frame.repaint();
    }
}