package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman {

    public static void main(String[] args) {
        File dictionary = new File(args[0]);
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);

        IEvilHangmanGame evilHangmanGame = new EvilHangmanGame();

        try {
            evilHangmanGame.startGame(dictionary, wordLength);
        } catch (IOException | EmptyDictionaryException ex) {
            ex.printStackTrace();
        }

        while (guesses > 0) {

            System.out.println("You have " + guesses + " guesses left");

            System.out.print("Used letters: ");
            System.out.println(evilHangmanGame.getGuessedLetters());

            System.out.print("Word: ");
            System.out.println(); // ?????????????

            System.out.print("Enter a guess: ");
            Scanner scanner = new Scanner(System.in);
            char guess = scanner.next().charAt(0);

            System.out.println("You guessed " + guess);


            try {
                evilHangmanGame.makeGuess(guess);
            } catch (GuessAlreadyMadeException ex) {
                ex.printStackTrace();
            }
            guesses--;
        }
        // FIXME - Add logic in case they win
        System.out.println("You lose!");
        // FIXME - Add new correct word
        System.out.println("The word was: FIXME");
    }

}
