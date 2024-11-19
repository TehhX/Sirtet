import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class GameOverScene extends JPanel implements ActionListener {
    /**
     * This class contains the game over JPanel and everything within. It allows the user to enter their name
     * and then sends it off to SaveData.java to be inserted or thrown away, depending on whether it makes it
     * into the top 10 scores list. The user will be told if their name is over 7 characters, and asked to re-enter
     * if it is, as the high score scene can only display names up to a certain size. After receiving a name, it
     * switches to the high score scene.
     */
    private SirtetWindow frame;
    private JPanel panel;
    private JTextField nameField;
    private JLabel label;
    public GameOverScene(SirtetWindow frame) {
        this.frame = frame;
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        // Uses HTML to change the paragraph style to justify center, adds a line break between lines.
        label = new JLabel("<html><p style=\"text-align:center;\">Game Over!<br>Enter Your Name</html></p>", SwingConstants.CENTER);
        label.setForeground(Color.black);
        label.setBounds(0, 275, 600, 100);
        label.setFont(Sirtet.SILKSCREEN_40);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel.setLayout(null);
        nameField = new JTextField();
        nameField.setBounds(150, 400, 300, 50);
        nameField.setForeground(Color.black);
        nameField.setFont(Sirtet.SILKSCREEN_30);
        nameField.addActionListener(this);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBackground(Sirtet.SIRTET_GREEN);
        nameField.setBorder(null);
        panel.add(label);
        panel.add(nameField);
        panel.add(this);
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }
    public JPanel getPanel() {
        return panel;
    }
    public void actionPerformed(ActionEvent e) {
        if(nameField.getText().length() > 10) {
            JOptionPane.showMessageDialog(frame, "10 Character Max!");
            return;
        }
        SaveData.insertScore(nameField.getText());
        frame.changeScene(3);
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(172, 241, 116));
        g.fillRect(0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
    }
}