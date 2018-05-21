import java.util.*;
import java.io.*;

/**
 * Write a description of class SinglePlayerRound here.
 *
 * @author Margaret Harrigan, Haley Park
 * @version 5/15/18
 */
public class SinglePlayerRound {
    private Vector<String> dictionary;
    public HangmanRound game;
    private String original;
    private HashSet word;
    private LinkedList guessed, wrongGuesses;
    final int numBodyParts = 9;
    private int count;
    boolean won, lost, hint;

    /**
     * Constructor for objects of class SinglePlayerRound
     * 
     * @param String categoryFileName input file
     */
    public SinglePlayerRound(String categoryFileName) {
        int randomIndex;
        dictionary = new Vector<String>();
        word = new HashSet();
        guessed = new LinkedList();
        wrongGuesses = new LinkedList();
        count = 0;
        hint = false;

        try {
            //get the words from the category.
            Scanner sc = new Scanner(new File(categoryFileName));

            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                word = word.toLowerCase();
                dictionary.add(word);
            }

            randomIndex = (int)(Math.floor(Math.random() * dictionary.size()));

            //randomly chooses a word from the category
            original = dictionary.get(randomIndex);
            for (char c : original.toCharArray()){
                word.add(c);
            }

            game = new HangmanRound(original);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * makeGuess takes in a char and sees if it's in the word
     * 
     * @param char guess
     */
    public void makeGuess(char guess) {
        //if the letter hasn't been guessed yet
        if (!guessed.contains(guess)) {
            //if the letter is in the word
            if (word.contains(guess)) {
                System.out.println(guess + " is in the word");
                count++;
                guessed.add(guess);
            } 
            //if the letter isn't in the word
            else {
                System.out.println(guess + " is not in the word :(");
                //add it to the wrong guesses linked list if it's the first
                //letter alphabetically or the first guess
                if (wrongGuesses.isEmpty() || guess > (char) wrongGuesses.getLast()) {
                        wrongGuesses.add(guess);
                }
                //add it to the linked list where it belongs alphabetically 
                else{
                    for (int i = 0; i < wrongGuesses.size(); i++){
                        if (guess < (char) wrongGuesses.get(i)){
                                wrongGuesses.add(i, guess);
                                break;
                        }
                    }
                }
                guessed.add(guess);
            }
        } 
        //the user has already guessed this letter.
        else {
            System.out.println("You've already guessed this letter. Try again.");
        }
        
        //check to see if the game is over
        if (wrongGuesses.size() == numBodyParts) {
            lost = true;
        }
        else if(count == word.size()){
            won = true;
        }
    }
    
    /**
     * letterFit takes in a char and sees where it appears in the original word
     * 
     * @param char guess
     * @return Vector<Integer>
     */
    public Vector<Integer> letterFit(char guess) {
        Vector<Integer> correctLetterPos = new Vector<Integer>();
        char[] charArray = original.toCharArray();
        
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == guess)
                correctLetterPos.add(i);
        }
        
        return correctLetterPos;
    }
    
    /**
     * didPlayerLose returns the lost boolean. True if they did lose. False if not
     * 
     * @return boolean lost
     */
    public boolean didPlayerLose() {
        return lost;
    }
    
    /**
     * didPlayerWin returns the won boolean. True if they did win. False if not.
     * 
     * @return boolean lost
     */
    public boolean didPlayerWin() {
        return won;
    }
    
    /**
     * isGameEnd checks to see if the user has taken all their guesses
     * 
     * @return boolean true if game over, false if not
     */
    public boolean isGameEnd() {
        return wrongGuesses.size() >= numBodyParts;
    }

    /**
     * getHint gives the user a hint of a letter they haven't guessed yet
     * 
     * @return String
     */
    public String getHint() {
        //if they haven't used their hint yet
        if (!hint){
            String hintChar = "";
            //if they haven't guessed the first letter yet
            if (!guessed.contains(original.charAt(0))) {
                hintChar = original.substring(0,1);
                hint = true;
                return "Starts with: " + hintChar;
            } 
            //gives a letter in the word they haven't guessed 
            else {
                for (int i = 1; i < original.length(); i++) {
                    if (!guessed.contains(original.charAt(i))) {
                        hintChar = original.substring(i,i+1);
                        break;
                    } 
                }
            }
            return "Contains letter " + hintChar;
        }
        else{
            return "No more hints";
        }
    }

    /**
     * getWrongGuesses returns all the wrong guesses
     * 
     * @return LinkedList
     */
    public LinkedList getWrongGuesses() {
        return wrongGuesses;
    }

    /**
     * getNumWrongGuesses gets the number of wrong guesses made.
     * 
     * @return int
     */
    public int getNumWrongGuesses() {
        return wrongGuesses.size();
    }
    
    /**
     * getNumBodyParts returns the number of guesses the user gets
     * 
     * @return int
     */
    public int getNumBodyParts(){
        return numBodyParts;
    }
    
    /**
     * getOriginal returns the original word
     * 
     * @return String
     */
    public String getOriginal() {
        return this.original;
    }

    /**
     * Tests the class
     */
    public static void main(String[] args) {
        SinglePlayerRound spr = new SinglePlayerRound("./texts/wellesley.txt");
        System.out.println(spr.word);
        System.out.println(spr.game);
    }
}
