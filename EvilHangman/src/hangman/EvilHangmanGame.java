package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    SortedSet<Character> guessedLetters;
    Set<String> hangmanDictionary;

    public EvilHangmanGame() {
        guessedLetters = new TreeSet<Character>();
        hangmanDictionary = new HashSet<String>();
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        if (dictionary == null) {
            throw new IOException("Dictionary does not exist.");
        }

        if (dictionary.length() == 0) {
            throw new EmptyDictionaryException("Dictionary does not contain any words.");
        }

        Scanner scanner = new Scanner(dictionary);
        String curWord;

        while (scanner.hasNext()) {
            curWord = scanner.next().toLowerCase();

            if(curWord.length() == wordLength) {
                hangmanDictionary.add(curWord);
            }
        }
        scanner.close();

        if (hangmanDictionary.size() == 0) {
            throw new EmptyDictionaryException("No words in dictionary are that length.");
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

        if (guessedLetters.contains(guess)) {
            throw new GuessAlreadyMadeException("You already guessed that letter.");
        }

        guessedLetters.add(guess);

        // If exists
        System.out.println("Yes, there is {frequency} " + guess);
//        else {
            System.out.println("Sorry, there are no " + guess + "\'s");
//        }
        System.out.println();

        return null;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }
}
