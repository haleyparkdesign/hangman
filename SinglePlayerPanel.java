import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Vector;

/**
 * Write a description of class SinglePlayerGamePanel here.
 *
 * @author Haley Park
 * @version May 15, 2018
 */
public class SinglePlayerPanel extends JPanel {
    private JButton[] buttons;
    private String[] categoryNames = {"Animals", "Computer Science", "Wellesley", "Countries", "Movies", "Random"};
    private String categoryName;
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel, chooseCategoryPanel,gamePlayPanel;
    private JLabel guessLabel, wrongGuesses, hintLabel, categoryLabel, imageLabel;
    private JTextField guessField;
    private JTextField[] letters;
    private JButton hintButton, skipButton;
    private SinglePlayerRound newRound;

    public SinglePlayerPanel() {
        chooseCategoryPanel = makeChooseCategoryPanel();

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(chooseCategoryPanel, "1");
        add(cardPanel);

        // initialize with showing the categories panel
        cardLayout.first(cardPanel);
    }

    private JPanel makeChooseCategoryPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(5, 3, 10, 10));
        JLabel statusLabel = new JLabel("Choose a category: ", JLabel.CENTER);

        buttons = new JButton[6];

        panel.add(new JLabel("     ")); // fills the first row
        panel.add(new JLabel("     ")); 
        panel.add(new JLabel("     "));

        panel.add(new JLabel("     ")); // fills the grid
        panel.add(statusLabel);
        panel.add(new JLabel("     ")); // fills the grid

        for (int i = 0; i < categoryNames.length; i++) {
            buttons[i] = new JButton(categoryNames[i]);
            buttons[i].addActionListener(new ButtonListener());
            panel.add(buttons[i]);
        }

        return panel;
    }

    private JPanel makeSinglePlayerGamePanel() {
        int numTextFields = newRound.getOriginal().length();

        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel letterBoxes = new JPanel();
        JPanel bottomPanel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        categoryLabel = new JLabel("Category is: " + categoryName);

        JPanel guessPanel = new JPanel();
        guessLabel = new JLabel("Guess! ");
        guessField = new JTextField(5);
        guessField.addActionListener(new TextFieldListener());
        guessPanel.add(guessLabel);
        guessPanel.add(guessField);
        
        // topPanel.add(categoryLabel);
        topPanel.add(guessPanel);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(categoryLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(guessPanel, c);

        // Add the hangman image
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        try {
            BufferedImage image = ImageIO.read(new File("./images/0.png"));
            imageLabel = new JLabel(new ImageIcon(image));
            panel.add(imageLabel, c);
        } catch (IOException e) {
            System.out.println(e);
        }


        // Add guess boxes
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        letters = new JTextField[numTextFields];
        for (int i = 0; i < numTextFields; i++) {
            letters[i] = new JTextField("  ");
            letters[i].setHorizontalAlignment(JTextField.CENTER);
            letters[i].setEnabled(false);
            letters[i].setPreferredSize(new Dimension(40, 60));
            letters[i].setFont(new Font("Sans-Serif", Font.BOLD, 30));
            letterBoxes.add(letters[i]);
        }
        panel.add(letterBoxes, c);
        
        // Add wrong guesses
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        wrongGuesses = new JLabel();
        panel.add(wrongGuesses, c);

        // Add buttons
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
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
        skipButton = new JButton("Skip");
        skipButton.addActionListener(new ButtonListener());
        panel.add(skipButton, c);

        return panel;
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            if (event.getSource() == hintButton) {
                hintLabel.setText(newRound.getHint());
            } else if (event.getSource() == skipButton) {
                //hide the game panel, show categories panel
                cardLayout.first(cardPanel);
                //gamePlayPanel = makeSinglePlayerGamePanel();
                resetGame();
            } else {
                categoryName = "";
                int randomCategoryIndex; 
                for (int i = 0; i < categoryNames.length; i++) {
                    if (event.getSource() == buttons[i]) {
                        categoryName = categoryNames[i].replaceAll("\\s+", "_");
                        if (categoryName == "Random") {
                            randomCategoryIndex = (int)(Math.round(Math.random() * (categoryNames.length-1)));
                            categoryName = categoryNames[randomCategoryIndex];
                        } 
                    }
                }

                String categoryFileName = "./texts/" + categoryName + ".txt";
                System.out.println("You chose the category of: " + categoryName);
                newRound = new SinglePlayerRound(categoryFileName);

                gamePlayPanel = makeSinglePlayerGamePanel();
                cardPanel.add(gamePlayPanel, "2");

                categoryLabel.setText("Category is: " + categoryName);

                //hide the categories panel, show game panel
                cardLayout.last(cardPanel);
            }
        }

        private void resetGame() {
            cardPanel.removeAll();
            chooseCategoryPanel = makeChooseCategoryPanel();
            gamePlayPanel = makeSinglePlayerGamePanel();
            cardPanel = new JPanel();
            cardPanel.setLayout(cardLayout);

            cardPanel.add(chooseCategoryPanel, "1");
            cardPanel.add(gamePlayPanel, "2");

            add(cardPanel);

            // initialize with showing the categories panel
            cardLayout.first(cardPanel);
        }
    }

    private class TextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            char guessedChar;

            String input = guessField.getText();
            System.out.println("Input: " + input);
            guessedChar = input.charAt(0);

            newRound.makeGuess(guessedChar);
            guessField.setText("");
            wrongGuesses.setText(newRound.getWrongGuesses());
            fillBlanks(guessedChar);
            setImage();
        }

        private void fillBlanks(char guessedChar) {
            Vector<Integer> whereToFill = newRound.letterFit(guessedChar);
            for (int i = 0; i < whereToFill.size(); i++) {
                letters[whereToFill.get(i)].setText("" + Character.toUpperCase(guessedChar));
            }
        }

        private void setImage() {
            int numWrongGuesses = newRound.getNumWrongGuesses();
            try {
                BufferedImage image = ImageIO.read(new File("./images/" + numWrongGuesses + ".png"));
                imageLabel.setIcon(new ImageIcon(image));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}