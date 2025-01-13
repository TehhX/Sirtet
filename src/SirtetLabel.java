import javax.swing.*;
import java.awt.*;

/**
 * SirtetLabel is an abstract class intended to create specialized JLabels for use in Sirtet. It has subclasses
 * further specialized in aligning the label in a specific way for use within the program. */
class SirtetLabel extends JLabel {
    public SirtetLabel(String text, int pixelSize) {
        super(text);

        setFont(SirtetWindow.getFont(pixelSize));
        setForeground(Color.black);
    }

    public SirtetLabel(int pixelSize) {
        this("", pixelSize);
    }

    /// Use ONLY if all parameters of the label are going to be defined after initialization
    public SirtetLabel() {
        this("", 0);
    }
}

/// Creates a center-aligned SirtetLabel
class LabelCenter extends SirtetLabel {
    public LabelCenter(String text, int pixelSize, int yPos) {
        super(text, pixelSize);

        setBounds(
            (SirtetWindow.FRAME_SIZE_X - getPreferredSize().width) / 2,
            yPos,
            getPreferredSize().width,
            getPreferredSize().height
        );
    }
}

/// Creates a right-aligned SirtetLabel
class LabelRight extends SirtetLabel {
    public LabelRight(String text, int pixelSize, int xPos, int yPos) {
        super(text, pixelSize);

        setBounds(
            xPos - getPreferredSize().width,
            yPos,
            getPreferredSize().width,
            getPreferredSize().height
        );
    }
}