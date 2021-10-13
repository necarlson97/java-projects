import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Question {
	
	String str;
	
	Answer[] answers;
	String answer;
	
	int reaskCount = 0;
	
	Question(String str, Answer[] answers) {
		this.str = str;
		this.answers = answers;
	}
	
	

}
