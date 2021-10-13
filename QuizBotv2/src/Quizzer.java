import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Quizzer {
	
	Random rand = new Random();
	
	QuizFrame frame;
	
	Question currentQ;

	Quizzer(QuizFrame f) {
		frame = f;
	}
	
	void ask(Question q) {
		currentQ = q;
		ask();
	}
	
	void ask() {
		print(currentQ.str);
		String input = requestInput();
		
		LinkedList<Answer> possibleAnswers = new LinkedList<Answer>();
		for(int i=0; i<currentQ.answers.length; i++) {
			if(contains(input, currentQ.answers[i].answerType)) possibleAnswers.add(currentQ.answers[i]);
		}
		if(possibleAnswers.isEmpty()) reask(input); 			 		// Could not parse, try again
		else if(possibleAnswers.size() > 1) clarify(input, possibleAnswers);		// Could be multiple things
		else accept(input, possibleAnswers.getFirst());							// Got it
	}
	
	void reask(String input) {
		
		if(contains(input, Answer.hate)) {
			print("It seems like you mean to refuse this question. Is this true?");
			if(confirm()) {
				print("Understood. Moving on.");
				refuse(input);
				return;
			} 
		}
		if(currentQ.reaskCount > 4) {
			print("Ok, I am done with this. Moving on.");
			refuse(input);
			return;
		} else if(currentQ.reaskCount > 2) {
			print("Please, try to put it in a way I can understand.");
		}
		
		currentQ.reaskCount++;
		
		print("I was not able to interpret \""+input+"\". Please rephrase?");
		ask();
	}
	
	void clarify(String input, LinkedList<Answer> possibleAnswers) {
		clarify(input, possibleAnswers, 0);
	}
	
	void clarify(String input, LinkedList<Answer> possibleAnswers, int count) {
		if(count > 5) {
			print("Fine, have it your way.");
			refuse(input);
		}
		if(count > 2) print("Please, this is childish. Respond yes or no.");
		else if(count > 0) print("Please respond in the affirmative or negative.");
		
		print("I interpreted your answer as: "+possibleAnswers.getFirst());
		print("Is this correct?");
		
		String clarity = requestInput();
		if(contains(clarity, Answer.yes)) {
			print("Thank you.");
			accept(input, possibleAnswers.getFirst());
		} else if(contains(clarity, Answer.no)) {
			print("No? Ok.");
			reask(input);
		} else if(contains(clarity, Answer.hate)) {
			print("It seems like you mean to refuse this question. Is this true?");
			if(confirm()) {
				print("Understood. Moving on.");
				refuse(input);
			}
			else reask(input); 
		} else {
			print("I do not understand: "+clarity);
			clarify(input, possibleAnswers, count++);
		}
	}
	
	String requestInput() {
		frame.open();
		for(int i=0; i<100; i++) {
			if(frame.input != "") {
				String ret = frame.input;
				frame.close();
				return ret;
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(i == 50) print("Hello? Did you leave?");
		}
		
		print("Ok... Recording silence as refusal to answer.");
		frame.close();
		return "*silence*";
	}
	
	boolean confirm() {
		return confirm(0);
	}
	
	boolean confirm(int count) {
		
		String confirmation = requestInput();
		if(contains(confirmation, Answer.yes)) {
			return true;
		} else if(contains(confirmation, Answer.no)) {
			return false;
		} else if(count > 5){
			print("Enough, Im going with 'yes'");
			return true;
		} else {
			print("ERROR :: Please confirm with yes or no.");
			return confirm(count++);
		}
	}

	
	void refuse(String input) {
		accept(input, new Answer(Answer.refusal, "Subject unable to answer"));
	}
	
	void accept(String input, Answer acceptedAnswer) {
		print(acceptedAnswer.responce);
		print("Accepted: "+input+" as "+acceptedAnswer);
	}
	
	boolean contains(String s, String[] answerType) {
		for(String a : answerType) {
			for(String w : s.split(" ")) {
				if(w.trim().equals(a.trim())) return true;
			}
		}
		return false;
	}
	
	void print(String s) {
		printWithDelays(s, 100);
	}
	
	void printWithDelays(String s, long delay) {
		long tempDelay;
		frame.println("");
	    for (char ch : s.toCharArray()) {
	        frame.print(ch+"");
	        if(ch == ' ') tempDelay = 0;
	        else if(ch == '.' || ch == '?') tempDelay = delay * 5;
	        else tempDelay = (long) (delay * rand.nextFloat());
	        
	        try {
				TimeUnit.MILLISECONDS.sleep(tempDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    frame.println("");
	}

}
