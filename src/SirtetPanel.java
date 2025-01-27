import javax.swing.*;
import java.awt.*;

/**
 * This is the class that all scene classes extend and use as a starting point to make themselves into their own panels
 * to be added to the frame. It has some initial setup that all panels use, and accepts a PaintPanel interface to paint
 * the background with. This can be null so that nothing is painted. */
class SirtetPanel extends JPanel {
    private final PanelPainter panelPainter;

    public SirtetPanel(boolean opaque, PanelPainter panelPainter) {
        super(null);

        this.panelPainter = panelPainter;
        setBackground(new Color(22, 207, 130));
        setSize(SirtetWindow.FRAME_SIZE_X, SirtetWindow.FRAME_SIZE_Y);
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

    /**
     * This functional interface will be called at the paint method of SirtetPanel. This will be used to make a
     * background for the paint method to use. */
    protected interface PanelPainter {
        void paint(Graphics g);
    }
}