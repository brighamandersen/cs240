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
        if (dictionary == null) {
            throw new IOException("Dictionary does not exist");
        }

        if (dictionary.length() == 0 || wordLength == 0) {
            throw new EmptyDictionaryException("Dictionary is empty");
        }

        Scanner scanner = new Scanner(dictionary);
        String curWord;

        hangmanDictionary.clear();

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

        // Make blank key
        largestSubsetKey = makeBlankString(wordLength);
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);

        if (guessedLetters.contains(guess)) {
            throw new GuessAlreadyMadeException("You already guessed that");
        }

        guessedLetters.add(guess);

        partitionDictionary(guess);

        reduceDictionary(guess);

        timesFound = getLetterCount(largestSubsetKey, guess);

        return hangmanDictionary;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public String makeBlankString(int wordLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            sb.insert(i, "_");
        }
        return sb.toString();
    }

    public String convertToKey(String word, char guess) {
        StringBuilder sb = new StringBuilder(makeBlankString(word.length()));
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                sb.setCharAt(i, guess);
            }
        }
        return sb.toString();
    }

    public int getLetterCount(String word, char letter) {
        int count = 0;
        for (int i = 0; i < word.length(); i ++) {
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

    public void partitionDictionary(char guess) {
        String key;

        for (String word : hangmanDictionary) {
            key = convertToKey(word, guess);

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
        String tempLargestSubsetKey = makeBlankString(largestSubsetKey.length());

        // Get biggest subset first
        for (Map.Entry<String, Set<String>> subset : partitionMap.entrySet()) {
            if (subset.getValue().size() > hangmanDictionary.size()) {
                hangmanDictionary = subset.getValue();
                tempLargestSubsetKey = subset.getKey();
            }
        }
        partitionMap.remove(tempLargestSubsetKey);


        /////// Handle tiebreakers
        for (Map.Entry<String, Set<String>> subset : partitionMap.entrySet()) {
            if (subset.getValue().size() == hangmanDictionary.size()) {
                int curSubsetMatches = getLetterCount(subset.getKey(), guess);
                int largestSubsetMatches = getLetterCount(tempLargestSubsetKey, guess);

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
        partitionMap.clear();
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

}
