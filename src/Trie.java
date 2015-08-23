/**
 * Created by jeet on 8/19/15.
 */


public class Trie {

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void addWord(String wordToAdd) {

        TrieNode node = this.root;

        for (char charInWord : wordToAdd.toCharArray()) {
            node = node.addChild(charInWord);

            if (node == null)
                return;
        }

        node.setWord(true);
    }

    public TrieNode matchWithDict(String wordToMatch) {

        TrieNode node = this.root;

        for (char charInWord : wordToMatch.toCharArray()) {
            node = node.getChar(charInWord);
            if (node == null)
                return null;
        }

        return node;
    }
}

class TrieNode {

    private TrieNode[] children;
    private boolean isWord = false;

    public TrieNode() {
        this.children = new TrieNode[26];
    }

    public TrieNode addChild(char charToAdd) {

        if (charToAdd < 'a' || charToAdd > 'z')
            return null;

        int offset = charToAdd - 'a';
        if (this.children[offset] == null) {
            this.children[offset] = new TrieNode();
        }

        return this.children[offset];
    }

    public TrieNode getChar(char passedChar) {
        int offset = passedChar - 'a';
        return this.children[offset];
    }

    public void setWord(boolean isWord) {
        this.isWord = isWord;
    }

    public boolean isWord() {
        return this.isWord;
    }
}