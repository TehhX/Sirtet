import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
class MenuScene extends JPanel {
    /**
     * This class handles the menu scene and panel. It can launch a new GameplayScene instance, HighScoreScene instance,
     * quit to desktop, or change the bgm/sfx volumes. The JSliders communicate to SirtetAudio to change volume.
     */
    private JPanel panel;
    public MenuScene() {
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        ReactiveButton playButton = new ReactiveButton(Sirtet.menuImages[0], Sirtet.menuImages[3], 280) {
            public void actionPerformed(ActionEvent ignored) {
                SirtetWindow.changeScene("Gameplay");
            }
        };
        ReactiveButton highScoreButton = new ReactiveButton(Sirtet.menuImages[1], Sirtet.menuImages[4], 340) {
            public void actionPerformed(ActionEvent ignored) {
                SirtetWindow.changeScene("HighScore");
            }
        };
        ReactiveButton quitButton = new ReactiveButton(Sirtet.menuImages[2], Sirtet.menuImages[5], 405) {
            public void actionPerformed(ActionEvent ignored) {
                SaveData.writeFile();
                System.exit(0);
            }
        };
        panel.add(playButton.getPanel());
        panel.add(highScoreButton.getPanel());
        panel.add(quitButton.getPanel());
        panel.add(new VolumeSlidersPanel(VolumeSlidersPanel.VOLUME_CENTER_X, 700).getPanel());
        panel.add(this);
    }
    public void paint(Graphics g) {
        super.paint(g);
        Image image = Sirtet.menuImages[6];
        g.drawImage(image, 0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y, Sirtet.observer);
        image = Sirtet.menuImages[7];
        g.drawImage(image, 600/2-388/2, 475, 388, 193, Sirtet.observer);
    }
    public JPanel getPanel() {
        return panel;
    }
}