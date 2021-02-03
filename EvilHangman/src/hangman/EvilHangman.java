package hangman;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {

    public static void main(String[] args) {
        File DICTIONARY = new File(args[0]);
        int WORD_LENGTH = Integer.parseInt(args[1]);
        int guessesLeft = Integer.parseInt(args[2]);
        Set<String> possibleWords = new HashSet<String>();

        // Set up game
        EvilHangmanGame evilHangmanGame = new EvilHangmanGame();

        try {
            evilHangmanGame.startGame(DICTIONARY, WORD_LENGTH);
        } catch (IOException | EmptyDictionaryException ex) {
            System.out.println(ex.toString());
            return;
        }

        // Play game rounds
        while (guessesLeft > 0) {

            System.out.println("You have " + guessesLeft + " guesses left");

            System.out.print("Used letters: ");
            System.out.println(evilHangmanGame.getGuessedLetters());

            System.out.print("Word: " + evilHangmanGame.getLargestSubsetKey() + "\n");

            System.out.print("Enter a guess: ");
            Scanner scanner = new Scanner(System.in);

            char letterGuessed = scanner.next().toLowerCase().charAt(0);
            System.out.println("You guessed " + letterGuessed);

            // Check if letterGuessed is valid, otherwise loop again
            if (letterGuessed < 'a' || letterGuessed > 'z') {
                System.out.println("Invalid character.  Try again.\n");
                continue;
            }

            try {
                evilHangmanGame.makeGuess(letterGuessed);
            } catch (GuessAlreadyMadeException ex) {
                System.out.println(ex.toString() + "\n");
                continue;
            }

            // If good guess
            if (letterGuessed >= 'a' && letterGuessed <= 'm') {     // FIXME - Switch this logic, just for testing
                System.out.println("Yes, there is {frequency} " + letterGuessed + "\n");

                // Check if user won (word is completely filled out)
//                if (!...contains("_")) {
//                    System.out.println("You win!  The word was FIXME");
//                }

            } else {    // If bad guess (if words in new hangmanDictionary do not contain the guessed letter)
                System.out.println("Sorry, there are no " + letterGuessed + "\'s\n");
                guessesLeft--;
            }

        }

        System.out.println("You lose!\nThe word was: FIXME");
    }

}
