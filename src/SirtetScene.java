/**
 * A way of setting up scenes in Sirtet with certain always-needed methods to set up/remove the scene from the
 * main frame. Cannot have a background other than Sirtet Green. */
abstract class SirtetScene extends SirtetPanel {
    public SirtetScene(boolean opaque) {
        super(opaque);
        setVisible(false);
    }

    /// To add a scene to the main frame, this method will be called. Must include "setVisible(true)".
    abstract void addScene();

    /// To remove a scene from the main frame, this method will be called. Must include "setVisible(false)".
    abstract void removeScene();
}