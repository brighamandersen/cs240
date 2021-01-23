package spell;

public class Trie implements ITrie {
	private Node root = new Node();
	private int nodeCount = 1;
	private int wordCount = 0;

	@Override
	public void add(String word) {
		// Adds words to trie and increments frequency count
//		System.out.println(word);

		char curLetter;
		int curIndex;

		for (int i = 0; i < word.length(); i++) {
			curLetter = word.charAt(i);
			curIndex = curLetter - 'a';


			// If letter doesn't exists
			if (root.getChildren()[word.charAt(i) - 'a'] == null) {
				// Add new node for the letter
				root.addChild(curIndex);
				// Increment nodeCount
			} else {	// If letter does exist
				// add
			}
//			System.out.println(root.getChildren()[curIndex]);
		}
//		System.out.println(root.printCharsInAlphabet());
//		System.out.println(toString());
		// Increment wordCount

//		INode wordNode = find(word);
//		if (wordNode == null) {	// If word is not found
//			// FIXME - Add word to trie
//			wordNode.incrementValue();	// Increment word count
//		}


	}

	@Override
	public INode find(String word) {
		// Searches trie to find word
		return null;
	}

	@Override
	public int getWordCount() {
		return wordCount;
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
	}

	@Override
	public String toString() {
		StringBuilder curWord = new StringBuilder();
		StringBuilder output = new StringBuilder();

		toStringHelper(root, curWord, output);

		return output.toString();
	}

	private void toStringHelper(INode node, StringBuilder curWord, StringBuilder output) {
		if (node.getValue() > 0) {
			output.append(curWord.toString());
			output.append("\n");
		}

		for (int i = 0; i < node.getChildren().length; i++) {
			INode child = node.getChildren()[i];

			if (child != null) {
				char childLetter = (char)('a' + i);
				curWord.append(childLetter);

				toStringHelper(child, curWord, output);

				curWord.deleteCharAt(curWord.length() - 1);
			}
		}
	}

	@Override
	public int hashCode() {
		return nodeCount * wordCount;
		//	return (nodeCount * wordCount + sumOfNonNullRootChildIndexes);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (o == null)
			return false;

		if (getClass() != o.getClass()) {
			return false;
		}

		Trie otherTrie = (Trie)o;

		if (wordCount != otherTrie.getWordCount()) {
			return false;
		}

		if (nodeCount != otherTrie.getNodeCount()) {
			return false;
		}

		return equalsHelper(root, otherTrie.root);
	}

	public boolean equalsHelper(Node trie1, Node trie2) {
		// Compare the two trees and see if they have exactly the same structure.

		// Compare valueCounts of the two nodes
		if (trie1.getValue() != trie2.getValue()) {
			return false;
		}

		// Check if they have non-null children in the same spots (ex: one has an A child, other doesn't)

		// Recursively compare the children

		return true;	// FIXME
	}
}
