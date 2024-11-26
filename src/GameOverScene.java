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
     * switches to the high score scene. The gameOverLabel is written with HTML syntax so it can be center aligned.
     */
    private JPanel panel;
    private JTextField nameField;
    public GameOverScene() {
        this.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        JLabel gameOverLabel = new JLabel("<html><p style=\"text-align:center;\">Game Over!<br>Enter Your Name</p>", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.black);
        gameOverLabel.setBounds(0, 275, 600, 100);
        gameOverLabel.setFont(Sirtet.SILKSCREEN_40);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel.setLayout(null);
        nameField = new JTextField();
        nameField.setBounds(150, 400, 300, 50);
        nameField.setForeground(Color.black);
        nameField.setFont(Sirtet.SILKSCREEN_30);
        nameField.addActionListener(this);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBackground(new Color(37, 218, 192));
        nameField.setBorder(null);
        panel.add(gameOverLabel);
        panel.add(nameField);
        panel.add(this);
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }
    public JPanel getPanel() {
        return panel;
    }
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        if(name.length() > 10 || name.length() < 3) {
            JOptionPane.showMessageDialog(SirtetWindow.frame, "Name Must be 3-10 Characters.");
            return;
        }
        if(name.contains(",") || name.contains(".")) {
            JOptionPane.showMessageDialog(SirtetWindow.frame, "No commas or periods allowed.");
            return;
        }
        SaveData.insertScore(name);
        SirtetWindow.changeScene(3);
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Sirtet.SIRTET_GREEN);
        g.fillRect(0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
    }
}