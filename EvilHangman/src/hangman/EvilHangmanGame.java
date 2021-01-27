package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class EvilHangmanGame implements IEvilHangmanGame {
    SortedSet<Character> guessedLetters;

    public EvilHangmanGame() {
        guessedLetters = new TreeSet<Character>();
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {

    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

        guessedLetters.add(guess);
        guessedLetters.add('a');
        guessedLetters.add('z');

        return null;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }
}
