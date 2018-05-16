import java.util.*;
import java.io.*;

/**
 * Write a description of class SinglePlayerRound here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SinglePlayerRound {
    private Vector<String> dictionary;
    public HangmanRound game;

    /**
     * Constructor for objects of class SinglePlayerRound
     */
    public SinglePlayerRound(String categoryFileName) {
        int randomIndex;
        dictionary = new Vector<String>();
        
        try {
            Scanner sc = new Scanner(new File(categoryFileName));
            
            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                word = word.toLowerCase();
                dictionary.add(word);
            }
            
            randomIndex = (int)(Math.round(Math.random() * dictionary.size()));
            
            game = new HangmanRound(dictionary.get(randomIndex));
            
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Tests the class
     */
    public static void main(String[] args) {
        SinglePlayerRound spr = new SinglePlayerRound("wellesley.txt");
        System.out.println(spr.game);
        spr.game.round();
    }
}
