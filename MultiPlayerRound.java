import java.util.*;
import java.io.*;

/**
 * MultiPlayerRound is the backend code for the MultiPlayer round of the game. 
 * @author Denise Chai, Margaret Harrigan
 * @version 20 May 2018
 */
public class MultiPlayerRound {
    private Vector<String> dictionary;
    private boolean player1Win;
    private boolean player1Over;
    private boolean player2Over;
    private boolean player2Win;
    private HashSet word1Set;
    private HashSet word2Set;
    public LinkedList guessed1, guessed2, wrongGuesses1, wrongGuesses2;
    String word1;
    String word2;
    int currentPlayer;
    private int count1 =0 ;
    private int count2 = 0;
    private boolean hint1, hint2;

    /**
     * Constructor for objects of class SinglePlayerRound
     */
    public MultiPlayerRound (String w1, String w2) {
        this.word1 = w1;
        this.word2 = w2;
        int randomIndex;
        currentPlayer = 0;
        dictionary = new Vector<String>();
        word1Set = new HashSet();
        word2Set = new HashSet();
        guessed1 = new LinkedList();
        guessed2 = new LinkedList();
        wrongGuesses1 = new <Character> LinkedList();
        wrongGuesses2 = new <Character> LinkedList();
        hint1 = false;
        hint2 = false;
        player1Over = false;
        player2Over = false;

        //creates hashSet() based on word1
        for (int i = 0; i < word1.length(); i++) {
            word1Set.add(word1.charAt(i));
        }
        
        //creates hashSet() based on word2
        for (int i = 0; i < word2.length(); i++) {
            word2Set.add(word2.charAt(i));
        }       
    }

    /**
     * Allows user 1 to make a guess
     * @Parameter     guess     the character the user 1 is guessing and inputting into the textfield
     */
    public void makeGuess1(char guess) {
        if (!player1Win && !player2Win ){
            if (!guessed1.contains(guess)) {
                if (word1.indexOf(guess) >= 0) {
                    // if the letter is correct and hasn't been guessed before, add it to guessed and increment count1
                    System.out.println(count1);
                    System.out.println(guess + " is in the word");
                    count1++;          
                    System.out.println(count1);
                    guessed1.add(guess); 
                } else {
                    //if the letter is not correct and hasn't been guessed before, add the letter to wrongGuesses1 and guessed1
                    System.out.println(guess + " is not in the word :(");
                    //add it to the wrong guesses linked list if it's the first
                    //letter alphabetically or the first guess
                    if (wrongGuesses1.isEmpty() || guess > (char) wrongGuesses1.getLast()) {
                            wrongGuesses1.add(guess);
                    }
                    //add it to the linked list where it belongs alphabetically 
                    else{
                        for (int i = 0; i < guessed1.size(); i++){
                            if (guess < (char) wrongGuesses1.get(i)){
                                    wrongGuesses1.add(i, guess);
                                    break;
                            }
                        }
                    }
                    guessed1.add(guess);
                }

            } else {
                System.out.println("You've already guessed this letter. Try again.");
            }
        } else {
            System.out.println("The word was " + word1 + ". It took Player1 " + guessed1.size()
                + " guesses. These are the letters you guessed: " + guessed1);
        }
        
        //updates on the status of player1's game once he/she wins/loses
        if (wrongGuesses1.size() == 9 || count1 == word1Set.size()) {
            player1Over = true;
            if (wrongGuesses1.size() == 9) {
                player1Win = false;            
            }
            else {
                player1Win = true;
            }
        }

    }

    /**
     * Allows user 2 to make a guess
     * @Parameter     guess     the character user 2 is guessing and inputting into the textfield
     */
    public void makeGuess2(char guess) {
        if (wrongGuesses2.size() <= 9 ){
            if (!guessed2.contains(guess)) {
                if (word2.indexOf(guess) >= 0) {
                    // if the letter is correct and hasn't been guessed before, add it to guessed and increment count2
                    System.out.println(guess + " is in the word");
                    count2++;
                    guessed2.add(guess);
                } else {
                    //if the letter is not correct and hasn't been guessed before, add the letter to wrongGuesses2 and guessed2
                    System.out.println(guess + " is not in the word :(");
                    //add it to the wrong guesses linked list if it's the first
                    //letter alphabetically or the first guess                    
                    if (wrongGuesses2.isEmpty() || guess > (char) wrongGuesses2.getLast()) {
                            wrongGuesses2.add(guess);
                    }
                    //add it to the linked list where it belongs alphabetically 
                    else{
                        for (int i = 0; i < guessed1.size(); i++){
                            if (guess < (char) wrongGuesses2.get(i)){
                                    wrongGuesses2.add(i, guess);
                                    break;
                            }
                        }
                    }
                    guessed2.add(guess);
                }
            } else {
                System.out.println("You've already guessed this letter. Try again.");
            }
        } else {
            System.out.println("The word was " + word2 + ". It took Player2 " + guessed2.size()
                + " guesses. These are the letters you guessed: " + guessed2);
        }
        
        //updates on the status of player2's game once he/she wins/loses
        if (wrongGuesses2.size() == 9 || count2 == word2Set.size()) {
            player2Over = true;
            System.out.println("player2 over");
            if (wrongGuesses2.size() == 9) {
                player2Win = false;            
            }
            else {
                player2Win = true;
            }
        }
    }
    
