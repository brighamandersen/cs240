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
		if (children[index] == null) {
			return false;
		}
		return true;
	}
}