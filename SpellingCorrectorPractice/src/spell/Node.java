package spell;

import java.util.Arrays;
import java.util.Objects;

public class Node implements INode {
    static final int LETTERS_IN_ALPHABET = 26;
    int frequency;
    Node[] children;

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

    public boolean hasChild(int index) {
        if (children[index] == null) {
            return false;
        }
        return true;
    }

    public void addChild(int index) {
        children[index] = new Node();
    }

    public Node getChild(int index) {
        return children[index];
    }
}
