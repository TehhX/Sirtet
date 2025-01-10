import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * This class holds a JPanel with JSliders within. Because these same JSliders and their functionality
 * are used by multiple other classes, it made sense to make them their own class to have
 * it editable all in the same place. */
class VolumeSliders extends SirtetPanel implements ChangeListener {
    private JSlider bgmSlider = sliderSetup();
    private JSlider sfxSlider = sliderSetup();

    public VolumeSliders() {
        super(false);
        VolumeSlidersMaster.addSliderObject(this);

        bgmSlider.setBounds(191, 720, 100, 50);
        bgmSlider.setValue(SaveData.bgmVolume);
        add(bgmSlider);

        sfxSlider.setBounds(331, 720, 100, 50);
        sfxSlider.setValue(SaveData.sfxVolume);
        add(sfxSlider);

        add(new SirtetPanel(false, g -> g.drawImage(Sirtet.menuImages[5], 162, 732, 157, 22, Sirtet.observer)));
    }

    /// Sets up and returns a JSlider object
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

        VolumeSlidersMaster.updateAll();

        SirtetWindow.frame.requestFocus();
    }

    public void updateSliders() {
        bgmSlider.setValue(SaveData.bgmVolume);
        sfxSlider.setValue(SaveData.sfxVolume);
    }
}

/**
 * This class exists simply to update the values of all volume sliders objects when a value is changed in only one
 * of them. Without this, the values would be inconsistent between different VolumeSlider objects. */
class VolumeSlidersMaster {
    private static ArrayList<VolumeSliders> slidersArray = new ArrayList<>();

    public static void addSliderObject(VolumeSliders slidersObject) {
        slidersArray.add(slidersObject);
    }

    public static void updateAll() {
        for (VolumeSliders sliders : slidersArray)
            sliders.updateSliders();
    }
}