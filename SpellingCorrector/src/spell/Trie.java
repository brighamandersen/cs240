package spell;

public class Trie implements ITrie {
	private Node root;
	private int nodeCount;
	private int wordCount;

	public Trie() {
		root = new Node();
		nodeCount = 1;
		wordCount = 0;
	}

	@Override
	public void add(String word) {	// Adds words to trie and increments frequency count
		Node curNode = root;
		char curLetter;
		int curIndex;

		for (int i = 0; i < word.length(); i++) {
			curLetter = word.charAt(i);
			curIndex = curLetter - 'a';

			if (!curNode.hasChild(curIndex)) {	// If node for letter doesn't exist

				curNode.addChild(curIndex);
				nodeCount++;
			}
			curNode = curNode.getChild(curIndex);
		}

		// Increment wordCount if word is unique to dictionary
		if (curNode.getValue() == 0) {
			wordCount++;
		}

		curNode.incrementValue();
	}

	@Override
	public INode find(String word) {	// Searches trie to find word
		Node curNode = root;
		char curLetter;
		int curIndex;

		for (int i = 0; i < word.length(); i++) {
			curLetter = word.charAt(i);
			curIndex = curLetter - 'a';

			// If curNode has a child at index
			if (curNode.hasChild(curIndex)) {
				curNode = curNode.getChild(curIndex);    // Set curNode to equal child at index		(move down trie)
			} else {
				return null;
			}
		}

		// Do not return back the word is found if it's a node but not a word in the dictionary
		if (curNode == null || curNode.getValue() == 0) {
			return null;
		}

		// Return the final Node
		return curNode;
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
		int firstNonNullIndex = 0;

		for (int i = 0; i < Node.LETTERS_IN_ALPHABET; i++) {
			if (root.hasChild(i)) {
				firstNonNullIndex = i + 1;
				break;
			}
		}

		return (nodeCount * wordCount + firstNonNullIndex);
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
		// Compare frequency of the two nodes
		if (trie1.getValue() != trie2.getValue()) {
			return false;
		}

		// Check if they have non-null children in the same spots (ex: one has an A child, other doesn't)
		for (int i = 0; i < Node.LETTERS_IN_ALPHABET; i++) {
			// Check if one has a child in a spot that the other doesn't
			if (trie1.hasChild(i) && !trie2.hasChild(i) || trie2.hasChild(i) && !trie1.hasChild(i)) {
				return false;
			}

			// If both have children in a spot, recurse down trie
			if (trie1.hasChild(i) && trie2.hasChild(i)) {
				if (!equalsHelper(trie1.getChild(i), trie2.getChild(i))) {	// Recursively compare the children
					return false;
				}
			}
		}
		return true;
	}
}
