import javax.swing.*;
class SirtetWindow {
    private JFrame frame;
    private int sizeX;
    private int sizeY;
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
        frame.setVisible(true);
        return frame;
    }
    public void changeScene(int sceneNum) {
        frame.getContentPane().removeAll();
        switch(sceneNum) {
            case 0:
                frame.add(new MenuScene().getPanel());
                break;
            case 1:
                GameplayScene gameplayScene = new GameplayScene();
                gameplayScene.setSize(sizeX, sizeY);
                frame.addKeyListener(gameplayScene);
                frame.add(gameplayScene);
                break;
            case 2:
                frame.add(new GameOverScene().getPanel());
                break;
            case 3:
                frame.add(new HighScoreScene().getPanel());
                break;
            default:
        }
        frame.repaint();
    }
}