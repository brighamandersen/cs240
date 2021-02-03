package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    SortedSet<Character> guessedLetters;
    Set<String> hangmanDictionary;
    Map<String, Set<String>> partitionMap;
    String largestSubsetKey;

    public EvilHangmanGame() {
        guessedLetters = new TreeSet<Character>();
        hangmanDictionary = new HashSet<String>();
        partitionMap = new HashMap<String, Set<String>>();
        largestSubsetKey = "";
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

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            sb.insert(i, '_');
        }
        largestSubsetKey = sb.toString();
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        if (guessedLetters.contains(guess)) {
            throw new GuessAlreadyMadeException("You already guessed that letter.");
        }
        guessedLetters.add(guess);

        // Program partitions hangmanDictionary relative to guessed letter
        partitionDictionary(guess);
        // Largest subset in the partition becomes the new hangmanDictionary
            // hangmanDictionary = largestSubset;
        // If words in the hangmanDictionary contain the guessed letter,
            // display each occurrence of the letter in the word.

        return hangmanDictionary;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void partitionDictionary(char guess) {   // Creates subsets based on current dictionary and guess
        // Loop through letters in word
            // Loop through hangmanDictionary

                // If hangmanDictionary at index contains char, add subset to partitionMap
        Set<String> set = new HashSet<>(Arrays.asList("tar", "tap", "tan"));

        partitionMap.put("t__", set);
    }

    public String getLargestSubsetKey() {
        // FIXME - Replace this so that it returns the key value within the map for the largest partition

        Set<String> set1 = new HashSet<>(Arrays.asList("tar", "tap", "tan"));
        Set<String> set2 = new HashSet<>(Arrays.asList("bat", "cat"));
        partitionMap.put("t__", set1);
        partitionMap.put("__t", set2);

        int largestSize = 0;
        for (Map.Entry<String, Set<String>> subset : partitionMap.entrySet()) {
//            System.out.println(subset.getKey() + "\t\t" + subset.getValue());

            if (subset.getValue().size() > largestSize) {
                largestSubsetKey = subset.getKey();
                largestSize = subset.getValue().size();
            }
        }

        return largestSubsetKey;
    }
}