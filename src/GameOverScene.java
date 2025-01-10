import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class contains the game over JPanel and everything within. It allows the user to enter their name
 * and then sends it off to SaveData.java to be inserted or thrown away, depending on whether it makes it
 * into the top 10 scores list. The user will be told if their name is over 10 characters or under 3, as these names
 * will not be allowed. After receiving a name, it inserts the score, and switches to the high score scene. */
class GameOverScene extends SirtetScene implements ActionListener {
    private JTextField nameField = nameField();
    private JLabel scoreLabel;

    public GameOverScene() {
        super(true);
        add(new LabelCenter("<html><p style=\"text-align:center;\">Game Over!<br>Enter Your Name:</p>", 40, 275));
        add(nameField);
    }

    public JTextField nameField() {
        JTextField field = new JTextField();

        field.setOpaque(false);
        field.setBounds(150, 400, 300, 50);
        field.setForeground(Color.black);
        field.setFont(SirtetWindow.getFont(30));
        field.addActionListener(this);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(null);
        return field;
    }

    /// Called when text-field is submitted
    public void actionPerformed(ActionEvent ignored) {
        String name = nameField.getText().trim();

        if (name.length() > 10 || name.length() < 3) {
            JOptionPane.showMessageDialog(SirtetWindow.frame, "Name Must be 3-10 Characters.");
            nameField.setText("");
            focusField();
            return;
        }

        SaveData.insertScore(name);
        SirtetWindow.changeScene(SceneID.HighScore);
    }

    ///  Called when field needs to become focused to accept user-input
    public void focusField() {
        nameField.requestFocusInWindow();
    }

    public void addScene() {
        setVisible(true);
        scoreLabel = new LabelCenter("Score: " + SaveData.currentScore, 40, 475);
        add(scoreLabel);
        nameField.setText("");
        focusField();
    }

    public void removeScene() {
        remove(scoreLabel);
        setVisible(false);
    }
}