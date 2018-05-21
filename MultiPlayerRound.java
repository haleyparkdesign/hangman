import java.util.*;
import java.io.*;

/**
 * @author (your name)
 * @version (a version number or a date)
 */
public class MultiPlayerRound {
    private Vector<String> dictionary;
    private boolean player1Win;
    private boolean player2Win;
    private HashSet word1Set;
    private HashSet word2Set;
    public LinkedList guessed1, guessed2, wrongGuesses1, wrongGuesses2;
    int user1Points;
    int user2Points;
    String word1;
    String word2;
    int currentPlayer;
    static int count1 =0 ;
    static int count2 = 0;
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


        for (int i = 0; i < word1.length(); i++) {
            word1Set.add(word1.charAt(i));
        }
        
        for (int i = 0; i < word2.length(); i++) {
            word2Set.add(word2.charAt(i));
        }

        
    }

    public void makeGuess1(char guess) {
        if (!player1Win && !player2Win ){
            if (!guessed1.contains(guess)) {
                if (word1.indexOf(guess) >= 0) {
                    System.out.println(count1);
                    System.out.println(guess + " is in the word");
                    count1++;          
                    System.out.println(count1);
                    guessed1.add(guess);
                } else {
                    System.out.println(guess + " is not in the word :(");
                    wrongGuesses1.add(guess);
                    guessed1.add(guess);
                }
            } else {
                System.out.println("You've already guessed this letter. Try again.");
            }
        } else {
            System.out.println("The word was " + word1 + ". It took Player1 " + guessed1.size()
                + " guesses. These are the letters you guessed: " + guessed1);
        }
        
        System.out.println("count1 so far " + count1 + "setSize2 " + word1Set.size());
        System.out.println("count2 so far " + count2 + "setSize2 " + word2Set.size());
       
        if (wrongGuesses2.size() == 9 || count1 == word1Set.size()) {
            player1Win = true;
            player2Win = false;
            System.out.println("player 1 won");
            
        }

        if (wrongGuesses2.size() == 9 || count2 == word2Set.size()) {
            player1Win = true;
            player2Win = false;
            System.out.println("player 2 won");
        }
    }
    
    public void makeGuess2(char guess) {
        if (wrongGuesses2.size() <= 9 ){
            if (!guessed2.contains(guess)) {
                if (word2.indexOf(guess) >= 0) {
                    System.out.println(guess + " is in the word");
                    count2++;
                    guessed2.add(guess);
                } else {
                    System.out.println(guess + " is not in the word :(");
                    wrongGuesses2.add(guess);
                    guessed2.add(guess);
                }
            } else {
                System.out.println("You've already guessed this letter. Try again.");
            }
        } else {
            System.out.println("The word was " + word2 + ". It took Player2 " + guessed2.size()
                + " guesses. These are the letters you guessed: " + guessed2);
        }
        
        System.out.println("count1 so far " + count1 + "setSize2 " + word1Set.size());
        System.out.println("count2 so far " + count2 + "setSize2 " + word2Set.size());
       
        if (wrongGuesses2.size() == 9 || count1 == word1Set.size()) {
            player2Win = true;
            player1Win = false;
            System.out.println("player 1 won");
            
        }
        
        if (wrongGuesses2.size() == 9 || count2 == word2Set.size()) {
            player2Win = true;
            player1Win = false;
            System.out.println("player 2 won");
        }
    }
    

    public String getHint1() {
        if (!hint1){
            String hintChar = "";
            if (!guessed1.contains(word1.charAt(0))) {
                hintChar = word1.substring(0,1);
                hint1 = true;
                return "Starts with: " + hintChar;
            } else {
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

    public Vector<Integer> letterFit1(char guess) {
        Vector<Integer> correctLetterPos = new Vector<Integer>();
        char[] charArray = word1.toCharArray();
        
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == guess)
                correctLetterPos.add(i);
        }
        
        return correctLetterPos;
    }
    
    
    public Vector<Integer> letterFit2(char guess) {
        Vector<Integer> correctLetterPos = new Vector<Integer>();
        char[] charArray = word2.toCharArray();
        
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == guess)
                correctLetterPos.add(i);
        }
        
        return correctLetterPos;
    }
    
    public String getHint2() {
        if (!hint2){
            String hintChar = "";
            if (!guessed2.contains(word2.charAt(0))) {
                hintChar = word2.substring(0,1);
                hint2 = true;
                return "Starts with: " + hintChar;
            } else {
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
    public String getWord1() {
        return this.word1;
    }
    
    public String getWord2() {
        return this.word2;
    }
    
    public LinkedList<Character> getWrongGuesses1() {
        return wrongGuesses1;
    }
   
    public LinkedList<Character> getWrongGuesses2() {
        return wrongGuesses2;
    }

    public int getNumWrongGuesses1() {
        return wrongGuesses1.size();
    }

    public int getNumWrongGuesses2() {
        return wrongGuesses2.size();
    }
    
    public boolean player1Win() {
        return player1Win;
    }
    
    public boolean player2Win() {
        return player2Win;
    }

    /**
     * Tests the class
     */
    public static void main(String[] args) {
        //needs to be filled 
    }
}
