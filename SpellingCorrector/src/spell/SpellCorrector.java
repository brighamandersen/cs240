package spell;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
	private Trie trie;
	private List<String> possibleWords;
	private String bestSuggestion;
	private int bestSuggestionFrequency;

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

		// Check for an exact match with the input word
		INode matchNode = trie.find(inputWord);
		if (matchNode != null) {
			return inputWord;
		}

		// Run d1 check
		generateDistanceOnes(inputWord);
		generateBestSuggestion();

		// If best suggestion is generated from d1, give it
		if (bestSuggestion != "") {
			return bestSuggestion;
		}

		// If no suggestions are found from d1 check, run d2
		generateDistanceTwos();
		generateBestSuggestion();

		// If suggestions are generated from d2, give the best one
		if (bestSuggestion != "") {
			return bestSuggestion;
		}

		// if it wasn't directly found, has no d1 words, and has no d2 words, then return no suggestion
		return null;
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

	public void generateDistanceOnes(String word) {
		delete(word);
		transpose(word);
		alter(word);
		insert(word);
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

	public String processNewSuggestion(INode newMatchNode, String newSuggestion) {
		int newFrequency = newMatchNode.getValue();

		// If there isn't already a suggestion, automatically add newSuggestion
		if (bestSuggestion == "") {
			replaceBestSuggestion(newSuggestion, newFrequency);
		}

		// If newSuggestion's frequency is greater than the current, add newSuggestion;
		if (newFrequency > bestSuggestionFrequency) {
			replaceBestSuggestion(newSuggestion, newFrequency);
		}

		// If the newSuggestion's frequency is equal to the current and it is sooner alphabetically, add new Suggestion
		if (newFrequency == bestSuggestionFrequency && newSuggestion.compareTo(bestSuggestion) < 0) {
			replaceBestSuggestion(newSuggestion, newFrequency);
		}

		// Otherwise keep with the current suggestion
		return bestSuggestion;
	}

	public void replaceBestSuggestion(String newSuggestion, int newFrequency) {
		bestSuggestion = newSuggestion;
		bestSuggestionFrequency = newFrequency;
	}

	public void generateDistanceTwos() {
		List<String> tempPossibleWords = new ArrayList<String>(possibleWords);	// Clone possible words
		possibleWords.clear();

		for (String tempPossibleWord : tempPossibleWords) {
			generateDistanceOnes(tempPossibleWord);
		}
	}
}

