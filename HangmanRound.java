
/**
 * HangmanRound is the backend code for one round of hangman. It only interacts with
 * the user in the consule. There is not limit to the number of guesses the user gets.
 * This is preliminary code that will be added upon as the GUI gets built.
 *
 * @author Margaret Harrigan
 * @version 5/14/18
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
     * 
     * @param String word
     */
    public HangmanRound(String w)
    {
        // initialise instance variables
        original = w;
        
        //turn the word into a hashSet of each char
        word = new HashSet();
        for (char c : w.toCharArray()){
            word.add(c);
        }

        guessed = new LinkedList();
    }

    /**
     * toString formats the hashSet
     * 
     * @reutrn String
     */
    public String toString(){
        String s = "";
        for (Object c : word){
            s += c + " ";
        }
        return s;
    }

    /**
     * round takes guesses from the user.
     */
    public void round(){
        Scanner scan = new Scanner(System.in);
        int count = 0;

        while (count < word.size()){
            //get the guess from the user
            System.out.println("Enter a guess");
            char guess = scan.next().charAt(0);
            
            //if the letter hasn't been guessed already
            if (!guessed.contains(guess)){
                //checks if the letter is/isn't in the word
                if (word.contains(guess)){
                    System.out.println(guess + " is in the word");
                    count++;
                }
                else{
                    System.out.println(guess + " is not in the word :(");
                }
                
                //adds the letter to the guessed linkedList in alphabetical order
                if (guessed.isEmpty() || guess > (char) guessed.getLast()) 
                {
                    guessed.add(guess);
                }
                else{
                    for (int i = 0; i < guessed.size(); i++){
                        if (guess < (char) guessed.get(i)){
                            guessed.add(i, guess);
                            break;
                        }
                    }
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

    /**
     * main method for testing
     * 
     * @param String[] args
     */
    public static void main (String[] args){
        HangmanRound test = new HangmanRound("yellow");
        System.out.println(test);
        test.round();
    }
}