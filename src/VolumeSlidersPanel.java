import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class holds a JPanel with JSliders within. Because these same JSliders and their functionality
 * are used by multiple other classes, it made sense to make them their own class to have
 * it editable all in the same place.
 */
public class VolumeSlidersPanel extends JPanel implements ChangeListener {
    public static final int VOLUME_CENTER_X = 190;
    private JPanel panel;
    private JSlider bgmSlider;
    private JSlider sfxSlider;

    public VolumeSlidersPanel(int xPos, int yPos) {
        panel = new JPanel();
        panel.setSize(600, 800);
        panel.setOpaque(false);
        panel.setLayout(null);
        bgmSlider = sliderSetup(xPos, yPos, false);
        sfxSlider = sliderSetup(xPos, yPos, true);
        bgmSlider.setBounds(xPos, yPos, 100, 50);
        sfxSlider.setBounds(xPos + 120, yPos, 100, 50);
        bgmSlider.setValue(SaveData.bgmVolume);
        sfxSlider.setValue(SaveData.sfxVolume);
        panel.add(bgmSlider);
        panel.add(sfxSlider);
    }

    public JSlider sliderSetup(int xPos, int yPos, boolean sfx) {
        JSlider slider = new JSlider(0, 9);
        slider.setOpaque(false);
        slider.addChangeListener(this);
        slider.setBounds(xPos + (sfx ? 120 : 0), yPos, 100, 50);
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

    public JPanel getPanel() {
        return panel;
    }
}