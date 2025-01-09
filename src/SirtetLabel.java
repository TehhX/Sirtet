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

    public SirtetLabel(String text, FontID fontID, int xPos, int yPos) {
        setText(text);
        setFont(SirtetWindow.getFont(fontID));
        setForeground(Color.black);
        this.xPos = xPos;
        this.yPos = yPos;
        this.preferredWidth = getPreferredSize().width;
        this.preferredHeight = getPreferredSize().height;
    }
}

/// Creates a center-aligned SirtetLabel
class LabelCenter extends SirtetLabel {
    public LabelCenter(String text, FontID fontID, int yPos) {
        super(text, fontID, 0, yPos);
        setBounds((SirtetWindow.FRAME_SIZE_X - preferredWidth) / 2, yPos, preferredWidth, preferredHeight);
    }
}

/// Creates a right-aligned SirtetLabel
class LabelRight extends SirtetLabel {
    public LabelRight(String text, FontID fontID, int xPos, int yPos) {
        super(text, fontID, xPos, yPos);
        setBounds(xPos - preferredWidth, yPos, preferredWidth, preferredHeight);
    }
}