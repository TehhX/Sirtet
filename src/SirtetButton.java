import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class creates a button using provided images and offset. The images will be shown when the button is hovered over,
 * and when not. yPos specifies how far down the panel the button will be, while the x position is always centered. */
class SirtetButton extends SirtetPanel implements MouseMotionListener, MouseListener {
    static final float defaultMultiplier = 0.75f;

    private final ButtonClick buttonClick;
    private final SirtetLabel label = new SirtetLabel();
    private boolean isIn = true;

    private final Font offFont;
    private final Font hoverFont;

    private final Rectangle offBounds;
    private final Rectangle hoverBounds;

    public SirtetButton(String text, ButtonClick buttonClick, int yPos, int pixelSize, float hoverMultiplier) {
        super(false);

        this.buttonClick = buttonClick;
        label.setText(text);

        offFont = SirtetWindow.getFont(pixelSize);
        hoverFont = new Font("Silkscreen", Font.PLAIN, (int) (pixelSize * hoverMultiplier));

        label.setFont(offFont);
        offBounds = new Rectangle(
            (int) ((SirtetWindow.FRAME_SIZE_X - label.getPreferredSize().width) / 2.0),
            yPos,
            label.getPreferredSize().width,
            label.getPreferredSize().height
        );

        label.setFont(hoverFont);
        hoverBounds = new Rectangle(
            (int) (offBounds.getX() + offBounds.getWidth() * (1 - hoverMultiplier) / 2.0),
            (int) (offBounds.getY() + offBounds.getHeight() * (1 - hoverMultiplier) / 2.0),
            label.getPreferredSize().width,
            label.getPreferredSize().height
        );

        invertLabel();
        add(label);
    }

    public SirtetButton(String text, ButtonClick buttonClick, int yPos, int pixelSize) {
        this(text, buttonClick, yPos, pixelSize, defaultMultiplier);
    }

    private void invertLabel() {
        isIn = !isIn;
        label.setFont(isIn ? hoverFont : offFont);
        label.setBounds(isIn ? hoverBounds : offBounds);
    }

    public void mouseMoved(MouseEvent e) {
        final boolean inToOut = offBounds.contains(e.getPoint()) && !isIn;
        final boolean outToIn = !offBounds.contains(e.getPoint()) && isIn;

        if (outToIn || inToOut)
            invertLabel();
    }

    public void mousePressed(MouseEvent ignored) {
        if (!isIn)
            return;

        buttonClick.onClick();
        invertLabel();
    }

    public void addButtonListener() {
        SirtetWindow.frame.addMouseListener(this);
        SirtetWindow.frame.addMouseMotionListener(this);
    }

    public void removeButtonListener() {
        SirtetWindow.frame.removeMouseListener(this);
        SirtetWindow.frame.removeMouseMotionListener(this);
    }

    interface ButtonClick {
        void onClick();
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseClicked(MouseEvent ignored) {}
    public void mouseReleased(MouseEvent ignored) {}
    public void mouseEntered(MouseEvent ignored) {}
    public void mouseExited(MouseEvent ignored) {}
}