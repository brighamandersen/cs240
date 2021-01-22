package spell;

public class Node implements INode {
	private int valueCount = 0;
	private Node[] children;

	public Node() {
		children = new Node[26];
	}

	@Override
	public int getValue() {
		return valueCount;
	}

	@Override
	public void incrementValue() {
		valueCount++;
	}

	@Override
	public INode[] getChildren() {	// FIXME - Why is type INode and not Node??
//		return new INode[0];
		return children;
	}

//	// N's fns below
//
//	@Override
//	public boolean equals(Object obj) {
//		return false;	// FIXME - Do we need this or is the equals() fn for Trie enough??
//	}
}
