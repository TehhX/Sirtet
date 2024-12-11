import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class contains the game over JPanel and everything within. It allows the user to enter their name
 * and then sends it off to SaveData.java to be inserted or thrown away, depending on whether it makes it
 * into the top 10 scores list. The user will be told if their name is over 10 characters, under 3, or contains spaces.
 * These names will not be allowed. After receiving a name, it switches to the high score scene.
 */
class GameOverScene extends JPanel implements ActionListener {
    private JPanel panel;
    private JTextField nameField;

    public GameOverScene() {
        setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        JLabel gameOverLabel = new JLabel("<html><p style=\"text-align:center;\">Game Over!<br>Enter Your Name:</p>", SwingConstants.CENTER);
        JLabel pointsLabel = new JLabel("Score: " + SaveData.currentScore, SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.black);
        gameOverLabel.setBounds(0, 275, Sirtet.FRAME_SIZE_X, 100);
        gameOverLabel.setFont(Sirtet.SILKSCREEN_40);
        pointsLabel.setForeground(Color.black);
        pointsLabel.setBounds(0, 475, Sirtet.FRAME_SIZE_X, 52);
        pointsLabel.setFont(Sirtet.SILKSCREEN_40);
        panel = new JPanel();
        panel.setSize(Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
        panel.setLayout(null);
        nameField = setField();
        panel.add(gameOverLabel);
        panel.add(pointsLabel);
        panel.add(nameField);
        panel.add(this);
    }

    public JTextField setField() {
        JTextField field = new JTextField();
        field.setBounds(150, 400, 300, 50);
        field.setForeground(Color.black);
        field.setFont(Sirtet.SILKSCREEN_30);
        field.addActionListener(this);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBackground(new Color(37, 218, 192));
        field.setBorder(null);
        return field;
    }

    public void focusField() {
        nameField.requestFocusInWindow();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        if (name.length() > 10 || name.length() < 3 || name.contains(" ")) {
            JOptionPane.showMessageDialog(SirtetWindow.frame, "Name Must be 3-10 Characters, no spaces.");
            nameField.setText("");
            focusField();
            return;
        }
        SaveData.insertScore(name);
        SirtetWindow.changeScene(SceneID.Highscore);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Sirtet.SIRTET_GREEN);
        g.fillRect(0, 0, Sirtet.FRAME_SIZE_X, Sirtet.FRAME_SIZE_Y);
    }
}