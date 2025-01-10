/**
 * This class handles the menu scene and panel. It can launch a new GameplayScene instance, HighScoreScene instance,
 * quit to desktop, or change the bgm/sfx volumes. The JSliders communicate to SirtetAudio to change volume. */
class MenuScene extends SirtetScene {
    private final SirtetButton playButton;
    private final SirtetButton highscoresButton;
    private final SirtetButton quitButton;

    public MenuScene() {
        super(false);

        playButton = new SirtetButton(
            "PLAY",
            () -> SirtetWindow.changeScene(SceneID.Gameplay),
            240,
            55
        );
        highscoresButton = new SirtetButton(
            "HIGHSCORES",
            () -> SirtetWindow.changeScene(SceneID.HighScore),
            310,
            55
        );
        quitButton = new SirtetButton(
            "QUIT",
            () -> {
                Sirtet.audioClips[AudioID.BackgroundMusic.ordinal()].stop();
                SirtetWindow.frame.dispose();
                SaveData.writeFile();
                System.exit(0);
            },
            380,
            55
        );

        add(playButton);
        add(highscoresButton);
        add(quitButton);
        add(new VolumeSliders());
        add(new SirtetPanel(
            false,
            g -> g.drawImage(
                Sirtet.menuImages[ImageID.MenuScene.ordinal()],
                0,
                0,
                Sirtet.observer)
            )
        );
    }

    public void addScene() {
        playButton.addButtonListener();
        highscoresButton.addButtonListener();
        quitButton.addButtonListener();

        setVisible(true);
    }

    public void removeScene() {
        playButton.removeButtonListener();
        highscoresButton.removeButtonListener();
        quitButton.removeButtonListener();

        setVisible(false);
    }
}