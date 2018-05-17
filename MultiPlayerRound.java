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
    private String original;
    private HashSet word1Set;
    private HashSet word2Set;
    private LinkedList guessed1, guessed2, wrongGuesses1, wrongGuesses2;
    int user1Points;
    int user2Points;
    String word1;
    String word2;
    int currentPlayer;

    /**
     * Constructor for objects of class SinglePlayerRound
     */
    public MultiPlayerRound () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Player 1 please enter a word.");
        word1 = sc.nextLine();
        System.out.println("Player 2 please enter a word.");
        word2 = sc.nextLine();
        int randomIndex;
        currentPlayer = 0;
        dictionary = new Vector<String>();
        word1Set = new HashSet();
        word2Set = new HashSet();
        guessed1 = new LinkedList();
        guessed2 = new LinkedList();
        wrongGuesses1 = new LinkedList();
        wrongGuesses2 = new LinkedList();

        for (int i = 0; i < word1.length(); i++) {
            word1Set.add(word1.charAt(i));
        }
        
        for (int i = 0; i < word2.length(); i++) {
            word2Set.add(word1.charAt(i));
        }

        
    }

    public void makeGuess() {
        int count1 = 0;
        int count2 = 0;
        Scanner scan = new Scanner(System.in); 
        while (!player1Win && !player2Win) {
            //player 1
            if (currentPlayer % 2  == 0 ) {
                currentPlayer ++;
                System.out.println("Player 1: Enter a guess");
                char letter = scan.next().charAt(0);
                //if the user hasn't guessed this letter
                if (!guessed1.contains(letter)) {
                    if (word1Set.contains(letter)) {
                        System.out.println(letter + " is in the word");
                        count1++;
                        guessed1.add(letter);
                    } else {
                        System.out.println(letter + " is not in the word :(");
                        wrongGuesses1.add(letter);
                        guessed1.add(letter);
                    }
                }
                
                else {
                    System.out.println("You've already guessed this letter. Try again.");
                    }
                    
                if (wrongGuesses1.size() == 9 ) {
                    player1Win = false;
                    player2Win = true;
                }
                if (count1 == word1Set.size()) {
                    player1Win = true;
                    player2Win = false;
                }
                
            }
            else if (currentPlayer % 2  != 0 ) {
                currentPlayer ++;
                System.out.println("Player 2: Enter a guess");
                char letter = scan.next().charAt(0);
                //if the user hasn't guessed this letter
                if (!guessed2.contains(letter)) {
                    if (word2Set.contains(letter)) {
                        System.out.println(letter + " is in the word");
                        guessed2.add(letter);
                        count2 ++;
                    } else {
                        System.out.println(letter + " is not in the word :(");
                        wrongGuesses2.add(letter);
                        guessed2.add(letter);
                    }
                }
                
                else {
                    System.out.println("You've already guessed this letter. Try again.");
                    }
                if (wrongGuesses2.size() == 9 ) {
                    player1Win = true;
                    player2Win = false;
                }
                if (count2 == word2Set.size()) {
                    player1Win = false;
                    player2Win = true;
                }
            }      
        }
        
        if (player1Win) {
            System.out.println("Player 1 correctly guessed the word " + word1 + ". It took Player1 " + guessed1.size() + " guesses. ");
        }
        else {
            System.out.println("Player 2 correctly guessed the word " + word1 + ". It took Player2 " + guessed2.size() + " guesses. ");
        }
        }
   
    public String getHint() {
        String hintChar = "";
        if (!guessed1.contains(word1.charAt(0))) {
            hintChar = original.substring(0,1);
            return "Starts with: " + hintChar;
        } else {
            for (int i = 1; i < original.length(); i++) {
                if (!guessed1.contains(word1.charAt(i))) {
                    hintChar = original.substring(i,i+1);
                    break;
                } 
            }
        }

        return "Contains letter " + hintChar;
    }

    public String getWrongGuesses1() {
        return wrongGuesses1.toString();
    }
   
    public String getWrongGuesses2() {
        return wrongGuesses2.toString();
    }

    public int getNumWrongGuesses1() {
        return wrongGuesses1.size();
    }

    public int getNumWrongGuesses2() {
        return wrongGuesses2.size();
    }
    
    /**
     * Tests the class
     */
    public static void main(String[] args) {
        MultiPlayerRound spr = new MultiPlayerRound();
        spr.makeGuess(); 
    }
}
