/**
 * A way of setting up scenes in Sirtet with certain always-needed methods to set up/remove the scene from the
 * main frame. Cannot have a background other than Sirtet Green.
 */
abstract class SirtetScene extends SirtetPanel {
    public SirtetScene(boolean opaque) {
        super(opaque, null);
        setVisible(false);
    }

    abstract void addScene();

    abstract void removeScene();
}