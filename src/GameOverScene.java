import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class GameOverScene extends JPanel implements ActionListener {
    private SirtetWindow frame;
    private JPanel panel;
    private JTextField nameField;
    private JLabel label;
    public GameOverScene(SirtetWindow frame) {
        this.frame = frame;
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        label = new JLabel("Enter Your Name", SwingConstants.CENTER);
        label.setForeground(Color.red);
        label.setBounds(0, 275, 600, 100);
        label.setFont(Sirtet.SILKSCREEN_40);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel.setLayout(null);
        nameField = new JTextField();
        nameField.setBounds(150, 400, 300, 50);
        nameField.setBorder(null);
        nameField.setForeground(Color.black);
        nameField.setFont(Sirtet.SILKSCREEN_30);
        nameField.addActionListener(this);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBackground(null);
        nameField.setOpaque(false);
        panel.add(label);
        panel.add(nameField);
        panel.add(this);
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }
    public JPanel getPanel() {
        return panel;
    }
    public void actionPerformed(ActionEvent e) {
        if(nameField.getText().length() > 10) return;
        SaveData.insertScore(nameField.getText());
        frame.changeScene(3);
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(210, 241, 116));
        g.fillRect(0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
    }
}