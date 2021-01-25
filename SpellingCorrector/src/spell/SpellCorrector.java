package spell;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
	private Trie trie = new Trie();
	private List<String> possibleWords = new ArrayList<String>();
	private String bestSuggestion = "";
	private int bestSuggestionFrequency = 0;

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		File file = new File(dictionaryFileName);
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

	public void deletion(String inputWord) {
		String wordToAdd;

		for (int i = 0; i < inputWord.length(); i++) {
			StringBuilder sb = new StringBuilder(inputWord);
			wordToAdd = sb.deleteCharAt(i).toString();
			possibleWords.add(wordToAdd);
		}
	}

	public void transposition(String inputWord) {
		String wordToAdd;
		char char1;
		char char2;

		for (int i = 0; i < inputWord.length() - 1; i++) {
			StringBuilder sb = new StringBuilder(inputWord);
			char1 = sb.charAt(i);
			char2 = sb.charAt(i + 1);

			sb.setCharAt(i, char2);
			sb.setCharAt(i + 1, char1);

			wordToAdd = sb.toString();
			possibleWords.add(wordToAdd);
		}
	}

	public void alteration(String inputWord) {
		String wordToAdd;
		char curChar;

		for (int i = 0; i < inputWord.length(); i++) {
			StringBuilder sb = new StringBuilder(inputWord);

			curChar = sb.charAt(i);
			for (char letter = 'a'; letter <= 'z'; letter++) {
				if (letter != curChar) {
					sb.setCharAt(i, letter);
					wordToAdd = sb.toString();
					possibleWords.add(wordToAdd);
				}
			}
		}
	}

	public void insertion(String inputWord) {
		String wordToAdd;

		for (int i = 0; i < inputWord.length() + 1; i++) {
			for (char letter = 'a'; letter <= 'z'; letter++) {
				StringBuilder sb = new StringBuilder(inputWord);
				sb.insert(i, letter);
				wordToAdd = sb.toString();
				possibleWords.add(wordToAdd);
			}
		}
	}

	public void generateDistanceOnes(String inputWord) {
		deletion(inputWord);
		transposition(inputWord);
		alteration(inputWord);
		insertion(inputWord);

		printPossibleWords();
	}

	public void generateBestSuggestion() {
		INode matchNode = new Node();
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

	public void printPossibleWords() {
		for (String possibleWord : possibleWords) {
			System.out.println("Possible word: " + possibleWord);
		}
	}
}

