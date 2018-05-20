import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Vector;
import java.util.LinkedList;

/**
 * Write a description of class SinglePlayerGamePanel here.
 * GUI elements for single player option
 *
 * @author Haley Park
 * @version May 15, 2018
 */

public class SinglePlayerPanel extends JPanel {
    private JTextField[] letters;
    private JButton hintButton, skipButton;
    private SinglePlayerRound newRound;
    private JLabel[] wrongGuessLabels;

    public SinglePlayerPanel() {
        wrongGuessLabels = new JLabel[9];
        chooseCategoryPanel = makeChooseCategoryPanel();

        cardPanel = new JPanel();

        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel letterBoxes = new JPanel();
        JPanel lettersArea = new JPanel();
        JPanel letterBoxesArea = new JPanel();
        JPanel wrongGuessesArea = new JPanel();
        JPanel bottomPanel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        categoryLabel = new JLabel("Category is: " + categoryName);
        categoryLabel = new JLabel("Category is: " + categoryName, SwingConstants.LEFT);

        JPanel guessPanel = new JPanel();
        guessLabel = new JLabel("Guess! ");
        guessField.addActionListener(new TextFieldListener());
        guessPanel.add(guessLabel);
        guessPanel.add(guessField);
        

        // topPanel.add(categoryLabel);
        topPanel.add(guessPanel);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(categoryLabel, c);
        

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        panel.add(guessPanel, c);

        // Add the hangman image

           // System.out.println(e);
        


        // Add guess boxes
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        lettersArea.setLayout(new BorderLayout(10, 10));
        letters = new JTextField[numTextFields];
        for (int i = 0; i < numTextFields; i++) {
            letters[i] = new JTextField("  ");
            letters[i].setEnabled(false);
            letters[i].setPreferredSize(new Dimension(40, 60));
            letters[i].setFont(new Font("Sans-Serif", Font.BOLD, 30));
            letterBoxes.add(letters[i]);
            letterBoxesArea.add(letters[i]);
        }
        lettersArea.add(letterBoxesArea, BorderLayout.NORTH);

        for (int i = 0; i < wrongGuessLabels.length; i++) {
            wrongGuessLabels[i] = new JLabel(" ");
            wrongGuessesArea.add(wrongGuessLabels[i]);
        }
        panel.add(letterBoxes, c);
        
        // Add wrong guesses
        lettersArea.add(wrongGuessesArea, BorderLayout.SOUTH);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        wrongGuesses = new JLabel();
        panel.add(wrongGuesses, c);
        c.gridy = 1;
        panel.add(lettersArea, c);

        // Add buttons
        c.fill = GridBagConstraints.HORIZONTAL;
        hintButton = new JButton("Hint");
        hintButton.addActionListener(new ButtonListener());
        panel.add(hintButton, c);
        

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        hintLabel = new JLabel();
        panel.add(hintLabel, c);
        

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;
    }
    private class TextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            char guessedChar;
            LinkedList<Character> wrongGuesses = newRound.getWrongGuesses();

            String input = guessField.getText();
            System.out.println("Input: " + input);


            newRound.makeGuess(guessedChar);
            guessField.setText("");
            wrongGuesses.setText(newRound.getWrongGuesses());

            for (int i = 0; i < wrongGuesses.size(); i++) {
                wrongGuessLabels[i].setText("" + wrongGuesses.get(i));
            }
            fillBlanks(guessedChar);
            setImage();
        }
    }
}
