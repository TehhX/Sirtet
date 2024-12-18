import javax.swing.*;
import java.awt.*;

public class SirtetPanel extends JPanel {
    public SirtetPanel(boolean opaque) {
        setSize(SirtetWindow.FRAME_SIZE_X, SirtetWindow.FRAME_SIZE_Y);
        setLayout(null);
        setBackground(new Color(22, 207, 130));
        setOpaque(opaque);
    }
}