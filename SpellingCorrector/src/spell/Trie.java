package spell;

public class Trie implements ITrie {
	private Node root;
	private int wordCount = 0;
	private int nodeCount = 1;
	// private int hashValue = 0 // FIXME - Only use if needed

	public Trie() {
		root = new Node();
	}

	@Override
	public void add(String word) {
		// Adds words to trie and increments frequency count
		System.out.println(word);
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

	private void toStringHelper(Node node, StringBuilder curWord, StringBuilder output) {
		if (node.getValue() > 0) {
			output.append(curWord.toString());
			output.append("\n");
		}

		for (int i = 0; i < node.getChildren().length; ++i) {
			Node child = (Node) node.getChildren()[i];	// FIXME - Is this right?
//			INode child = node.getChildren()[i];	// FIXME - Or is this?

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
