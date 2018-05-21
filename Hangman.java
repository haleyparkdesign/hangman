
/**
 * Hangman is preliminary code that will be added upon as the GUI gets built.
 * It only interacts with the user in the consule. Only has a working multiplayer
 * game.
 *
 * @author Margaret Harrigan
 * @version 5/14/18
 */
import java.util.*;
public class Hangman
{
    /**
     * Constructor for objects of Hangman. Creates a multiplayer game with 4 rounds
     * 
     * @param String name of player1, String name of player2
     */
    public Hangman(String player1, String player2){
        int rounds = 0;
        while(rounds < 4){
            Scanner scan = new Scanner(System.in);
            String currentPlayer;
            String guesser;
            
            //establishes the guesser and the inputter
            if (rounds % 2 == 0) {
                currentPlayer = player1;
                guesser = player2;
            }
            else {
                currentPlayer = player2;
                guesser = player1;
            }
            
            //creates a round based on the word that is entered
            System.out.println(currentPlayer + ", enter the word for" 
                + " your opponent to guess");
            String word = scan.nextLine();
            HangmanRound game = new HangmanRound(word);
            System.out.println(guesser + ", it is your turn to guess.");
            game.round();
            rounds++;
        }
    }

    /**
     * main method for testing
     * 
     * @param String[] args
     */
    public static void main (String[] args){
        Hangman test = new Hangman("one", "two");
    }
}
