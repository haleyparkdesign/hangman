
/**
 * Write a description of class HangmanRound here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

public class HangmanRound
{
    // instance variables
    private String original;
    private HashSet word;
    private LinkedList guessed;

    /**
     * Constructor for objects of class HangmanRound
     * Multiplayer - word is inputted by the user and passed to the round
     * SinglePlayer - word is chosen from a dictionary
     */
    public HangmanRound(String w)
    {
        // initialise instance variables
        original = w;
        word = new HashSet();
        for (char c : w.toCharArray()){
            word.add(c);
        }

        guessed = new LinkedList();
    }

    public String toString(){
        String s = "";
        for (Object c : word){
            s += c + " ";
        }
        return s;
    }

    public void round(){
        Scanner scan = new Scanner(System.in);
        int count = 0;

        while (count < word.size()){
            System.out.println("Enter a guess");
            char guess = scan.next().charAt(0);
            if (!guessed.contains(guess)){
                if (word.contains(guess)){
                    System.out.println(guess + " is in the word");
                    count++;
                    guessed.add(guess);
                }
                else{
                    System.out.println(guess + " is not in the word :(");
                    guessed.add(guess);
                }
            }
            else{
                System.out.println("You've already guessed this letter. Try again.");
            }
        }
        scan.close();
        System.out.println("The word was " + original + ". It took you " + guessed.size()
            + " guesses. These are the letters you guessed: " + guessed);
    }

    public static void main (String[] args){
        HangmanRound test = new HangmanRound("yellow");
        System.out.println(test);
        test.round();
    }
}