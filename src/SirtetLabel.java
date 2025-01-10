import javax.swing.*;
import java.awt.*;

/**
 * SirtetLabel is an abstract class intended to create specialized JLabels for use in Sirtet. It has subclasses
 * further specialized in aligning the label in a specific way for use within the program. */
class SirtetLabel extends JLabel {
    protected final int xPos;
    protected final int yPos;

    protected final int preferredWidth;
    protected final int preferredHeight;

    public SirtetLabel(String text, int pixelSize, int xPos, int yPos) {
        super(text);

        setFont(SirtetWindow.getFont(pixelSize));
        setForeground(Color.black);

        this.xPos = xPos;
        this.yPos = yPos;

        this.preferredWidth = getPreferredSize().width;
        this.preferredHeight = getPreferredSize().height;
    }

    public SirtetLabel(String text, int pixelSize) {
        this(text, pixelSize, 0, 0);
    }

    public SirtetLabel(int pixelSize) {
        this("", pixelSize, 0, 0);
    }

    /// Use ONLY if all parameters of the label are going to be defined after initialization
    public SirtetLabel() {
        this("", 0, 0, 0);
    }
}

/// Creates a center-aligned SirtetLabel
class LabelCenter extends SirtetLabel {
    public LabelCenter(String text, int pixelSize, int yPos) {
        super(text, pixelSize);

        setBounds((SirtetWindow.FRAME_SIZE_X - preferredWidth) / 2, yPos, preferredWidth, preferredHeight);
    }
}

/// Creates a right-aligned SirtetLabel
class LabelRight extends SirtetLabel {
    public LabelRight(String text, int pixelSize, int xPos, int yPos) {
        super(text, pixelSize);

        setBounds(xPos - preferredWidth, yPos, preferredWidth, preferredHeight);
    }
}