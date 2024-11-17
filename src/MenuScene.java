import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
class MenuScene extends JPanel implements ActionListener, MouseListener {
    private SirtetWindow frame;
    private JButton play;
    private JButton highScores;
    private JButton quit;
    private JPanel panel;
    public MenuScene(SirtetWindow frame) {
        this.frame = frame;
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setBackground(new Color(135, 232, 155));
        panel.setLayout(null);
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        play = buttonSetup(Sirtet.menuImages[1]);
        highScores = buttonSetup(Sirtet.menuImages[2]);
        quit = buttonSetup(Sirtet.menuImages[3]);
        play.setBounds(225, 300, 151, 39);
        highScores.setBounds(125, 360, 350, 42);
        quit.setBounds(247, 425, 107, 39);
        panel.add(play);
        panel.add(highScores);
        panel.add(quit);
        panel.add(this);
    }
    public JButton buttonSetup(BufferedImage image) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(image));
        button.setBorder(null);
        button.setBackground(null);
        button.addActionListener(this);
        button.setFocusable(false);
        button.addMouseListener(this);
        button.setContentAreaFilled(false);
        return button;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == play) {
            frame.changeScene(1);
        } else if(e.getSource() == highScores) {
            frame.changeScene(3);
        } else {
            System.exit(0);
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        Image currentImage = Sirtet.menuImages[0];
        g.drawImage(currentImage, 55, 150, 480, 93, Sirtet.observer);
    }
    public JPanel getPanel() {
        return panel;
    }
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == play) {
            play.setIcon(new ImageIcon(Sirtet.menuImages[4]));
        } else if(e.getSource() == highScores) {
            highScores.setIcon(new ImageIcon(Sirtet.menuImages[5]));
        } else {
            quit.setIcon(new ImageIcon(Sirtet.menuImages[6]));
        }
    }
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == play) {
            play.setIcon(new ImageIcon(Sirtet.menuImages[1]));
        } else if(e.getSource() == highScores) {
            highScores.setIcon(new ImageIcon(Sirtet.menuImages[2]));
        } else {
            quit.setIcon(new ImageIcon(Sirtet.menuImages[3]));
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}