import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Question {
	
	Random rand = new Random();
	
	String str;
	
	Answer[] answers;
	String answer;
	
	int reaskCount = 0;
	
	boolean allowHate;
	
	Question(String str, Answer[] answers) {
		this.str = str;
		this.answers = answers;
		
		for(Answer a : answers) {
			if(a.answerType == Answer.hate) allowHate = true;
		}
	}
	
	void ask() {
		print(str);
		answer = "";//in.nextLine().toLowerCase()+" ";
		processAnswer(answer);
	}
	
	void processAnswer(String answer) {
		LinkedList<Answer> possibleAnswers = new LinkedList<Answer>();
		for(int i=0; i<answers.length; i++) {
			if(contains(answer, answers[i].answerType)) possibleAnswers.add(answers[i]);
		}
		if(possibleAnswers.isEmpty()) reask(); 			 				// Could not parse, try again
		else if(possibleAnswers.size() > 1) clarify(possibleAnswers);		// Could be multiple things
		else accept(possibleAnswers.getFirst());							// Got it
	}
	
	void clarify(LinkedList<Answer> possibleAnswers) {
		clarify(possibleAnswers, 0);
	}
	
	void clarify(LinkedList<Answer> possibleAnswers, int count) {
		if(count > 5) {
			print("Fine, have it your way.");
			accept(possibleAnswers.getFirst());
		}
		if(count > 2) print("Please, this is childish. Respond yes or no.");
		else if(count > 0) print("Please respond in the affirmative or negative.");
		
		print("I interpreted your answer as: "+possibleAnswers.getFirst());
		print("Is this correct?");
		
		String clarity = "";//in.nextLine();
		if(contains(clarity, Answer.yes)) {
			print("Thank you.");
			accept(possibleAnswers.getFirst());
		} else if(contains(clarity, Answer.no)) {
			print("No? Ok.");
			reask();
		} else if(contains(clarity, Answer.hate)) {
			print("It seems like you mean to refuse this question. Is this true?");
			if(confirm()) {
				print("Understood. Moving on.");
				refuse();
			}
			else reask(); 
		} else {
			print("I do not understand: "+clarity);
			clarify(possibleAnswers, count++);
		}
	}
	
	boolean confirm() {
		return confirm(0);
	}
	
	boolean confirm(int count) {
		
		String confirmation = "";//= in.nextLine();
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
	
	void reask() {
		reaskCount++;
		
		if(!allowHate && contains(answer, Answer.hate)) {
			print("It seems like you mean to refuse this question. Is this true?");
			if(confirm()) {
				print("Understood. Moving on.");
				refuse();
				return;
			} 
		}
		if(reaskCount > 4) {
			print("Ok, I am done with this. Moving on.");
			refuse();
			return;
		} else if(reaskCount > 2) {
			print("Please, try to put it in a way I can understand.");
		}
		
		print("I was not able to interpret your answer. Please rephrase?");
		ask();
		
	}
	
	void refuse() {
		accept(new Answer(Answer.refusal, "Subject unable to answer"));
	}
	
	void accept(Answer acceptedAnswer) {
		print(acceptedAnswer.responce);
		print("Accepted: "+answer+" as "+acceptedAnswer);
	}
	
	boolean contains(String s, String[] answerType) {
		for(String a : answerType) {
			for(String n : Answer.no) {
				for(String w : s.split(" ")) {
					if(w == a) return true;
				}
			}
		}
		return false;
	}
	
	void print(String s) {
		printWithDelays(s, 50);
	}
	
	void printWithDelays(String s, long delay) {
		long tempDelay;
	    for (char ch : s.toCharArray()) {
	        System.out.print(ch);
	        if(ch == ' ') tempDelay = 0;
	        else if(ch == '.' || ch == '?') tempDelay = delay * 5;
	        else tempDelay = (long) (delay * rand.nextFloat());
	        
	        try {
				TimeUnit.MILLISECONDS.sleep(tempDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    System.out.println();
	}

}
