package spell;

public class Node implements INode {
	private int dictOccurrences = 0;
	private Node[] children = new Node[SpellCorrector.LETTERS_IN_ALPHABET];

	@Override
	public int getValue() {
		return dictOccurrences;
	}

	@Override
	public void incrementValue() {
		dictOccurrences++;
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
		for (int i = 0; i < SpellCorrector.LETTERS_IN_ALPHABET; i++) {
			if (children[i] != null) {
				outStr += (char)(i + 'a') + ", ";
			}
		}
		return outStr;
	}
}



// CHECKS FOR CLOSEST WORD

//1	Calc d1s on the level (are they valid words?)
//2	Calc d1s vertically (are parent or child valid words?)
//3	Throw out invalid words (valueCount == 0)
//4	Recommend the one with the highest valueCount
//5	If there are no d1s, then calc d2s (same as above)