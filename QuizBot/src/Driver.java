import java.io.Console;

public class Driver {
	
	Answer[] a1 = { new Answer(Answer.yes, "Good!"),
					new Answer(Answer.no, "Still counts!") };
	Question q1 = new Question("Can you respond to this test?", a1);
	
	Driver() {
		q1.ask();
	}
	
	public static void main(String[] args) {
		new Driver();
	}

}
