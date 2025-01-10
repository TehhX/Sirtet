import javax.swing.*;
import java.awt.*;

/**
 * Interface for use with SirtetPanel's paint method, the background of a panel. Single method interfaces can be
 * used with lambdas in their implementations. */
interface PanelPainter {
    void paint(Graphics g);
}

/**
 * This is the class that all scene classes extend and use as a starting point to make themselves into their own panels
 * to be added to the frame. It has some initial setup that all panels use, and accepts a PaintPanel interface to paint
 * the background with. This can be null so that nothing is painted. */
class SirtetPanel extends JPanel {
    PanelPainter panelPainter;

    public SirtetPanel(boolean opaque, PanelPainter panelPainter) {
        super(null);

        this.panelPainter = panelPainter;
        setSize(SirtetWindow.FRAME_SIZE_X, SirtetWindow.FRAME_SIZE_Y);
        setBackground(new Color(22, 207, 130));
        setOpaque(opaque);
    }

    public SirtetPanel(boolean opaque) {
        this(opaque, null);
    }

    /**
     * Paint method for painting assets/components on the panel. Uses the panel-painter interface if passed.
     * If not passed, it will be null and skipped. */
    public void paint(Graphics g) {
        super.paint(g);
        if (panelPainter != null)
            panelPainter.paint(g);
    }
}

abstract class SirtetScene extends SirtetPanel {
    public SirtetScene(boolean opaque, PanelPainter panelPainter) {
        super(opaque, panelPainter);
        setVisible(false);
    }

    public SirtetScene(boolean opaque) {
        this(opaque, null);
    }

    abstract void addScene();

    abstract void removeScene();
}