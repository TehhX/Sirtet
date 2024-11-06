import javax.swing.*;
import java.awt.*;
class SirtetWindow extends JPanel {
    private JFrame frame;
    public SirtetWindow() {
        frame = frameSetup();
    }
    public JFrame frameSetup() {
        JFrame frame = new JFrame("Sirtet");
        frame.setLayout(null);
        // 950x950 adjusted for actual size
        frame.setSize(968, 997);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        return frame;
    }
    public void changeScene(int sceneNum) {
        frame.getContentPane().removeAll();
        switch(sceneNum) {
            case 0:
                frame.add(new MainMenuScene().getPanel());
                break;
            case 1:
                frame.add(new GameplayScene().getPanel());
                break;
            case 2:
                //frame.add(new HighScoreScene().getPanel());
                break;
            default:
                //frame.add(new GameOverScene().getPanel());
        }
        frame.repaint();
    }
}
class MainMenuScene {
    private JPanel panel;
    public MainMenuScene() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(950, 950);
    }
    public JPanel getPanel() {
        return panel;
    }
}
class GameplayScene {
    private JPanel panel;
    public GameplayScene() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(950, 950);
    }
    public JPanel getPanel() {
        return panel;
    }
}
class HighScoreScene {

}
class GameOverScene {

}