import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * This class holds a JPanel with JSliders within. Because these same JSliders and their functionality
 * are used by multiple other classes, it made sense to make them their own class to have
 * it editable all in the same place.
 */
class VolumeSliders extends SirtetPanel implements ChangeListener {
    private JSlider bgmSlider = sliderSetup();
    private JSlider sfxSlider = sliderSetup();

    public VolumeSliders() {
        super(false);
        bgmSlider.setBounds(191, 720, 100, 50);
        sfxSlider.setBounds(331, 720, 100, 50);
        bgmSlider.setValue(SaveData.bgmVolume);
        sfxSlider.setValue(SaveData.sfxVolume);
        add(bgmSlider);
        add(sfxSlider);
        add(new SirtetPanel(false) {
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(Sirtet.menuImages[5], 169, 732, 157, 22, Sirtet.observer);
            }
        });
    }

    public JSlider sliderSetup() {
        JSlider slider = new JSlider(0, 9);
        slider.setOpaque(false);
        slider.addChangeListener(this);
        return slider;
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == bgmSlider) {
            SaveData.bgmVolume = bgmSlider.getValue();
            SirtetAudio.updateBGMVolume();
        } else {
            SaveData.sfxVolume = sfxSlider.getValue();
        }
        SirtetWindow.frame.requestFocus();
    }
}
