/**
 * This class handles the menu scene and panel. It can launch a new GameplayScene instance, HighScoreScene instance,
 * quit to desktop, or change the bgm/sfx volumes. The JSliders communicate to SirtetAudio to change volume. */
class MenuScene extends SirtetScene {
    public MenuScene() {
        super(false);
        add(new VolumeSliders());
        add(new SirtetButton(Sirtet.menuImages[ImageID.PlayButton.ordinal()], 260, () -> SirtetWindow.changeScene(SceneID.Gameplay)));
        add(new SirtetButton(Sirtet.menuImages[ImageID.HighScoreButton.ordinal()], 320, () -> SirtetWindow.changeScene(SceneID.HighScore)));
        add(new SirtetButton(Sirtet.menuImages[ImageID.QuitButton.ordinal()], 380, () -> {
            Sirtet.audioClips[AudioID.BackgroundMusic.ordinal()].stop();
            SaveData.writeFile();
            System.exit(0);
        }));
        add(new SirtetPanel(false, g -> g.drawImage(Sirtet.menuImages[ImageID.MenuScene.ordinal()], 0, 0, Sirtet.observer)));
    }

    void addScene() {
        setVisible(true);
    }

    void removeScene() {
        setVisible(false);
    }
}