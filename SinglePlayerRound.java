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
    private String original;
    private HashSet word;
    private LinkedList guessed, wrongGuesses;
    int count;

    /**
     * Constructor for objects of class SinglePlayerRound
     */
    public SinglePlayerRound(String categoryFileName) {
        int randomIndex;
        dictionary = new Vector<String>();
        word = new HashSet();
        guessed = new LinkedList();
        wrongGuesses = new LinkedList();
        count = 0;

        try {
            Scanner sc = new Scanner(new File(categoryFileName));

            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                word = word.toLowerCase();
                dictionary.add(word);
            }

            randomIndex = (int)(Math.round(Math.random() * dictionary.size()));

            //randomly chooses letter from the category
            original = dictionary.get(randomIndex);
            for (char c : original.toCharArray()){
                word.add(c);
            }

            game = new HangmanRound(original);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void makeGuess(char guess) {
        if (count < word.size()){
            if (!guessed.contains(guess)) {
                if (word.contains(guess)) {
                    System.out.println(guess + " is in the word");
                    count++;
                    guessed.add(guess);
                } else {
                    System.out.println(guess + " is not in the word :(");
                    wrongGuesses.add(guess);
                    guessed.add(guess);
                }
            } else {
                System.out.println("You've already guessed this letter. Try again.");
            }
        } else {
            System.out.println("The word was " + original + ". It took you " + guessed.size()
                + " guesses. These are the letters you guessed: " + guessed);
        }
    }

    public String getHint() {
        String hintChar = "";
        if (!guessed.contains(original.charAt(0))) {
            hintChar = original.substring(0,1);
            return "Starts with: " + hintChar;
        } else {
            for (int i = 1; i < original.length(); i++) {
                if (!guessed.contains(original.charAt(i))) {
                    hintChar = original.substring(i,i+1);
                    break;
                } 
            }
        }

        return "Contains letter " + hintChar;
    }

    public String getWrongGuesses() {
        return wrongGuesses.toString();
    }

    public int getNumWrongGuesses() {
        return wrongGuesses.size();
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
