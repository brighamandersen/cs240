package spell;

public class Node implements INode {
	private final int LETTERS_IN_ALPHABET = 26;
	private int frequency = 0;
	private Node[] children = new Node[LETTERS_IN_ALPHABET];

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

	public void addChild(int index) {
		children[index] = new Node();
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