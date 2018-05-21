import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI & driver for the whole program. 
 *
 * @author Haley Park, Margaret Harrigan
 * @version May 19, 2018
 */

public class HangmanGUI extends JPanel {
    /**
     * Constructor for objects of HangmanGUI
     */
    public HangmanGUI() {
        JTabbedPane hangmanGUI = new JTabbedPane();
        JPanel singlePlayerTab = new SinglePlayerPanel();
        hangmanGUI.addTab("Single Player", singlePlayerTab);
        hangmanGUI.setSelectedIndex(0);
        JPanel multiPlayerTab = new MultiPlayerPanel();
        hangmanGUI.addTab("Multiplayer", multiPlayerTab);

        // Add the tabbed pane to this panel.
        setLayout(new GridLayout(1, 1));
        add(hangmanGUI);
    }

    /**
     * main method for testing purposes
     * 
     * @param String[] args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new HangmanGUI(),BorderLayout.CENTER);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }
}