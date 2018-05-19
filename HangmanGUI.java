import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class MultiPlayerGamePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class HangmanGUI extends JPanel {
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new HangmanGUI(),BorderLayout.CENTER);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }
}