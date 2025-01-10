import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * This class creates a button using provided images and offset. The images will be shown when the button is hovered over,
 * and when not. yPos specifies how far down the panel the button will be, while the x position is always centered. */
class SirtetButton extends JButton implements MouseListener, ActionListener {
    private final ImageIcon inactiveImage;
    private final ImageIcon activeImage;
    private final ButtonClick buttonClick;

    public SirtetButton(BufferedImage image, int yPos, ButtonClick buttonClick) {
        this.inactiveImage = new ImageIcon(image);
        this.buttonClick = buttonClick;

        // Setup
        addMouseListener(this);
        addActionListener(this);
        setIcon(inactiveImage);
        setContentAreaFilled(false);
        setFocusable(false);
        setBorder(null);
        setBackground(null);

        // Center and get size of image
        int xSize = (int) getPreferredSize().getWidth();
        int ySize = (int) getPreferredSize().getHeight();
        setBounds((SirtetWindow.FRAME_SIZE_X - xSize) / 2, yPos, xSize, ySize);

        // Make new image of size: 0.75 * size
        xSize = (int) (xSize * 0.75);
        ySize = (int) (ySize * 0.75);
        activeImage = new ImageIcon(image.getScaledInstance(xSize, ySize, BufferedImage.SCALE_DEFAULT));
    }

    /// Sets button icon to 75% scale image when moused-over
    public void mouseEntered(MouseEvent ignored) {
        setIcon(activeImage);
    }

    /// Resets button icon to 100% scale image when mouse leaves boundaries
    public void mouseExited(MouseEvent ignored) {
        setIcon(inactiveImage);
    }

    public void actionPerformed(ActionEvent e) {
        buttonClick.onClick();
        mouseExited(null);
    }

    interface ButtonClick {
        void onClick();
    }

    // Below methods not in use
    public void mouseClicked(MouseEvent ignored) {}
    public void mousePressed(MouseEvent ignored) {}
    public void mouseReleased(MouseEvent ignored) {}
}