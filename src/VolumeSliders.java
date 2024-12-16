import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class holds a JPanel with JSliders within. Because these same JSliders and their functionality
 * are used by multiple other classes, it made sense to make them their own class to have
 * it editable all in the same place.
 */
class VolumeSliders extends JPanel implements ChangeListener {
    private JSlider bgmSlider = sliderSetup();
    private JSlider sfxSlider = sliderSetup();

    public VolumeSliders(int yPos) {
        SirtetWindow.basicPanelSetup(this, false);
        bgmSlider.setBounds(190, yPos, 100, 50);
        sfxSlider.setBounds(310, yPos, 100, 50);
        bgmSlider.setValue(SaveData.bgmVolume);
        sfxSlider.setValue(SaveData.sfxVolume);
        add(bgmSlider);
        add(sfxSlider);
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
            bgmSlider.repaint();
            SirtetAudio.updateBGMVolume();
        } else {
            SaveData.sfxVolume = sfxSlider.getValue();
            sfxSlider.repaint();
        }
        SirtetWindow.frame.requestFocus();
    }
}
