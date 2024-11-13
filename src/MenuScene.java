import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
class MenuScene extends JPanel implements KeyListener {
    private SirtetWindow frame;
    public MenuScene(SirtetWindow frame) {
        this.setLayout(null);
        this.frame = frame;
        JLabel label  = new JLabel("MenuScene (Press Any)");
        label.setBounds(0, 0, 1000, 100);
        this.add(label);
    }
    public void keyPressed(KeyEvent e) {
        frame.changeScene(1);
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}