package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    SortedSet<Character> guessedLetters;
    Set<String> hangmanDictionary;
    Map<String, Set<String>> partitionMap;
    String largestSubsetKey;
    int timesFound;

    public EvilHangmanGame() {
        guessedLetters = new TreeSet<>();
        hangmanDictionary = new HashSet<>();
        partitionMap = new HashMap<>();
        largestSubsetKey = "";
        timesFound = 0;
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        if (dictionary == null || wordLength == 0) {
            throw new EmptyDictionaryException("Dictionary is empty");
        }

        hangmanDictionary.clear();

        Scanner scanner = new Scanner(dictionary);
        String curWord;

        while (scanner.hasNext()) {
            curWord = scanner.next().toLowerCase();
            if (curWord.length() == wordLength) {
                hangmanDictionary.add(curWord);
            }
        }
        scanner.close();

        if (hangmanDictionary.size() == 0) {
            throw new EmptyDictionaryException("Dictionary is empty");
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

        partitionDictionary(guess);

        reduceDictionary(guess);

        timesFound = getNumMatches(largestSubsetKey, guess);

        return hangmanDictionary;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void partitionDictionary(char guess) {
        for (String word : hangmanDictionary) {
            String key = createKey(word, guess);

            // if already exists
            if (partitionMap.containsKey(key)) {
                partitionMap.get(key).add(word);

            } else {
                Set<String> newSet = new HashSet<>();
                newSet.add(word);
                partitionMap.put(key, newSet);
            }
        }
        hangmanDictionary.clear();
    }

    public void reduceDictionary(char guess) {
        String tempLargestSubsetKey = "";

        // Get biggest subset
        for (Map.Entry<String, Set<String>> subset : partitionMap.entrySet()) {
            if (subset.getValue().size() > hangmanDictionary.size()) {
                hangmanDictionary = subset.getValue();
                tempLargestSubsetKey = createKey(subset.getKey(), guess);
            }
        }
        partitionMap.remove(tempLargestSubsetKey);

        // Handle tiebreakers
        for (Map.Entry<String, Set<String>> subset : partitionMap.entrySet()) {
            int curSubsetMatches = getNumMatches(subset.getKey(), guess);
            int largestSubsetMatches = getNumMatches(tempLargestSubsetKey, guess);

            if (subset.getValue().size() == hangmanDictionary.size()) {
                if (curSubsetMatches < largestSubsetMatches) {
                    hangmanDictionary = subset.getValue();
                    tempLargestSubsetKey = createKey(subset.getKey(), guess);
                }

                if (curSubsetMatches == largestSubsetMatches) {
                    for (int i = largestSubsetKey.length() - 1; i >= 0; i--) {
                        if (subset.getKey().charAt(i) != tempLargestSubsetKey.charAt(i)) {
                            if (subset.getKey().charAt(i) != '_' && tempLargestSubsetKey.charAt(i) == '_') {
                                hangmanDictionary = subset.getValue();
                                tempLargestSubsetKey = createKey(subset.getKey(), guess);
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        largestSubsetKey = combineKeys(largestSubsetKey, tempLargestSubsetKey);
        partitionMap.clear();
    }

    public String getLargestSubsetKey() {
        return largestSubsetKey;
    }

    public int getTimesFound() {
        return timesFound;
    }

    public String makeBlankString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.insert(i, "_");
        }
        return sb.toString();
    }

    public String createKey(String word, char guess) {
        StringBuilder sb = new StringBuilder(makeBlankString(word.length()));
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                sb.setCharAt(i, guess);
            }
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
}
