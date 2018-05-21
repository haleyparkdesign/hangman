import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class MultiPlayerGamePanel here.
 * GUI & driver for the whole program. 
 *
 * @author (your name)
 * @version (a version number or a date)
 * @author Haley Park (add your names as you modify the class!)
 * @version May 19, 2018
 */

public class HangmanGUI extends JPanel {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new HangmanGUI(),BorderLayout.CENTER);
        frame.setSize(1000, 600);
        frame.setSize(800, 400);
        frame.setVisible(true);
}

