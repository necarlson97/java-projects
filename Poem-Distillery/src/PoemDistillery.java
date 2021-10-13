import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PoemDistillery {
	
	private final String PUNCTUATION = ".!?\"";
	
	private Scanner scan;
	private String fileName;
	private String[] poem;
	
	public PoemDistillery(String fileName){
		this.fileName = fileName;
		poem = createPoem();
		
	}
	
	
	
	public String[] createPoem(){
		// Determine number of lines (weighted random between 1-6);
		int lineNum;
		int r = (int) (Math.random() * 60);
		if(r > 54) lineNum = 6;
		else if(r > 47) lineNum = 5;
		else if(r > 40) lineNum = 4;
		else if(r > 30) lineNum = 3;
		else if(r > 10) lineNum = 2;
		else lineNum = 1;
		
		System.out.println("Line number: "+lineNum);
		poem = new String[lineNum];
		
		
		for(int i=0; i<lineNum; i++){
			poem[i] = findLine();
		}

		
		return poem;
	}
	
	
	// Find sentence to splice
	public String findLine(){
		try {
			scan = new Scanner(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int r = (int) (Math.random()*5000);
		for(int i=0; i<r; i++){
			if(!scan.hasNext()) return findLine();
			scan.nextLine();
		}
		
		// SHITS BROKEN HERE
		
		String line = "";
		r = (int) (Math.random()*5);
		String word = scan.next();
		for(int i=0; i<r; i++){
			for(char c : word.toCharArray()){
				if(PUNCTUATION.indexOf(c) != -1) line+=word;
				else i--;
			}
		}
		
		System.out.println("Line: "+line);
		return line;

		
		
	}
	
	
	public void writePoem(){
		
	}
	
	public void printPoem(){
		for(String l : poem){
			System.out.println(l);
		}
	}
	
	 

}
