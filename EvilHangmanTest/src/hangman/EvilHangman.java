package hangman;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {

    public static void main(String[] args) {
        String FILE_NAME = args[0];
        int wordLength = Integer.parseInt(args[1]);
        int guessesLeft = Integer.parseInt(args[2]);

        File dictionary = new File(FILE_NAME);

        EvilHangmanGame evilHangmanGame = new EvilHangmanGame();
        Set<String> possibleWords = new HashSet<>();

        try {
            evilHangmanGame.startGame(dictionary, wordLength);
        } catch (IOException | EmptyDictionaryException ex) {
            System.out.println(ex.toString());
        }

        while (guessesLeft > 0) {
            System.out.println("\nYou have " + guessesLeft + " guesses left");
            System.out.println("Used letters: " + evilHangmanGame.getGuessedLetters());
            System.out.println("Word: " + evilHangmanGame.getLargestSubsetKey());

            boolean isInvalid;
            String stringGuess;
            char guess;

            do {
                isInvalid = false;

                System.out.print("Enter guess: ");
                Scanner scanner = new Scanner(System.in);
                stringGuess = scanner.next().toLowerCase();
                guess = stringGuess.charAt(0);

                if (guess < 'a' || guess > 'z' || (stringGuess.length() > 1 && guess != '\n')) {
                    isInvalid = true;
                    System.out.print("Invalid input! ");
                    continue;
                }

                try {
                    possibleWords = evilHangmanGame.makeGuess(guess);
                } catch (GuessAlreadyMadeException ex) {
                    isInvalid = true;
                    System.out.print(ex.toString() + " ");
                    continue;
                }

            } while (isInvalid);

            if (evilHangmanGame.getTimesFound() == 0) {
                System.out.println("Sorry, there are no " + guess);
                guessesLeft--;
            } else {
                System.out.println("Yes, there is " + evilHangmanGame.getTimesFound() + " " + guess);

                if (!evilHangmanGame.getLargestSubsetKey().contains("_")) {
                    System.out.println("You win! You guessed the word: " + possibleWords.iterator().next());
                    return;
                }
            }
        }
        System.out.println("Sorry, you lost! The word was: " + possibleWords.iterator().next());
    }

}
