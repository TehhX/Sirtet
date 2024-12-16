import javax.swing.*;
import java.awt.*;

/**
 * This class handles the menu scene and panel. It can launch a new GameplayScene instance, HighScoreScene instance,
 * quit to desktop, or change the bgm/sfx volumes. The JSliders communicate to SirtetAudio to change volume.
 */
class MenuScene extends JPanel {
    public MenuScene() {
        SirtetWindow.basicPanelSetup(this, false);
        JPanel backgroundPanel = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(Sirtet.menuImages[6], 0, 0, Sirtet.observer);
            }
        };
        SirtetWindow.basicPanelSetup(backgroundPanel, false);
        add(new ReactiveButton(Sirtet.menuImages[0], Sirtet.menuImages[3], 260, e -> SirtetWindow.changeScene(SceneID.Gameplay)));
        add(new ReactiveButton(Sirtet.menuImages[1], Sirtet.menuImages[4], 320, e -> SirtetWindow.changeScene(SceneID.HighScore)));
        add(new ReactiveButton(Sirtet.menuImages[2], Sirtet.menuImages[5], 380, e-> { SaveData.writeFile(); System.exit(0); }));
        add(new VolumeSliders(VolumeSliders.VOLUME_CENTER_X, 700));
        add(backgroundPanel);
    }
}