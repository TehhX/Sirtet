import javax.swing.*;
import java.awt.*;

/**
 * This class handles the menu scene and panel. It can launch a new GameplayScene instance, HighScoreScene instance,
 * quit to desktop, or change the bgm/sfx volumes. The JSliders communicate to SirtetAudio to change volume.
 */
class MenuScene extends JPanel {
    public MenuScene() {
        setSize(SirtetWindow.FRAME_SIZE_X, SirtetWindow.FRAME_SIZE_Y);
        setLayout(null);
        setSize(SirtetWindow.FRAME_SIZE_X, SirtetWindow.FRAME_SIZE_Y);
        JPanel menuBackground = new JPanel() {
            public void paint(Graphics g) {
                Image image = Sirtet.menuImages[6];
                g.drawImage(image, 0, 0, SirtetWindow.FRAME_SIZE_X, SirtetWindow.FRAME_SIZE_Y, Sirtet.observer);
                image = Sirtet.menuImages[7];
                g.drawImage(image, 600 / 2 - 388 / 2, 475, 388, 193, Sirtet.observer);
            }
        };
        menuBackground.setSize(SirtetWindow.FRAME_SIZE_X, SirtetWindow.FRAME_SIZE_Y);
        add(new ReactiveButton(Sirtet.menuImages[0], Sirtet.menuImages[3], 280, e -> SirtetWindow.changeScene(SceneID.Gameplay)));
        add(new ReactiveButton(Sirtet.menuImages[1], Sirtet.menuImages[4], 340, e -> SirtetWindow.changeScene(SceneID.HighScore)));
        add(new ReactiveButton(Sirtet.menuImages[2], Sirtet.menuImages[5], 405, e-> {
            SaveData.writeFile();
            System.exit(0);
        }));
        add(new VolumeSliders(VolumeSliders.VOLUME_CENTER_X, 700));
        add(menuBackground);
    }
}