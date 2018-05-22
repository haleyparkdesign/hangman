import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Vector;
import java.util.LinkedList;

/**
 * GUI elements for multi player option
 *
 * @author Denise Chai, Haley Park
 * @version May 15, 2018
 */
public class MultiPlayerPanel extends JPanel {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel, gamePlayPanel1, gamePlayPanel2, enterWordPanel1, enterWordPanel2, resultPanel;
    private JLabel guessLabel1, guessLabel2, hintLabel1, hintLabel2, imageLabel1, imageLabel2;
    private JTextField guessField1, guessField2;
    private JTextField guessWord1;
    private JTextField guessWord2;
    private JTextField[] letters1,letters2;
    private JButton hintButton1, hintButton2, nextPlayer, results, startover;
    private MultiPlayerRound newRound;
    private JLabel[] wrongGuessLabels1, wrongGuessLabels2;
    String word1, word2;
    int count = 0;

    //-----------------------------------------------------------------
    //  Sets up the labels and the first two pages, which prompts
    //  two users to each provide a word for the other user to guess
    //-----------------------------------------------------------------
    public MultiPlayerPanel() {
        
        wrongGuessLabels1 = new JLabel[9];
        wrongGuessLabels2 = new JLabel[9];
        
        enterWordPanel1 = makeEnterWordPanel1();
        enterWordPanel2 = makeEnterWordPanel2();
        
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(enterWordPanel1, "1");
        
        cardPanel.add(enterWordPanel2, "2");
                        
        add(cardPanel);
      
        // initialize with showing the categories panel
        cardLayout.first(cardPanel);
       
    }
    
