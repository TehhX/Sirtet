import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
class MenuScene extends JPanel implements ActionListener, MouseListener {
    /**
     * This class handles the menu scene and panel. It can launch a new GameplayScene instance, HighScoreScene instance,
     * quit to desktop, or change the bgm/sfx volumes. The JSliders communicate to SirtetAudio to change volume.
     */
    private JButton play;
    private JButton highScores;
    private JButton quit;
    private JPanel panel;
    public MenuScene() {
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        play = buttonSetup(Sirtet.menuImages[0]);
        highScores = buttonSetup(Sirtet.menuImages[1]);
        quit = buttonSetup(Sirtet.menuImages[2]);
        play.setBounds(225, 280, 151, 39);
        highScores.setBounds(125, 340, 350, 42);
        quit.setBounds(247, 405, 107, 39);
        panel.add(play);
        panel.add(highScores);
        panel.add(quit);
        panel.add(new VolumeSlidersPanel(VolumeSlidersPanel.VOLUME_CENTER_X, 700).getPanel());
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
    public void paint(Graphics g) {
        super.paint(g);
        Image image = Sirtet.menuImages[6];
        g.drawImage(image, 0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y, Sirtet.observer);
        image = Sirtet.menuImages[7];
        g.drawImage(image, 600/2-388/2, 475, 388, 193, Sirtet.observer);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == play) {
            SirtetWindow.changeScene(1);
        } else if(e.getSource() == highScores) {
            SirtetWindow.changeScene(3);
        } else if (e.getSource() == quit){
            SaveData.writeFile();
            System.exit(0);
        }
    }
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == play) {
            play.setIcon(new ImageIcon(Sirtet.menuImages[3]));
        } else if(e.getSource() == highScores) {
            highScores.setIcon(new ImageIcon(Sirtet.menuImages[4]));
        } else {
            quit.setIcon(new ImageIcon(Sirtet.menuImages[5]));
        }
    }
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == play) {
            play.setIcon(new ImageIcon(Sirtet.menuImages[0]));
        } else if(e.getSource() == highScores) {
            highScores.setIcon(new ImageIcon(Sirtet.menuImages[1]));
        } else {
            quit.setIcon(new ImageIcon(Sirtet.menuImages[2]));
        }
    }
    public JPanel getPanel() {
        return panel;
    }
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}