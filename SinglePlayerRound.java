import java.util.*;
import java.io.*;

/**
 * Write a description of class SinglePlayerRound here.
 *
 * @author Margaret Harrigan, Haley Park
 * @version (a version number or a date)
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
            Scanner sc = new Scanner(new File(categoryFileName));

            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                word = word.toLowerCase();
                dictionary.add(word);
            }

            randomIndex = (int)(Math.floor(Math.random() * dictionary.size()));
            //System.out.println(randomIndex);
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

    public void makeGuess(char guess) {
        if (!guessed.contains(guess)) {
            if (word.contains(guess)) {
                    System.out.println(guess + " is in the word");
                    count++;
                    guessed.add(guess);
            } else {
                    System.out.println(guess + " is not in the word :(");
                if (wrongGuesses.isEmpty() || guess > (char) wrongGuesses.getLast()) {
                        wrongGuesses.add(guess);
                    }
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
        } else {
                System.out.println("You've already guessed this letter. Try again.");
        }
        
        if (wrongGuesses.size() == numBodyParts) {
            lost = true;
        }
        else if(count == word.size()){
            won = true;
        }
    }
    
    public Vector<Integer> letterFit(char guess) {
        Vector<Integer> correctLetterPos = new Vector<Integer>();
        char[] charArray = original.toCharArray();
        
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == guess)
                correctLetterPos.add(i);
        }
        
        return correctLetterPos;
    }
    
    public boolean didPlayerLose() {
        return lost;
    }
    
    public boolean didPlayerWin() {
        return won;
    }
    
    public boolean isGameEnd() {
        return wrongGuesses.size() >= numBodyParts;
    }

    public String getHint() {
        if (!hint){
            String hintChar = "";
            if (!guessed.contains(original.charAt(0))) {
                hintChar = original.substring(0,1);
                hint = true;
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
        else{
            return "No more hints";
        }
    }

    public LinkedList getWrongGuesses() {
        return wrongGuesses;
    }

    public int getNumWrongGuesses() {
        return wrongGuesses.size();
    }
    
    public int getNumBodyParts(){
        return numBodyParts;
    }
     
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
        spr.game.round();
    }
}
