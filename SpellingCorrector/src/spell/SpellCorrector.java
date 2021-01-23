package spell;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
	private Trie trie = new Trie();
	private List<String> possibleWords = new ArrayList<String>();
	private List<String> wrongWords = new ArrayList<String>();
	static final int LETTERS_IN_ALPHABET = 26;

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
		INode matchNode = trie.find(inputWord);

		// If word was found in dictionary
		if (matchNode != null) {
			return matchNode.toString();
		}

		// Run d1 checks
		runDistanceCheck(inputWord);

		// If d1 has no suggested word, run d2
//		if (possibleWords == null) {
//			runDistanceCheck();
//		}

		// if it wasn't directly found, has no d1 words, and has no d2 words, then return no suggestion
		return null;
	}

	// N's fns below

	public String findPrecedence() {
		return "FIXME";
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

	public void runDistanceCheck(String inputWord) {
		deletion(inputWord);
		transposition(inputWord);
		alteration(inputWord);
		insertion(inputWord);

		printPossibleWords();
	}

	public void printPossibleWords() {
		for (String possibleWord : possibleWords) {
			System.out.println("Possible word: " + possibleWord);
		}
	}
}

