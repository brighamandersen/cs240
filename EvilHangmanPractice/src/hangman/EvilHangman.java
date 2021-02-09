package hangman;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {

    public static void main(String[] args) {
        String FILE_NAME = args[0];
        int WORD_LENGTH = Integer.parseInt(args[1]);
        int guessesLeft = Integer.parseInt(args[2]);

        Set<String> possibleWords = new HashSet<>();

        File dictionary = new File(FILE_NAME);

        EvilHangmanGame evilHangmanGame = new EvilHangmanGame();

        try {
            evilHangmanGame.startGame(dictionary, WORD_LENGTH);
        } catch (IOException | EmptyDictionaryException ex) {
            System.out.println(ex.toString());
            return;
        }

        while (guessesLeft > 0) {
            System.out.println("You have " + guessesLeft + " guesses left");
            System.out.println("Used letters: " + evilHangmanGame.getGuessedLetters());
            System.out.println("Word: " + evilHangmanGame.getLargestSubsetKey());

            String stringGuess;
            char guess;
            boolean isInvalid = false;

            do {
                System.out.print("Enter guess: ");
                Scanner scanner = new Scanner(System.in);
                stringGuess = scanner.next().toLowerCase();
                guess = stringGuess.charAt(0);

                // Check if input is valid
                if (guess < 'a' || guess > 'z' || (stringGuess.length() > 1 && guess != '\n')) {
                    System.out.print("Invalid input! ");
                    isInvalid = true;
                    continue;
                }
                isInvalid = false;

                try {
                    possibleWords = evilHangmanGame.makeGuess(guess);
                } catch (GuessAlreadyMadeException ex) {
                    System.out.print("Guess already made! ");
                    isInvalid = true;
                }
            } while (isInvalid);

            // If bad guess
            if (evilHangmanGame.getTimesFound() == 0) {
                System.out.println("Sorry, there are no " + guess + "\n");
                guessesLeft--;
                continue;
            } else {    // If good guess
                // Check if they won
                if (!evilHangmanGame.getLargestSubsetKey().contains("_")) {
                    System.out.println("You win!  You guessed the word: " + possibleWords.iterator().next());
                    return;
                }
                // If they guessed right but didn't win
                System.out.println("Yes, there is " + evilHangmanGame.getTimesFound() + " " + guess + "\n");
            }
        }
        System.out.println("Sorry, you lost!  The word was " + possibleWords.iterator().next());
    }

}
