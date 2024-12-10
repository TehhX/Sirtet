import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
public abstract class ReactiveButton implements ActionListener, MouseListener {
    private JPanel panel;
    private JButton button;
    private BufferedImage inactiveImage;
    private BufferedImage activeImage;
    public ReactiveButton(BufferedImage inactiveImage, BufferedImage activeImage, int yOffset) {
        this.inactiveImage = inactiveImage;
        this.activeImage = activeImage;
        panelSetup();
        buttonSetup();
        int xSize = (int) button.getPreferredSize().getWidth();
        int ySize = (int) button.getPreferredSize().getHeight();
        button.setBounds(300 - xSize / 2, yOffset, xSize, ySize);
        panel.add(button);
    }
    public void panelSetup() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
    }
    public void buttonSetup() {
        button = new JButton();
        button.setIcon(new ImageIcon(this.inactiveImage));
        button.addActionListener(this);
        button.addMouseListener(this);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorder(null);
        button.setBackground(null);
    }
    public void mouseEntered(MouseEvent ignored) {
        button.setIcon(new ImageIcon(activeImage));
    }
    public void mouseExited(MouseEvent ignored) {
        button.setIcon(new ImageIcon(inactiveImage));
    }
    public JPanel getPanel() {
        return panel;
    }
    public abstract void actionPerformed(ActionEvent e);
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}