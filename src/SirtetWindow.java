import javax.swing.*;
class SirtetWindow extends JPanel {
    private JFrame frame;
    public SirtetWindow() {
        frame = frameSetup();
        changeScene(0);
    }
    public JFrame frameSetup() {
        frame = new JFrame("Sirtet");
        frame.setLayout(null);
        frame.setSize(966, 989);
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