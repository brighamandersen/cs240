package spell;

public class Node implements INode {
	static public final int LETTERS_IN_ALPHABET = 26;
	private int frequency;
	private Node[] children;

	public Node() {
		frequency = 0;
		children = new Node[LETTERS_IN_ALPHABET];
	}

	@Override
	public int getValue() {
		return frequency;
	}

	@Override
	public void incrementValue() {
		frequency++;
	}

	@Override
	public INode[] getChildren() {
		return children;
	}

	public Node getChild(int index) {
		return children[index];
	}

	public void addChild(int index) {
		children[index] = new Node();
	}

	public boolean hasChild(int index) {
		if (children[index] != null) {
			return true;
		} else {
			return false;
		}
	}

	public String printCharsInAlphabet() {
		String outStr = "Level 1: ";
		for (int i = 0; i < LETTERS_IN_ALPHABET; i++) {
			if (children[i] != null) {
				outStr += (char)(i + 'a') + ", ";
			}
		}
		return outStr;
	}
}