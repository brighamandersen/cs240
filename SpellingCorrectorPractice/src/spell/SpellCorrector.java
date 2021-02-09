package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    Trie trie;
    List<String> possibleWords;
    String bestSuggestion;
    int bestSuggestionFrequency;

    public SpellCorrector() {
        trie = new Trie();
        possibleWords = new ArrayList<>();
        bestSuggestion = "";
        bestSuggestionFrequency = 0;
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);

        if (file == null) {
            throw new IOException("File does not exist");
        }

        Scanner scanner = new Scanner(file);

        String curWord;
        while (scanner.hasNext()) {
            curWord = scanner.next().toLowerCase();
            trie.add(curWord);
        }
        scanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        possibleWords.clear();
        bestSuggestion = "";
        bestSuggestionFrequency = 0;

        INode matchNode = trie.find(inputWord);
        if (matchNode != null) {
            return inputWord;
        }

        runDistanceOne(inputWord);
        generateBestSuggestion();

        if (bestSuggestion != "") {
            return bestSuggestion;
        }

        runDistanceTwo();
        generateBestSuggestion();

        if (bestSuggestion != "") {
            return bestSuggestion;
        }

        return null;
    }

    public void runDistanceOne(String word) {
        delete(word);
        transpose(word);
        alter(word);
        insert(word);
    }

    public void delete(String word) {
        for (int i = 0; i < word.length(); i++) {
            StringBuilder sb = new StringBuilder(word);
            sb.deleteCharAt(i);
            possibleWords.add(sb.toString());
        }
    }

    public void transpose(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            StringBuilder sb = new StringBuilder(word);

            char char1 = word.charAt(i);
            char char2 = word.charAt(i + 1);

            sb.setCharAt(i, char2);
            sb.setCharAt(i + 1, char1);

            possibleWords.add(sb.toString());
        }
    }

    public void alter(String word) {
        for (int i = 0; i < word.length(); i++) {
            for (char letter = 'a'; letter <= 'z'; letter++) {
                StringBuilder sb = new StringBuilder(word);

                if (letter != word.charAt(i)) {
                    sb.setCharAt(i, letter);
                    possibleWords.add(sb.toString());
                }
            }
        }
    }

    public void insert(String word) {
        for (int i = 0; i < word.length() + 1; i++) {
            for (char letter = 'a'; letter <= 'z'; letter++) {
                StringBuilder sb = new StringBuilder(word);

                sb.insert(i, letter);
                possibleWords.add(sb.toString());
            }
        }
    }

    public void runDistanceTwo() {
        List<String> tempPossibleWords = new ArrayList<>(possibleWords);
        possibleWords.clear();

        for (String word : tempPossibleWords) {
            runDistanceOne(word);
        }
    }

    public void generateBestSuggestion() {
        INode matchNode;
        for (String possibleWord : possibleWords) {
            matchNode = trie.find(possibleWord);

            if (matchNode != null) {
                processNewSuggestion(matchNode, possibleWord);
            }
        }
    }

    public void processNewSuggestion(INode newMatchNode, String newSuggestion) {
        int newFrequency = newMatchNode.getValue();

        if (bestSuggestion == "") {
            bestSuggestion = newSuggestion;
            bestSuggestionFrequency = newFrequency;
        }

        if (newFrequency > bestSuggestionFrequency) {
            bestSuggestion = newSuggestion;
            bestSuggestionFrequency = newFrequency;
        }

        if (newFrequency == bestSuggestionFrequency && newSuggestion.compareTo(bestSuggestion) < 0) {
            bestSuggestion = newSuggestion;
            bestSuggestionFrequency = newFrequency;
        }
    }
}
