package hangman;

public class ArrayGameModel implements GameModel {

	/** The number of characters (lower/upper). */
	private static final int ALPHABET_COUNT = 26 * 2;
	
	/** hung state */
	private int state;

	// Number of guesses
	private int guesses;

	// Word to guess
	private String guessWord;

	// char array of past guesses
	private char[] previousGuesses = new char[ALPHABET_COUNT];

	// char array of word to guess
	private char[] wordState;

	public ArrayGameModel(String guessWord) {
		this.guessWord = guessWord;
		state = STARTING_STATE;
		char[] wordState = new char[guessWord.length()];
		this.wordState = wordState;
	}

	public boolean isPriorGuess(char guess) {
		return (previousGuessString().indexOf(guess) != -1);
	}

	public int numberOfGuesses() {
		return guesses;
	}

	public boolean isCorrectGuess(char guess) {
		return (!isPriorGuess(guess) && guessWord.indexOf(guess) != -1);
	}

	public boolean doMove(char guess) {
		boolean didAction = false;
		guesses += 1;

		if (isCorrectGuess(guess)) {
			for (int i = 0; i < guessWord.length(); i++) {
				if (guess == guessWord.charAt(i))
					wordState[i] = guess;
			}
			didAction = true;
		}

		else if (!isPriorGuess(guess))
			state += 1;
		if (!isPriorGuess(guess))
			previousGuesses[guesses - 1] = guess;

		return didAction;

	}

	public boolean inWinningState() {
		// NOTE: I had to look up the procedure of representing 'all
		// non-letters' as a simple string
		return (toString().replaceAll("\\W+", "").equals(guessWord));

	}

	public boolean inLosingState() {
		return (state == 10);
	}

	public String toString() {
		String s = "";

		for (char c : wordState) {
			if (c == 0)
				s += "_ ";
			else
				s += c + " ";
		}

		return s.trim();

	}

	public String previousGuessString() {
		String s = "[";
		for (char c : previousGuesses) {
			if (c != 0) {
				if (c == previousGuesses[0])
					s += c;
				else
					s += ", " + c;
			}

		}
		s += "]";

		return s;
	}

	public int getState() {
		return state;
	}
	
	public String getWord() {
		return guessWord;
	}
	
}

