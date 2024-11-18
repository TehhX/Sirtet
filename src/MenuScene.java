import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
class MenuScene extends JPanel implements ActionListener, MouseListener, ChangeListener {
    private SirtetWindow frame;
    private JButton play;
    private JButton highScores;
    private JButton quit;
    private JPanel panel;
    private JSlider bgmSlider;
    private JSlider sfxSlider;
    public MenuScene(SirtetWindow frame) {
        this.frame = frame;
        this.setOpaque(false);
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        play = buttonSetup(Sirtet.menuImages[0]);
        highScores = buttonSetup(Sirtet.menuImages[1]);
        quit = buttonSetup(Sirtet.menuImages[2]);
        play.setBounds(225, 300, 151, 39);
        highScores.setBounds(125, 360, 350, 42);
        quit.setBounds(247, 425, 107, 39);
        bgmSlider = sliderSetup();
        sfxSlider = sliderSetup();
        bgmSlider.setBounds(200, 700, 100, 50);
        sfxSlider.setBounds(320, 700, 100, 50);
        bgmSlider.setValue(SaveData.bgmVolume);
        sfxSlider.setValue(SaveData.sfxVolume);
        panel.add(play);
        panel.add(highScores);
        panel.add(quit);
        panel.add(bgmSlider);
        panel.add(sfxSlider);
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
    public JSlider sliderSetup() {
        JSlider slider = new JSlider(0, 9);
        slider.setOpaque(false);
        slider.addChangeListener(this);
        return slider;
    }
    public void paint(Graphics g) {
        super.paint(g);
        Image image = Sirtet.menuImages[6];
        g.drawImage(image, 0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y, Sirtet.observer);
    }
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == bgmSlider) {
            SaveData.bgmVolume = bgmSlider.getValue();
            SirtetAudio.updateBGMVolume();
        } else {
            SaveData.sfxVolume = sfxSlider.getValue();
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == play) {
            frame.changeScene(1);
        } else if(e.getSource() == highScores) {
            frame.changeScene(3);
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