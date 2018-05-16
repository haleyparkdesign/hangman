
/**
 * Write a description of class Hangman here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Hangman
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Hangman
     */
    public Hangman(String category)
    {
        // initialise instance variables
        //Scanner reader = new Scanner(new File(category));

    }

    public Hangman(String player1, String player2){
        int rounds = 0;
        while(rounds < 2){
            Scanner scan = new Scanner(System.in);
            String currentPlayer;
            String guesser;
            if (rounds % 2 == 0) {
                currentPlayer = player1;
                guesser = player2;
            }
            else {
                currentPlayer = player2;
                guesser = player1;
            }
            System.out.println(currentPlayer + ", enter the word for" 
                + " your opponent to guess");
            String word = scan.nextLine();
            HangmanRound game = new HangmanRound(word);
            System.out.println(guesser + ", it is your turn to guess.");
            game.round();
            rounds++;
        }
    }

    public static void main (String[] args){
        Hangman test = new Hangman("one", "two");

    }
}
