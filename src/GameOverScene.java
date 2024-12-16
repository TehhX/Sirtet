import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class contains the game over JPanel and everything within. It allows the user to enter their name
 * and then sends it off to SaveData.java to be inserted or thrown away, depending on whether it makes it
 * into the top 10 scores list. The user will be told if their name is over 10 characters, under 3, or contains spaces.
 * These names will not be allowed. After receiving a name, it switches to the high score scene and inserts the score.
 */
class GameOverScene extends JPanel implements ActionListener {
    private JTextField nameField;

    public GameOverScene() {
        SirtetWindow.basicPanelSetup(this, true);
        JLabel gameOverLabel = SirtetWindow.labelSetupCenter("<html><p style=\"text-align:center;\">Game Over!<br>Enter Your Name:</p>", Sirtet.SILKSCREEN_40, 275);
        JLabel pointsLabel = SirtetWindow.labelSetupCenter("Score: " + SaveData.currentScore, Sirtet.SILKSCREEN_40, 475);
        nameField = new JTextField();
        setField();
        add(gameOverLabel);
        add(pointsLabel);
        add(nameField);
    }

    public void setField() {
        nameField.setOpaque(false);
        nameField.setBounds(150, 400, 300, 50);
        nameField.setForeground(Color.black);
        nameField.setFont(Sirtet.SILKSCREEN_30);
        nameField.addActionListener(this);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBorder(null);
    }

    public void actionPerformed(ActionEvent ignored) {
        String name = nameField.getText();
        if (name.length() > 10 || name.length() < 3 || name.contains(" ")) {
            JOptionPane.showMessageDialog(SirtetWindow.frame, "Name Must be 3-10 Characters, no spaces.");
            nameField.setText("");
            focusField();
            return;
        }
        SaveData.insertScore(name);
        SirtetWindow.changeScene(SceneID.HighScore);
    }

    public void focusField() {
        nameField.requestFocusInWindow();
    }
}