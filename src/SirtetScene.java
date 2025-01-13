/**
 * A way of setting up scenes in Sirtet with certain always-needed methods to set up/remove the scene from the
 * main frame. Cannot have a background other than the default Sirtet Green shown only when not opaque. */
abstract class SirtetScene extends SirtetPanel {
    public SirtetScene(boolean opaque) {
        super(opaque);

        setVisible(false);
    }

    /**
     * To add a scene to the main frame, this method will be called. Must include "setVisible(true)", along with
     * the addButtonListener calls of any buttons in the scene and whatever setup is required. */
    public abstract void addScene();

    /**
     * To add a scene to the main frame, this method will be called. Must include "setVisible(true)", along with
     * the removeButtonListener calls of any buttons in the scene and whatever cleanup is required. */
    public abstract void removeScene();
}