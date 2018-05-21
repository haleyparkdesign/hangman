import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Vector;
import java.util.LinkedList;

/**
 * GUI elements for single player option
 *
 * @author Haley Park, Margaret Harrigan
 * @version May 15, 2018
 */
public class SinglePlayerPanel extends JPanel {
    private JButton[] buttons;
    private String[] categoryNames = {"Animals", "Computer Science", "Wellesley", "Countries", "Movies", "Random"};
    private String categoryName;
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel, chooseCategoryPanel,gamePlayPanel;
    private JLabel guessLabel, wrongGuesses, hintLabel, categoryLabel, imageLabel, result;
    private JTextField guessField;
    private JTextField[] letters;
    private JButton hintButton, skipButton;
    private SinglePlayerRound newRound;
    private JLabel[] wrongGuessLabels;

    /**
     * Constructor for objects of SinglePlayerPanel
     */
    public SinglePlayerPanel() {
        wrongGuessLabels = new JLabel[9];
        chooseCategoryPanel = makeChooseCategoryPanel();

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(chooseCategoryPanel, "1");
        add(cardPanel);

        // initialize with showing the categories panel
        cardLayout.first(cardPanel);
    }

    /**
     * makeChooseCategoryPanel creates the opening panel for the single player panel.
     * The player will choose which category they want to play based on the options.
     * 
     * @return JPanel showing the categories
     */
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

        //adds the label and button listener to each button
        for (int i = 0; i < categoryNames.length; i++) {
            buttons[i] = new JButton(categoryNames[i]);
            buttons[i].addActionListener(new ButtonListener());
            panel.add(buttons[i]);
        }

        return panel;
    }

    /**
     * makeSinglePlayerGamePanel creates the game panel for the single player panel.
     * It contains scaffolding, letter boxes, a hint button, and a quit button.
     * 
     * @return JPanel showing the single player mode
     */
    private JPanel makeSinglePlayerGamePanel() {
        //gets how many empty letter boxes should be shown
        int numTextFields = newRound.getOriginal().length();

        //create all the panels
        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel lettersArea = new JPanel();
        JPanel letterBoxesArea = new JPanel();
        JPanel wrongGuessesArea = new JPanel();
        JPanel bottomPanel = new JPanel();

        //add constraints
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        categoryLabel = new JLabel("Category is: " + categoryName, SwingConstants.LEFT);

        //add the guess box
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
        c.anchor = GridBagConstraints.FIRST_LINE_END;
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
        lettersArea.setLayout(new BorderLayout(10, 10));
        letters = new JTextField[numTextFields];
        for (int i = 0; i < numTextFields; i++) {
            letters[i] = new JTextField("  ");
            letters[i].setHorizontalAlignment(JTextField.CENTER);
            letters[i].setEnabled(false);
            letters[i].setPreferredSize(new Dimension(40, 60));
            letters[i].setFont(new Font("Sans-Serif", Font.BOLD, 30));
            letterBoxesArea.add(letters[i]);
        }
        lettersArea.add(letterBoxesArea, BorderLayout.NORTH);

        //Add wrong guesses
        for (int i = 0; i < wrongGuessLabels.length; i++) {
            wrongGuessLabels[i] = new JLabel(" ");
            wrongGuessesArea.add(wrongGuessLabels[i]);
        }
        lettersArea.add(wrongGuessesArea, BorderLayout.SOUTH);

        //add the result label
        result = new JLabel("");
        lettersArea.add(result);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(lettersArea, c);
        
        // Add hint & quit buttons
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
        skipButton = new JButton("Play a Different Category");
        skipButton.addActionListener(new ButtonListener());
        panel.add(skipButton, c);

        return panel;
    }

    /**
     * ButtonListener class performs the desired actions when the buttons are clicked
     */
    private class ButtonListener implements ActionListener {
        /**
         * actionPerformed takes in an event and does the required action based on
         * the source.
         * 
         * @param ActionEvent from one of the listeners
         */
        public void actionPerformed (ActionEvent event) {
            //gives a hint
            if (event.getSource() == hintButton) {
                hintLabel.setText(newRound.getHint());
            } 
            //skips the round and brings you back to the categories panel
            else if (event.getSource() == skipButton) {
                //hide the game panel, show categories panel
                cardLayout.first(cardPanel);
                //gamePlayPanel = makeSinglePlayerGamePanel();
                resetGame();
            } 
            //picks a category
            else {
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

        /**
         * resetGame returns you to the categories panel
         */
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

    /**
     * TextFieldListener class gets the guess from the user
     */
    private class TextFieldListener implements ActionListener {
        /**
         * actionPerformed takes in an event and uses it as a guess
         * 
         * @param ActionEvent from one of the listeners
         */
        public void actionPerformed(ActionEvent e) {
            char guessedChar;
            LinkedList<Character> wrongGuesses = newRound.getWrongGuesses();

            String input = guessField.getText();
            System.out.println("Input: " + input);
            guessedChar = input.charAt(0);
            
            //makes a guess with the input
            newRound.makeGuess(guessedChar);
            
            //checks to see if the game is over
            if(newRound.didPlayerWin()){
                result.setText("You Won!");
            }
            else if(newRound.didPlayerLose()){
                result.setText("You lost. The word was " + newRound.getOriginal());
            }
            
            guessField.setText("");

            //adds the wrong guesses
            for (int i = 0; i < wrongGuesses.size(); i++) {
                wrongGuessLabels[i].setText("" + wrongGuesses.get(i));
            }
            fillBlanks(guessedChar);
            setImage();
        }

        /**
         * fillBlanks takes in a correct guess and adds it to the right place in 
         * the word
         * 
         * @param char guessedChar
         */
        private void fillBlanks(char guessedChar) {
            Vector<Integer> whereToFill = newRound.letterFit(guessedChar);
            for (int i = 0; i < whereToFill.size(); i++) {
                letters[whereToFill.get(i)].setText("" + Character.toUpperCase(guessedChar));
            }
        }

        /**
         * setImage sets the hangman image to the right one based on the number of 
         * wrong guesses
         */
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