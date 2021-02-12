package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    SortedSet<Character> guessedLetters;
    Set<String> hangmanDictionary;
    Map<String, Set<String>> partitions;
    String largestSubsetKey;
    int timesFound;

    public EvilHangmanGame() {
        guessedLetters = new TreeSet<>();
        hangmanDictionary = new HashSet<>();
        partitions = new HashMap<>();
        largestSubsetKey = "";
        int timesFound = 0;
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        if (dictionary == null) {
            throw new IOException("Dictionary does not exist");
        }

        if (dictionary.length() == 0) {
            throw new EmptyDictionaryException("Dictionary does not contain any words");
        }

        hangmanDictionary.clear();
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
            throw new EmptyDictionaryException("No words in dictionary are that length");
        }

        largestSubsetKey = makeBlankString(wordLength);
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        timesFound = 0;

        if (guessedLetters.contains(guess)) {
            throw new GuessAlreadyMadeException("Guess already made!");
        }

        guessedLetters.add(guess);

        // Program partitions hangmanDictionary relative to guessed letter
        partitionDictionary(guess);

        // Largest subset in the partition becomes the new hangmanDictionary
        reduceDictionary(guess);

        timesFound = getNumMatches(largestSubsetKey, guess);

        return hangmanDictionary;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void partitionDictionary(char guess) {   // Creates subsets based on current dictionary and guess
        String key;

        for (String word : hangmanDictionary) {
            key = createKey(word, guess);

            if (partitions.containsKey(key)) {
                partitions.get(key).add(word);
            } else {
                Set<String> newSet = new HashSet<>();
                newSet.add(word);
                partitions.put(key, newSet);
            }
        }
        hangmanDictionary.clear();
    }

    public void reduceDictionary(char guess) {    // Makes largest partition the new hangmanDictionary
        String tempLargestSubsetKey = makeBlankString(largestSubsetKey.length());

        // Get biggest subset first
        for (Map.Entry<String, Set<String>> subset : partitions.entrySet()) {
            if (subset.getValue().size() > hangmanDictionary.size()) {
                hangmanDictionary = subset.getValue();
                tempLargestSubsetKey = subset.getKey();
            }
        }
        partitions.remove(tempLargestSubsetKey);


        // Handle tiebreakers
        for (Map.Entry<String, Set<String>> subset : partitions.entrySet()) {
            if (subset.getValue().size() == hangmanDictionary.size()) {
                int curSubsetMatches = getNumMatches(subset.getKey(), guess);
                int largestSubsetMatches = getNumMatches(tempLargestSubsetKey, guess);

                if (curSubsetMatches < largestSubsetMatches) {
                    hangmanDictionary = subset.getValue();
                    tempLargestSubsetKey = subset.getKey();
                }

                if (curSubsetMatches == largestSubsetMatches) {
                    for (int i = tempLargestSubsetKey.length() - 1; i >= 0; i--) {
                        if (subset.getKey().charAt(i) != tempLargestSubsetKey.charAt(i)) {
                            if (subset.getKey().charAt(i) != '_' && tempLargestSubsetKey.charAt(i) == '_') {
                                hangmanDictionary = subset.getValue();
                                tempLargestSubsetKey = subset.getKey();
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        largestSubsetKey = combineKeys(largestSubsetKey, tempLargestSubsetKey);
        partitions.clear();
    }

    public String createKey(String word, char guess) {
        StringBuilder sb = new StringBuilder((makeBlankString(word.length())));
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                sb.setCharAt(i, guess);
            }
        }
        return sb.toString();
    }

    public String makeBlankString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.insert(i, "_");
        }
        return sb.toString();
    }

    public String combineKeys(String origKey, String mergeKey) {
        StringBuilder sb = new StringBuilder(origKey);
        for (int i = 0; i < origKey.length(); i++) {
            if (origKey.charAt(i) == '_' && mergeKey.charAt(i) != '_') {
                sb.setCharAt(i, mergeKey.charAt(i));
            }
        }
        return sb.toString();
    }

    public int getNumMatches(String word, char letter) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                count++;
            }
        }
        return count;
    }

    public String getLargestSubsetKey() {
        return largestSubsetKey;
    }

    public int getTimesFound() {
        return timesFound;
    }
}