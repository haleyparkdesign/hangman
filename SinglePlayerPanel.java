import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class SinglePlayerGamePanel here.
 *
 * @author Haley Park
 * @version May 15, 2018
 */
public class SinglePlayerPanel extends JPanel {
    private JButton[] buttons;
    private String[] categoryNames = {"Animals", "Computer Science", "Wellesley", "Countries", "Movies", "Random"};

    public SinglePlayerPanel() {
        JPanel panels;

        JPanel chooseCategory = makeChooseCategoryPanel();
        JPanel playGame = makeSinglePlayerGamePanel();
        panels = new JPanel(new CardLayout());
        panels.add(chooseCategory);
        panels.add(playGame);

        add(panels);
    }

    private JPanel makeChooseCategoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 10, 10));
        JLabel statusLabel = new JLabel("Choose a category: ", JLabel.CENTER);
        buttons = new JButton[6];

        panel.add(statusLabel);

        for (int i = 0; i < categoryNames.length; i++) {
            buttons[i] = new JButton(categoryNames[i]);
            buttons[i].addActionListener(new ButtonListener());
            panel.add(buttons[i]);
        }

        return panel;
    }

    private JPanel makeSinglePlayerGamePanel() {
        JPanel panel = new JPanel();
        return panel;
    }

    private class ButtonListener implements ActionListener {
        /** 
         *@param event is the event which causes an action to be performed
         **/
        public void actionPerformed (ActionEvent event) {
            String categoryName = "";
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
            SinglePlayerRound newRound = new SinglePlayerRound(categoryFileName);

            System.out.println("You chose the category of: " + categoryName);
            System.out.println(newRound.game);
            newRound.game.round();
        } 
    }
}