    /**
     * Provides a limit of 1 hint for the first user
     * @return    if it's the first time the user is accessing the hint button, this will provide the first letter not guessed yet in the word.
     *            if it's other than the first time the user is accessing hint, the string will say "No more hints"
     */
    public String getHint1() {
        //if they haven't guessed the first letter yet
        if (!hint1){
            String hintChar = "";
            //if they haven't guessed the first letter yet
            if (!guessed1.contains(word1.charAt(0))) {
                hintChar = word1.substring(0,1);
                hint1 = true;
                return "Starts with: " + hintChar;
            } 
            //gives a letter in the word they haven't guessed 
            else {
                for (int i = 1; i < word1.length(); i++) {
                    if (!guessed1.contains(word1.charAt(i))) {
                        hintChar = word1.substring(i,i+1);
                        hint1 = true;
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
     * Provides a limit of 1 hint for the second user
     * @return    if it's the first time the user is accessing the hint button, this will provide the first letter not guessed yet in the word.
     *            if it's other than the first time the user is accessing hint, the string will say "No more hints"
     */  
    public String getHint2() {
        //if they haven't guessed the first letter yet
        if (!hint2){
            String hintChar = "";
            //if they haven't guessed the first letter yet
            if (!guessed2.contains(word2.charAt(0))) {
                hintChar = word2.substring(0,1);
                hint2 = true;
                return "Starts with: " + hintChar;
            } 
            //gives a letter in the word they haven't guessed 
            else {
                for (int i = 1; i < word2.length(); i++) {
                    if (!guessed2.contains(word1.charAt(i))) {
                        hintChar = word2.substring(i,i+1);
                        hint2 = true;
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
     * Places the character guess into the next empty blank grid in the game interface for the first user
     * @Param     guess    the new correct letter guessed by the first user that will appear in the correct
     *                     positions in the blank grid
     * @Return             a vector of integers that provide the index for the correct position of the guessed
     *                     character that will appear in the blank grid 
     */
    public Vector<Integer> letterFit1(char guess) {
        Vector<Integer> correctLetterPos = new Vector<Integer>();
        char[] charArray = word1.toCharArray();        
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == guess)
                correctLetterPos.add(i);
        }       
        return correctLetterPos;
    }

    /**
     * Places the character guess into the next empty blank grid in the game interface for the second user
     * @Param    guess     the new correct letter guessed by the second user that will appear in the correct
     *                     positions in the blank grid
     * @Return             a vector of integers that provide the index for the correct position of the guessed
     *                     character that will appear in the blank grid 
     */  
    public Vector<Integer> letterFit2(char guess) {
        Vector<Integer> correctLetterPos = new Vector<Integer>();
        char[] charArray = word2.toCharArray();
        
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == guess)
                correctLetterPos.add(i);
        }        
        return correctLetterPos;
    }
        
    /**
     * provides the LinkedList that contains all the wrong character guessed by the first user
     * @return     the LinkedList that contains all the wrong characters guessed by the first user
     */
    public LinkedList<Character> getWrongGuesses1() {
        return wrongGuesses1;
    }

    /**
     * provides the LinkedList that contains all the wrong character guessed by the second user
     * @return     the LinkedList that contains all the wrong characters guessed by the second user
     */
    public LinkedList<Character> getWrongGuesses2() {
        return wrongGuesses2;
    }

    /**
     * provides the LinkedList that contains all the wrong character guessed by the first user
     * @return     the integer that represents the numeber of wrong characters guessed by the first user
     */
    public int getNumWrongGuesses1() {
        return wrongGuesses1.size();
    }

    /**
     * provides the LinkedList that contains all the wrong character guessed by the second user
     * @return     the integer that represents the numeber of wrong characters guessed by the second user
     */    
    public int getNumWrongGuesses2() {
        return wrongGuesses2.size();
    }

    /**
     * provides the boolean that indicates whether or not the first user has won
     * @return     the boolean that indicates whether the first player has won or not
     */   
    public boolean player1Win() {
        return player1Win;
    }

    /**
     * provides the boolean that indicates whether or not the second user has won
     * @return     the boolean that indicates whether the second player has won or not
     */ 
    public boolean player2Win() {
        return player2Win;
    }
    
    /**
     * provides the boolean that indicates whether or not the first user has finished his/her side of the game
     * @return     the boolean that indicates whether the first player has finished the game
     */   
    public boolean getPlayer1Over() {
        return player1Over;
    }

    /**
     * provides the boolean that indicates whether or not the second user has finished his/her side of the game
     * @return     the boolean that indicates whether the second player has finished the game
     */ 
    public boolean getPlayer2Over() {
        return player2Over;
    }    
    
}