package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    SortedSet<Character> guessedLetters;
    Set<String> hangmanDictionary;
    int guessesRemaining;
    // add map for partitions

    public EvilHangmanGame() {
        guessedLetters = new TreeSet<Character>();
        hangmanDictionary = new HashSet<String>();
        guessesRemaining = 0;
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

        // If good guess
        if (guess >= 'a' && guess <= 'm') {     // FIXME - Switch this logic, just for testing
            System.out.println("Yes, there is {frequency} " + guess);
        } else {    // If bad guess
            guessedLetters.add(guess);
            guessesRemaining--;
            System.out.println("Sorry, there are no " + guess + "\'s");
        }



        // set dictionary value equal to largest partition in map

        return null;    //
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public int getGuessesRemaining() {
        return guessesRemaining;
    }

    public void setGuessesRemaining(int guessesRemaining) {
        this.guessesRemaining = guessesRemaining;
    }

    public String getWordProgress() {
        // FIXME - Replace this so that it returns the key value within the map for the largest partition
        return "_FIX_ME_";
    }
}