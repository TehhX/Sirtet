import javax.swing.*;
class SirtetWindow extends JFrame {
    private JFrame frame;
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
        frame.setIconImage(Sirtet.icon);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        return frame;
    }
    public void removeAll() {
        frame.requestFocus();
        if(menuScene != null) {
            frame.getContentPane().remove(menuScene.getPanel());
            menuScene = null;
        } else if(gameplayScene != null) {
            frame.getContentPane().remove(gameplayScene.getPanel());
            frame.removeKeyListener(gameplayScene);
            gameplayScene.getGrid().stopTimer();
            gameplayScene = null;
        } else if(gameOverScene != null) {
            frame.getContentPane().remove(gameOverScene.getPanel());
            gameOverScene = null;
        } else if (highScoreScene != null){
            frame.getContentPane().remove(highScoreScene.getPanel());
            frame.removeKeyListener(highScoreScene);
            highScoreScene = null;
        }
    }
    public void changeScene(int sceneNum) {
        removeAll();
        switch(sceneNum) {
            case 0:
                menuScene = new MenuScene(this);
                frame.getContentPane().add(menuScene.getPanel());
                break;
            case 1:
                gameplayScene = new GameplayScene(this);
                frame.addKeyListener(gameplayScene);
                frame.getContentPane().add(gameplayScene.getPanel());
                break;
            case 2:
                gameOverScene = new GameOverScene(this);
                frame.getContentPane().add(gameOverScene.getPanel());
                break;
            case 3:
                highScoreScene = new HighScoreScene(this);
                frame.addKeyListener(highScoreScene);
                frame.getContentPane().add(highScoreScene.getPanel());
                break;
            default:
        }
        frame.repaint();
    }
}