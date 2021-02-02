package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman {

    public static void main(String[] args) {
        File DICTIONARY = new File(args[0]);
        int WORD_LENGTH = Integer.parseInt(args[1]);
        int NUM_GUESSES = Integer.parseInt(args[2]);

        // Set up game
        EvilHangmanGame evilHangmanGame = new EvilHangmanGame();

        try {
            evilHangmanGame.startGame(DICTIONARY, WORD_LENGTH);
            evilHangmanGame.setGuessesRemaining(NUM_GUESSES);
        } catch (IOException | EmptyDictionaryException ex) {
            System.out.println(ex.toString());
            return;
        }

        // Play game rounds
        while (evilHangmanGame.getGuessesRemaining() > 0) {

            System.out.println("You have " + evilHangmanGame.getGuessesRemaining() + " guesses left");

            System.out.print("Used letters: ");
            System.out.println(evilHangmanGame.getGuessedLetters());

            System.out.print("Word: ");
            System.out.println(evilHangmanGame.getWordProgress());

            System.out.print("Enter a guess: ");
            Scanner scanner = new Scanner(System.in);
            char guess = scanner.next().charAt(0);

            System.out.println("You guessed " + guess);


            try {
                evilHangmanGame.makeGuess(guess);
            } catch (GuessAlreadyMadeException ex) {
                System.out.println(ex.toString());
            }
            System.out.println();
        }

        // Handle end outcome
        // FIXME - Add logic in case they win
        System.out.println("You lose!");
        // FIXME - Add new correct word
        System.out.println("The word was: FIXME");
    }

}
