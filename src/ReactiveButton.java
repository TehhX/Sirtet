import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * This class creates a button using provided images and offset. The images will be shown when the button is hovered over,
 * and when not. yOffset specifies how far down the panel the button will be, while the x position is always centered.
 */
public abstract class ReactiveButton extends JPanel implements ActionListener, MouseListener {
    private JButton button;
    private int yOffset;
    private BufferedImage inactiveImage;
    private BufferedImage activeImage;

    public ReactiveButton(BufferedImage inactiveImage, BufferedImage activeImage, int yOffset) {
        this.inactiveImage = inactiveImage;
        this.activeImage = activeImage;
        this.yOffset = yOffset;
        setLayout(null);
        setOpaque(false);
        setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        buttonSetup();
    }

    public void buttonSetup() {
        button = new JButton();
        button.setIcon(new ImageIcon(inactiveImage));
        button.addActionListener(this);
        button.addMouseListener(this);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorder(null);
        button.setBackground(null);
        int xSize = (int) button.getPreferredSize().getWidth();
        int ySize = (int) button.getPreferredSize().getHeight();
        button.setBounds(300 - xSize / 2, yOffset, xSize, ySize);
        add(button);
    }

    public void mouseEntered(MouseEvent ignored) {
        button.setIcon(new ImageIcon(activeImage));
    }

    public void mouseExited(MouseEvent ignored) {
        button.setIcon(new ImageIcon(inactiveImage));
    }

    public abstract void actionPerformed(ActionEvent ignored);

    public void mouseClicked(MouseEvent ignored) {}
    public void mousePressed(MouseEvent ignored) {}
    public void mouseReleased(MouseEvent ignored) {}
}