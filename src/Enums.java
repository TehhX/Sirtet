/**
 * This enum handles what Sonimortets are called. The square-looking block is O, the long 1x4 is I etc. This
 * helps understand what ordinal pertains to which shaped piece type.
 */
enum BlockType {
    O,
    I,
    S,
    Z,
    L,
    J,
    T
}

/// This enum handles what scenes are called.
enum SceneID {
    Menu,
    Gameplay,
    Highscore,
    Gameover
}