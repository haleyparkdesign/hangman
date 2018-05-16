import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * Write a description of class SinglePlayerGamePanel here.
 *
 * @author Haley Park, (add your name as you modify this class!)
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
    private JButton hintButton, skipButton;
    private SinglePlayerRound newRound;

    public SinglePlayerPanel() {
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
        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        
        panel.setLayout(new BorderLayout());

        categoryLabel = new JLabel("Category is: " + categoryName);
        
        JPanel guessPanel = new JPanel();
        guessLabel = new JLabel("Guess! ");
        guessField = new JTextField(5);
        guessField.addActionListener(new TextFieldListener());
        guessPanel.add(guessLabel);
        guessPanel.add(guessField);
        topPanel.add(categoryLabel);
        topPanel.add(guessPanel);
        
        panel.add(topPanel, BorderLayout.PAGE_START);

        try {
            BufferedImage image = ImageIO.read(new File("./images/0.png"));
            imageLabel = new JLabel(new ImageIcon(image));
            panel.add(imageLabel, BorderLayout.LINE_START);
        } catch (IOException e) {
            System.out.println(e);
        }

        wrongGuesses = new JLabel();
        centerPanel.add(wrongGuesses);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        hintButton = new JButton("Hint");
        hintLabel = new JLabel();
        skipButton = new JButton("Skip");
        hintButton.addActionListener(new ButtonListener());
        skipButton.addActionListener(new ButtonListener());
        bottomPanel.add(hintButton);
        bottomPanel.add(hintLabel);
        bottomPanel.add(skipButton);
        
        panel.add(bottomPanel, BorderLayout.PAGE_END);

        return panel;
    }

    private class ButtonListener implements ActionListener {
        /** 
         *@param event is the event which causes an action to be performed
         **/
        public void actionPerformed (ActionEvent event) {
            if (event.getSource() == hintButton) {
                hintLabel.setText(newRound.getHint());
            } else if (event.getSource() == skipButton) {
                //hide the game panel, show categories panel
                cardLayout.first(cardPanel);
                gamePlayPanel = makeSinglePlayerGamePanel();
            } else {
                categoryName = "";
                int randomCategoryIndex; 
                for (int i = 0; i < categoryNames.length; i++) {
                    if (event.getSource() == buttons[i]) {
                        categoryName = categoryNames[i];
                        if (categoryName == "Random") {
                            randomCategoryIndex = (int)(Math.round(Math.random() * (categoryNames.length-1)));
                            categoryName = categoryNames[randomCategoryIndex];
                        } 
                    }
                }

                String categoryFileName = categoryName + ".txt";
                System.out.println("You chose the category of: " + categoryName);
                categoryLabel.setText("Category is: " + categoryName);

                newRound = new SinglePlayerRound(categoryFileName);

                //hide the categories panel, show game panel
                cardLayout.last(cardPanel);
            }
        } 
    }

    private class TextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = guessField.getText();
            System.out.println("Input: " + input);
            char guessedChar = input.charAt(0);
            newRound.makeGuess(guessedChar);
            guessField.setText("");
            wrongGuesses.setText(newRound.getWrongGuesses());
            setImage();
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