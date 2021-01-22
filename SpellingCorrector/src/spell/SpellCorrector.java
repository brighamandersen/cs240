package spell;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
	private Trie trie;	// = new Trie(); - FIXME - moved to constructor
	private List<String> possibleWords = new ArrayList<String>();
	private List<String> wrongWords = new ArrayList<String>();
	private boolean distance1;

	public SpellCorrector() {
		trie = new Trie();
	}

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		// Read in dictionary file and add every word to trie using a Scanner
		// try catch throw?
		Scanner scanner = new Scanner(dictionaryFileName);
		scanner.close();
	}

	@Override
	public String suggestSimilarWord(String inputWord) {
		return null;
		//
	}

	// N's fns below

	public String findPrecedence() {
		return "FIXME";
	}

	public void delete(String inputWord) {
		//
	}

	public void insert(String inputWord) {
		//
	}

	public void transpose(String inputWord) {
		//
	}

	public void alter(String inputWord) {
		//
	}
}