    /**
     * Creates the panel that allows the first user to enter the word for the second user to guess
     */
    private JPanel makeEnterWordPanel1() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 3, 10, 10));
        JLabel statusLabel = new JLabel("Player 1's turn to enter the word. Please only enter lowercase letters", JLabel.CENTER);
        guessWord1 = new JTextField();
        guessWord1.addActionListener(new TextFieldListener());
        panel.add(statusLabel);   
        panel.add(guessWord1);
        return panel;
    }
    
    /**
     * Creates the panel that allows the second user to enter the word for the first user to guess
     */
    private JPanel makeEnterWordPanel2() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 3, 10, 10));
        JLabel statusLabel = new JLabel("Player 2's turn to enter the word. Please only enter lowercase letters", JLabel.CENTER);
        guessWord2 = new JTextField();
        guessWord2.addActionListener(new TextFieldListener());
        panel.add(statusLabel);
        panel.add(guessWord2);        
        return panel;
    }
    
    /**
     * Creates the panel that allows the first user to input guessed letters and skip to the second user's turn
     */
    private JPanel makeMultiPlayerGamePanel1() {
        JPanel panel = new JPanel();
        JLabel currentPlayerLabel = new JLabel("Player 1's turn. ", JLabel.CENTER);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        int numTextFields1 = word1.length();
   
        JPanel topPanel1 = new JPanel();
        JPanel lettersArea1 = new JPanel();
        JPanel letterBoxesArea1 = new JPanel();
        JPanel wrongGuessesArea1 = new JPanel();
        JPanel bottomPanel1 = new JPanel();

        JPanel guessPanel1 = new JPanel();
        guessLabel1 = new JLabel("Guess! ");
        guessField1 = new JTextField(5);
        guessField1.addActionListener(new TextFieldListener());
        guessPanel1.add(guessLabel1);
        guessPanel1.add(guessField1);

        // topPanel.add(categoryLabel);
        topPanel1.add(guessPanel1);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        panel.add(guessPanel1, c);

        // Add the hangman image
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        
        try {
            BufferedImage image1 = ImageIO.read(new File("./images/0.png"));
            imageLabel1 = new JLabel(new ImageIcon(image1));
            panel.add(imageLabel1, c);
        } catch (IOException e) {
            System.out.println(e);
        }
        
        // Add guess boxes
        lettersArea1.setLayout(new BorderLayout(10, 10));
        letters1 = new JTextField[numTextFields1];
        for (int i = 0; i < numTextFields1; i++) {
            letters1[i] = new JTextField("  ");
            letters1[i].setHorizontalAlignment(JTextField.CENTER);
            letters1[i].setEnabled(false);
            letters1[i].setPreferredSize(new Dimension(40, 60));
            letters1[i].setFont(new Font("Sans-Serif", Font.BOLD, 30));
            letterBoxesArea1.add(letters1[i]);
        }
        lettersArea1.add(letterBoxesArea1, BorderLayout.NORTH);

        for (int i = 0; i < wrongGuessLabels1.length; i++) {
            wrongGuessLabels1[i] = new JLabel(" ");
            wrongGuessesArea1.add(wrongGuessLabels1[i]);
        }
        lettersArea1.add(wrongGuessesArea1, BorderLayout.SOUTH);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(lettersArea1, c);

        // Add buttons
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        hintButton1 = new JButton("Hint");
        hintButton1.addActionListener(new ButtonListener());
        panel.add(hintButton1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        hintLabel1 = new JLabel();
        panel.add(hintLabel1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;

        panel.add(currentPlayerLabel);

        return panel;
               
    }
    
    /**
     * Creates the panel that allows the second user to input guessed letters and skip to the first user's turn
     */
    private JPanel makeMultiPlayerGamePanel2() {
        JPanel panel = new JPanel();
        JLabel currentPlayerLabel = new JLabel("Player 2's turn. ", JLabel.CENTER);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int numTextFields2 = this.word2.length();
   
        JPanel topPanel2 = new JPanel();
        JPanel lettersArea2 = new JPanel();
        JPanel letterBoxesArea2 = new JPanel();
        JPanel wrongGuessesArea2 = new JPanel();
        JPanel bottomPanel2 = new JPanel();

        JPanel guessPanel2 = new JPanel();
        guessLabel2 = new JLabel("Guess! ");
        guessField2 = new JTextField(5);
        guessField2.addActionListener(new TextFieldListener());
        guessPanel2.add(guessLabel2);
        guessPanel2.add(guessField2);

        // topPanel.add(categoryLabel);
        topPanel2.add(guessPanel2);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        panel.add(guessPanel2, c);

        // Add the hangman image
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;

        try {
            BufferedImage image2 = ImageIO.read(new File("./images/0.png"));
            imageLabel2 = new JLabel(new ImageIcon(image2));
            panel.add(imageLabel2, c);
        } catch (IOException e) {
            System.out.println(e);
        }

        // Add guess boxes
        lettersArea2.setLayout(new BorderLayout(10, 10));
        letters2 = new JTextField[numTextFields2];
        for (int i = 0; i < numTextFields2; i++) {
            letters2[i] = new JTextField("  ");
            letters2[i].setHorizontalAlignment(JTextField.CENTER);
            letters2[i].setEnabled(false);
            letters2[i].setPreferredSize(new Dimension(40, 60));
            letters2[i].setFont(new Font("Sans-Serif", Font.BOLD, 30));
            letterBoxesArea2.add(letters2[i]);
        }
        lettersArea2.add(letterBoxesArea2, BorderLayout.NORTH);

        for (int i = 0; i < wrongGuessLabels2.length; i++) {
            wrongGuessLabels2[i] = new JLabel(" ");
            wrongGuessesArea2.add(wrongGuessLabels2[i]);
        }
        lettersArea2.add(wrongGuessesArea2, BorderLayout.SOUTH);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(lettersArea2, c);

        // Add buttons
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        hintButton2 = new JButton("Hint");
        hintButton2.addActionListener(new ButtonListener());
        panel.add(hintButton2, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        hintLabel2 = new JLabel();
        panel.add(hintLabel2, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;


        panel.add(currentPlayerLabel);
        
        return panel;
    }
    
    /**
     * Creates the panel that displays the results of the game. It gives information on the winner of the game as well as the correct words 
     * for each user's game
     */
    private JPanel makeResultPanel() {
        JPanel panel = new JPanel();
        if (newRound.player1Win() == newRound.player2Win()) {            
            JLabel resultLabel = new JLabel("It's a tie!", JLabel.CENTER);
            panel.add(resultLabel);
        }
        
        else {
            if (newRound.player1Win()) {
                JLabel resultLabel = new JLabel("Player 1 won!", JLabel.CENTER);
                panel.add(resultLabel);
            }
            else {
                JLabel resultLabel = new JLabel("Player 2 won!", JLabel.CENTER);
                panel.add(resultLabel);
            }
                
        }        
        JLabel resultLabel = new JLabel("The correct word for player 1 is " + word1 + " . The correct word for player 2 is "  + word2, JLabel.CENTER);
        panel.add(resultLabel);         
        
        //this button starts over the game
        startover = new JButton("Play Again");
        startover.addActionListener(new ButtonListener());
               
        panel.add(startover);
 
        return panel;
    }
    
    //*****************************************************************
    //  Represents the listener for all the buttons
    //***************************************************************** 
    private class ButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            if (event.getSource() == hintButton1 || event.getSource() == hintButton2) {
                if (event.getSource() == hintButton1) {
                    hintLabel1.setText(newRound.getHint1());
                }
                else {
                    hintLabel2.setText(newRound.getHint2());
                }
            } 
            
            else if (event.getSource() == nextPlayer ) {
                cardLayout.next(cardPanel);
            }

            else if (event.getSource() == results) {
                cardLayout.last(cardPanel);                        
            }
            
             else {                
                resetGame();
            }
        }

        /**
         * Resets all the variables of the panel and the game and starts a new game.
         */
        private void resetGame() {
            //reset variables;
            cardPanel.removeAll();
            gamePlayPanel1 = null;
            gamePlayPanel2 = null;
            resultPanel = null;
            guessLabel1 = null;
            guessLabel2 = null;
            hintLabel1 = null;
            hintLabel2 = null;
            imageLabel1 = null;
            imageLabel2 = null;
            guessField1 = null;
            guessField2 = null;
            guessWord1 = null;
            guessWord2 = null;
            letters1 = null;
            letters2 = null;
            hintButton1 = null;
            hintButton2 = null;
            nextPlayer = null;
            results = null;
            startover = null;
            newRound = null;
            wrongGuessLabels1 = null;
            wrongGuessLabels2 = null;
            word1 = null;
            word2 = null;
            count = 0;
             
            //initialize new game
            wrongGuessLabels1 = new JLabel[9];
            wrongGuessLabels2 = new JLabel[9];            
            enterWordPanel1 = makeEnterWordPanel1();
            enterWordPanel2 = makeEnterWordPanel2();
                
            cardPanel = new JPanel();
            cardPanel.setLayout(cardLayout);
            cardPanel.add(enterWordPanel1, "1");            
            cardPanel.add(enterWordPanel2, "2");            
                    
            add(cardPanel);
          
            // initialize with showing the categories panel
            cardLayout.first(cardPanel);                   
        }
    }

    //*****************************************************************
    //  Represents the listener for all the text fields
    //*****************************************************************
    private class TextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            count ++;
            char guessedChar1;
            char guessedChar2;  
            LinkedList<Character> wrongGuesses1 = new LinkedList<Character>();
            LinkedList<Character> wrongGuesses2 = new LinkedList<Character>();
            if (e.getSource() == guessWord1) {
                word1 = guessWord1.getText();
                gamePlayPanel1 = makeMultiPlayerGamePanel1();
                cardPanel.add(gamePlayPanel1, "3");
                cardLayout.next(cardPanel);               
            }

            else if (e.getSource() == guessWord2) {
                word2 = guessWord2.getText();
                gamePlayPanel2 = makeMultiPlayerGamePanel2();          
                cardPanel.add(gamePlayPanel2, "4");
                cardLayout.next(cardPanel);
            }
            
            //create a new MultiPlayerRound aftr both users have enter their words
            if (count == 2) {
                System.out.println("word1" + word1);
                System.out.println("word2" + word2);
                newRound = new MultiPlayerRound(word1, word2);
            }
            System.out.println(newRound.word1);
            wrongGuesses2 = newRound.getWrongGuesses2();   
            wrongGuesses1 = newRound.getWrongGuesses1();       
            if (e.getSource() == guessField1) {
                String input = guessField1.getText();
                System.out.println("Input: " + input);
                guessedChar1 = input.charAt(0);
    
                newRound.makeGuess1(guessedChar1);
                guessField1.setText("");
                System.out.println(newRound.getWrongGuesses1());
                if (newRound.getWrongGuesses1().size() > 0) {
                    for (int i = 0; i < wrongGuesses1.size(); i++) {
                        wrongGuessLabels1[i].setText("" + wrongGuesses1.get(i));
                    }
                }   
                fillBlanks1(guessedChar1);
                setImage();
                               
            }
            else if (e.getSource() == guessField2) {
                String input = guessField2.getText();
                System.out.println("Input: " + input);
                guessedChar2 = input.charAt(0);
    
                newRound.makeGuess2(guessedChar2);
                guessField2.setText("");
                             
                for (int i = 0; i < wrongGuesses2.size(); i++) {
                    wrongGuessLabels2[i].setText("" + wrongGuesses2.get(i));
                }
    
                fillBlanks2(guessedChar2);
                setImage();
            }
                                  
            if (newRound.getPlayer1Over()) {
                JLabel statusLabel = new JLabel("Player 1's turn is up! ", JLabel.CENTER);
  
                gamePlayPanel1.add(statusLabel);
                    
                nextPlayer = new JButton("Next Player");
                nextPlayer.addActionListener(new ButtonListener());
                                
                gamePlayPanel1.add(nextPlayer);

                
            }
            if (newRound.getPlayer2Over()) {
                JLabel statusLabel = new JLabel("Player 2's turn is up! ", JLabel.CENTER);
  
                gamePlayPanel2.add(statusLabel);
                    
                results = new JButton("Show Results");
                results.addActionListener(new ButtonListener());
                                
                gamePlayPanel2.add(results);
                resultPanel = makeResultPanel();
                cardPanel.add(resultPanel, "5");
            }

        }

        
        /**
         * Fills in the correct letters guessed for each game. 
         * @Param    guessedChar   character that is gussed correctly and will be shown in the previously blank grid  
         */
        private void fillBlanks1(char guessedChar) {
            Vector<Integer> whereToFill1 = newRound.letterFit1(guessedChar);
            for (int i = 0; i < whereToFill1.size(); i++) {
                letters1[whereToFill1.get(i)].setText("" + Character.toUpperCase(guessedChar));
            }
        }

        private void fillBlanks2(char guessedChar) {
            Vector<Integer> whereToFill2 = newRound.letterFit2(guessedChar);
            for (int i = 0; i < whereToFill2.size(); i++) {
                letters2[whereToFill2.get(i)].setText("" + Character.toUpperCase(guessedChar));
            }
        }
        /**
         * Updates the hangman image after each round of the game
         */
        private void setImage() {
            int numWrongGuesses1 = newRound.getNumWrongGuesses1();
            int numWrongGuesses2 = newRound.getNumWrongGuesses2();
            try {
                BufferedImage image1 = ImageIO.read(new File("./images/" + numWrongGuesses1 + ".png"));
                imageLabel1.setIcon(new ImageIcon(image1));
                BufferedImage image2 = ImageIO.read(new File("./images/" + numWrongGuesses2 + ".png"));
                imageLabel2.setIcon(new ImageIcon(image2));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
