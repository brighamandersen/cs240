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
        partitionMap = new HashMap<String, Set<String>>();
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

        Scanner scanner = new Scanner(dictionary);
        String curWord;

        hangmanDictionary.clear();

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
            throw new GuessAlreadyMadeException("You already guessed that letter");
        }

        guessedLetters.add(guess);

        // Program partitions hangmanDictionary relative to guessed letter
        partitionDictionary(guess);

        // Largest subset in the partition becomes the new hangmanDictionary
        reduceDictionary(guess);

        timesFound = getLetterCount(largestSubsetKey, guess);

        return hangmanDictionary;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void partitionDictionary(char guess) {   // Creates subsets based on current dictionary and guess
        for (String word : hangmanDictionary) {
            String blankString = makeBlankString(word.length());
            boolean gotAdded = false;

            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    gotAdded = true;
                    String key = convertToKey(word, guess);

                    // CASE 1 - if key in map, add word to end of set
                    if (partitionMap.containsKey(key)) {
                        partitionMap.get(key).add(word);
                    } else {    // CASE 2 - if key not in map, create a new key value pair, add single word to set
                        Set<String> newSet = new HashSet<>();
                        newSet.add(word);
                        partitionMap.put(key, newSet);
                    }
                }
            }
            // Case 3 - If none were created for the word, add to blank subset
            if (!gotAdded) {
                if (partitionMap.containsKey(blankString)) {
                    partitionMap.get(blankString).add(word);
                } else {
                    Set<String> newSet = new HashSet<>();
                    newSet.add(word);
                    partitionMap.put(blankString, newSet);
                }
            }
        }
    }

    public void reduceDictionary(char guess) {    // Makes largest partition the new hangmanDictionary
        hangmanDictionary.clear();
        String newLargestSubsetKey = "";

        // Get largest subset
        for (Map.Entry<String, Set<String>> subset : partitionMap.entrySet()) {
            if (subset.getValue().size() > hangmanDictionary.size()) {  // Update hangmanDictionary if subset has more words
                hangmanDictionary = subset.getValue();
                newLargestSubsetKey = subset.getKey();
            }
        }
        partitionMap.remove(newLargestSubsetKey);

        // Handle tiebreakers
        for (Map.Entry<String, Set<String>> subset : partitionMap.entrySet()) {
            if (subset.getValue().size() == hangmanDictionary.size()) {
//                int largestGuessCount = newLargestSubsetKey.length() - newLargestSubsetKey.replaceAll(String.valueOf(guess), "").length();
//                int curSubsetGuessCount = subset.getKey().length() - subset.getKey().replaceAll(String.valueOf(guess), "").length();

                int largestGuessCount = getLetterCount(newLargestSubsetKey, guess);
                int curSubsetGuessCount = getLetterCount(subset.getKey(), guess);

                // 1 Priority - Group which the letter doesn't appear at all
                // 2 Priority - Group with the fewest letters (ex: a__ over aa_)
                if (curSubsetGuessCount < largestGuessCount) {
                    hangmanDictionary = subset.getValue();
                    newLargestSubsetKey = subset.getKey();
                }

                // 3 Priority - Group with rightmost letter (ex: _e_e over e__e)
                // repeat over and over
                if (curSubsetGuessCount == largestGuessCount) {
                    String tempKey = newLargestSubsetKey;
                    for (int i = newLargestSubsetKey.length() - 1; i >= 0; i--) {
                        if (subset.getKey().charAt(i) != tempKey.charAt(i)) {
                            if (subset.getKey().charAt(i) != '_' && tempKey.charAt(i) == '_') {
                                hangmanDictionary = subset.getValue();
                                tempKey = subset.getKey();
                            } else {
                                break;
                            }
                        }
                    }
                    newLargestSubsetKey = tempKey;
                }
            }
        }
        largestSubsetKey = combineKeys(largestSubsetKey, newLargestSubsetKey);
        if (!partitionMap.isEmpty()) {
            partitionMap.clear();
        }
    }

    public String convertToKey(String word, char guess) {
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

//    public int checkMatches(Set<String> possibleWords, char letterGuessed) {
//        int numMatches = 0;
//
//        for (String word : possibleWords) {
//            if (word.contains(String.valueOf(letterGuessed))) {
//                numMatches++;
//            }
//        }
//        return numMatches;
//    }

    public String combineKeys(String key1, String key2) {
        StringBuilder sb = new StringBuilder(key1);
        for (int i = 0; i < key1.length(); i++) {
            // If one key is blank
            if (key1.charAt(i) == '_' && key2.charAt(i) != '_') {
                sb.setCharAt(i, key2.charAt(i));
            }
        }
        return sb.toString();
    }

    public int getLetterCount(String word, char letter) {
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