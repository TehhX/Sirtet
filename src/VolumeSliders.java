import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * This class holds a JPanel with JSliders within. Because these same JSliders and their functionality
 * are used by multiple other classes, it made sense to make them their own class to have
 * it editable all in the same place. */
class VolumeSliders extends SirtetPanel implements ChangeListener {
    /// The master list of all VolumeSliders instances for the purpose of updating all of them at once
    private static ArrayList<VolumeSliders> masterSlidersList = new ArrayList<>();

    private JSlider bgmSlider = sliderSetup();
    private JSlider sfxSlider = sliderSetup();

    public VolumeSliders() {
        super(false);

        masterSlidersList.add(this);

        bgmSlider.setBounds(191, 720, 100, 50);
        add(bgmSlider);

        sfxSlider.setBounds(331, 720, 100, 50);
        add(sfxSlider);

        add(new SirtetPanel(
            false,
            g -> g.drawImage(Sirtet.menuImages[ImageID.SoundIcons.ordinal()], 166, 732, 157, 22, Sirtet.observer))
        );

        updateThisSliders();
    }

    /// Creates, sets up and returns a JSlider object
    public JSlider sliderSetup() {
        JSlider slider = new JSlider(0, 9);
        slider.setOpaque(false);
        slider.addChangeListener(this);
        return slider;
    }

    /// When slider is moved
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == bgmSlider) {
            SaveData.bgmVolume = bgmSlider.getValue();
            SirtetAudio.updateBGMVolume();
        }
        else if (e.getSource() == sfxSlider)
            SaveData.sfxVolume = sfxSlider.getValue();

        updateAllSliders();
        SirtetWindow.frame.requestFocus();
    }

    /// Updates the sliders in this instance to the recorded BGM and SFX values
    private void updateThisSliders() {
        bgmSlider.setValue(SaveData.bgmVolume);
        sfxSlider.setValue(SaveData.sfxVolume);
    }

    /// Updates all sliders that exist
    private static void updateAllSliders() {
        for (VolumeSliders sliders : masterSlidersList)
            sliders.updateThisSliders();
    }
}