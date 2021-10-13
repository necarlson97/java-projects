
public class Answer {
	
	static String yes[] = {"yes", "y ", "yep"};
	static String no[] = {"no", "n " , "nope"};
	static String hate[] = {"hate", "fuck"};
	static String love[] = {"love"};
	
	static String refusal[] = {"refusal"};

	String[] answerType;
	String responce;
	
	Answer(String[] answerType, String responce) {
		this.answerType = answerType;
		this.responce = responce;
	}
	
	public String toString() {
		return answerType[0];
	}
}
