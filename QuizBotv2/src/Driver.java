import java.io.Console;

public class Driver {
	
	static QuizFrame f = new QuizFrame();
	static Quizzer q = new Quizzer(f);
	
	static Answer[] a1 = { new Answer(Answer.yes, "Good!"),
					new Answer(Answer.no, "Still counts!") };
	static Question q1 = new Question("Can you respond to this test?", a1);
	
	public static void main(String[] args) {
		q.ask(q1);
	}

}
