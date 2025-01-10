///  Handles what Sonimortet block types are called
enum BlockID {
    O,
    I,
    S,
    Z,
    L,
    J,
    T
}

/// Handles what scenes are called
enum SceneID {
    Menu,
    Gameplay,
    HighScore,
    GameOver
}

/// Handles what audio files are called
enum AudioID {
    BackgroundMusic,
    BlockPlace,
    OneRow,
    TwoRow,
    ThreeRow,
    FourRow,
    GameOver
}

/// Handles what image files are called
enum ImageID {
    PlayButton,
    HighScoreButton,
    QuitButton,
    MenuButton,
    MenuScene,
    SoundIcons
}

/// Handles font sized
enum FontID {
    Silk30,
    Silk40,
    Silk60
}