package spell;

import java.util.Objects;

public class Trie implements ITrie {
    Node root;
    int nodeCount;
    int wordCount;

    public Trie() {
        root = new Node();
        nodeCount = 1;
        wordCount = 0;
    }

    @Override
    public void add(String word) {
        Node curNode = root;

        for (int i = 0; i < word.length(); i++) {
            char curLetter = word.charAt(i);
            int curIndex = curLetter - 'a';

            if (!curNode.hasChild(curIndex)) {
                curNode.addChild(curIndex);
                nodeCount++;
            }
            curNode = curNode.getChild(curIndex);
        }

        if (curNode.getValue() == 0) {
            wordCount++;
        }

        curNode.incrementValue();
    }

    @Override
    public INode find(String word) {
        Node curNode = root;

        for (int i = 0; i < word.length(); i++) {
            char curLetter = word.charAt(i);
            int curIndex = curLetter - 'a';

            if (!curNode.hasChild(curIndex)) {
                return null;
            }
            curNode = curNode.getChild(curIndex);
        }

        if (curNode.getValue() == 0) {
            return null;
        }

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

    @Override
    public int hashCode() {
        int firstNonNullIndex = 0;

        for (int i = 0; i < Node.LETTERS_IN_ALPHABET; i++) {
            if (root.hasChild(i)) {
                firstNonNullIndex = i + 1;
                break;
            }
        }
        return (wordCount * nodeCount + firstNonNullIndex);
    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toStringHelper(root, curWord, output);

        return output.toString();
    }

    public void toStringHelper(Node node, StringBuilder curWord, StringBuilder output) {
        if (node.getValue() > 0) {
            output.append(curWord.toString() + "\n");
        }

        for (int i = 0; i < Node.LETTERS_IN_ALPHABET; i++) {
            if (node.hasChild(i)) {
                char curLetter = (char) ('a' + i);
                curWord.append(curLetter);

                toStringHelper(node.getChild(i), curWord, output);

                curWord.deleteCharAt(curWord.length() - 1);
            }
        }
    }

}
