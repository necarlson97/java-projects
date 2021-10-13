package hangman;

public class LLCharacterNode {

	// Char given to the class

	private char infoChar;

	// Link Char

	private LLCharacterNode linkChar = null;

	public LLCharacterNode(char c) {
		this.infoChar = c;
	}

	public char getInfo() {
		return infoChar;
	}

	public boolean hasLink() {
		return (linkChar != null);
	}

	public LLCharacterNode getLink() {
		return linkChar;
	}

	public void setLink(LLCharacterNode b) {
		linkChar = b;
	}

}
