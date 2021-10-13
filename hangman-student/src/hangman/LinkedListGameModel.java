package hangman;

public class LinkedListGameModel implements GameModel {

	/** The number of characters (lower/upper). */
	private static final int ALPHABET_COUNT = 26*2;
	
	/** hung state */
	private int	state;
	
	private int guesses = 0;
	
	private LLCharacterNode head = null;
	
	private String previousGuesses = "";
	
	private String guessWord;
	
	private String wordState = "";
	
	public LinkedListGameModel(String guessWord) {
		state = 0;
		this.guessWord = guessWord;
	}
		
	public boolean isPriorGuess(char guess) {
		return previousGuesses.indexOf(guess)!=-1;
	}
	
	public int numberOfGuesses() {
		return guesses;
	}
	
	public boolean isCorrectGuess(char guess) {
		return guessWord.indexOf(guess)!=-1;
	}
	
	public boolean doMove(char guess) {
		boolean success = isCorrectGuess(guess) && !isPriorGuess(guess);
				
		guesses += 1;
		if(isCorrectGuess(guess)) wordState+= guess;
		else if (!isPriorGuess(guess)) state +=1;

// Lets see what I get from auto grader before I finish implementing LLCharacterNodes
//		LLCharacterNode n = new LLCharacterNode(guess);	
//		for(int i = 0; i<guesses; i++){
//			if (head == null)
//				head = n;
//			else {
////				previousGuesses+=n.getInfo();
////				if(isCorrectGuess(guess)) wordState+=n.getInfo();
//					
//				n.setLink(head);
//				head = n;
//			}
//		}
		
		previousGuesses += guess;
		return success;
	}

	public boolean inWinningState() {
		return (toString().replaceAll("\\W+","").equals(guessWord));
	}

	public boolean inLosingState() {
		return state == 10;
	}
	
	public String toString() {
		String s = "";
		
		for(int i = 0; i<guessWord.length(); i++){
			s+=" ";
			if(wordState.indexOf(guessWord.charAt(i)) != -1){
				s+=guessWord.charAt(i);
			}
			else s+="_";
		}
		
		return s.trim();
	}

	public String previousGuessString() {
		String s = "[";
		
		s+=previousGuesses.charAt(0);
		for(int i = 1; i<previousGuesses.length(); i++){
			s+=", "+previousGuesses.charAt(i);
		}
		s+="]";
		
		return s;
	}
	
	public int getState() {
		return state;
	}

	public String getWord() {
		return guessWord;
	}
}
