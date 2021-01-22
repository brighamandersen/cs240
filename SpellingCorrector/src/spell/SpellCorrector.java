package spell;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
	private Trie trie;	// = new Trie(); - FIXME - moved to constructor
	private List<String> possibleWords = new ArrayList<String>();
	private List<String> wrongWords = new ArrayList<String>();
//	private boolean distance1;	// FIXME - What is this used for?

	public SpellCorrector() {
		trie = new Trie();
	}

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		File file = new File(dictionaryFileName);
		Scanner scanner = new Scanner(file);

		while (scanner.hasNext()) {
			String curWord = scanner.next().toLowerCase();
			trie.add(curWord);
		}
		scanner.close();
	}

	@Override
	public String suggestSimilarWord(String inputWord) {
		INode foundNode = trie.find(inputWord);
		if (foundNode == null) {
			return null;
		}
		return foundNode.toString();
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
