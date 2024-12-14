import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * This class creates a button using provided images and offset. The images will be shown when the button is hovered over,
 * and when not. yOffset specifies how far down the panel the button will be, while the x position is always centered.
 */
class ReactiveButton extends JButton implements MouseListener {
    private int yOffset;
    private ImageIcon inactiveImage;
    private ImageIcon activeImage;
    private ActionListener actionListener;

    public ReactiveButton(BufferedImage inactiveImage, BufferedImage activeImage, int yOffset, ActionListener actionListener) {
        this.inactiveImage = new ImageIcon(inactiveImage);
        this.activeImage = new ImageIcon(activeImage);
        this.yOffset = yOffset;
        this.actionListener = actionListener;
        buttonSetup();
    }

    public void buttonSetup() {
        setIcon(inactiveImage);
        addMouseListener(this);
        addActionListener(actionListener);
        setContentAreaFilled(false);
        setFocusable(false);
        setBorder(null);
        setBackground(null);
        int xSize = (int) getPreferredSize().getWidth();
        int ySize = (int) getPreferredSize().getHeight();
        setBounds((SirtetWindow.FRAME_SIZE_X - xSize) / 2, yOffset, xSize, ySize);
    }

    public void mouseEntered(MouseEvent ignored) {
        setIcon(activeImage);
    }

    public void mouseExited(MouseEvent ignored) {
        setIcon(inactiveImage);
    }

    public void mouseClicked(MouseEvent ignored) {}
    public void mousePressed(MouseEvent ignored) {}
    public void mouseReleased(MouseEvent ignored) {}
}