package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the pass-off program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];

		ISpellCorrector spellCorrector = new SpellCorrector();

		try {
			spellCorrector.useDictionary(dictionaryFileName);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}

		String suggestion = spellCorrector.suggestSimilarWord(inputWord);

		if (suggestion == null) {
		    suggestion = "No similar word found";
		}

		System.out.println("Suggestion is: " + suggestion);
	}

}
