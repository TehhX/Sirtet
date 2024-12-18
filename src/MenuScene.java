/**
 * This class handles the menu scene and panel. It can launch a new GameplayScene instance, HighScoreScene instance,
 * quit to desktop, or change the bgm/sfx volumes. The JSliders communicate to SirtetAudio to change volume.
 */
class MenuScene extends SirtetPanel {
    public MenuScene() {
        super(false);
        add(new ReactiveButton(Sirtet.menuImages[0], 260, e -> SirtetWindow.changeScene(SceneID.Gameplay)));
        add(new ReactiveButton(Sirtet.menuImages[1], 320, e -> SirtetWindow.changeScene(SceneID.HighScore)));
        add(new ReactiveButton(Sirtet.menuImages[2], 380, e -> { SaveData.writeFile(); System.exit(0); }));
        add(new VolumeSliders());
        add(new SirtetPanel(false, g -> g.drawImage(Sirtet.menuImages[4], 0, 0, Sirtet.observer)));
    }
}