import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
/* Everything up here is just telling java what pre-written peices 
 * of code we intend to use. We don't want to have to write every little 
 * thing from scratch, and the java programmers have coded some very basic
 * functions (like writing to a text file) for us, so we load them up here
 */

public class GenderTally {
	
	// Create a new 'scanner' which lets us read through text files
	private Scanner scan; 
	
	// Just the name of the file 
	// (a 'String' anything represented by multiple letters, s)
	private String fileName; 
	
	// The name of the book, just the name of the file without the date or '.txt'
	private String bookName;
	
	private int year;
	
	// An 'array' of our results, just a container to hold our 4 result integers within
	// In slot 0 will be Male pronouns, 1 will be Female, 2 will be Total, and 3 will be Word count
	private int[] results;
	
	// This is our 'constructor', which just allows us to run this file from another program
	// All anyone has to do is give us a file name, and we do the rest!
	public GenderTally(String fileName){
		// First we get the file name they gave us
		this.fileName = fileName;
		// Here we get the book name from the file name
		this.bookName = fileName.replaceAll(".txt", "").substring(0, fileName.length()-8)
				.replaceAll("FemaleAuthors/", "").replaceAll("MaleAuthors/", "");
		this.year = Integer.parseInt(fileName.replaceAll(".txt", "").substring(fileName.length()-8));
		
		// And from here we call 'getResults()' which tallys up the results of the given text
		results = getResults();
	}
	
	// This is another constructor just incase we get an actual file given to us
	// as opposed to just a file name
	public GenderTally(File file){
		// First we get the file name they gave us
		this.fileName = file.getName();
		results = getResults();
	}
	
	public int[] getResults(){
		// Just a place holder integer container for tallying up the counts
		int[] tally = new int[4];
		
		//This is just so the program ends if it was given a faulty file name
		try {
			scan = new Scanner(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// int means integer, and here we are setting our counters for 
		// male, female, and word count
		int m = 0;
		int f = 0;
		int w = 0;
		
		// while the 'scanner' says we still have lines left in our text
		while(scan.hasNext()){
			// get the next line
			String l = scan.next();
			
			// add to 'm' the occurances of the diffrent male prnouns in this line
			m += countOccurrences(l, "he");
			m += countOccurrences(l, "him");
			m += countOccurrences(l, "his");
			
			// do the same for the female pronouns in this line
			f += countOccurrences(l, "she");
			f += countOccurrences(l, "her");
			f += countOccurrences(l, "hers");
			
			// and lastly add to w the ammount of words in this line 
			w+= countWords(l);
		}
		
		// Now that we have been through every line and gotten our tallys, 
		// lets put them in their containers 
		tally[0] = m;
		tally[1] = f;
		tally[2] = m+f; // the Total Pronouns will just be the male # + the female # 
		tally[3] = w;
		
		// and this last line sends our temporary 'tally' container 
		// back to the top to be stored as 'results'
		return tally;
	}
	
	/* here is the function we wrote to count occurances of each pronoun, as used above
	* I wont fully explain it here, but it is basicaly splitting the
	* line up into diffrent words, then putting those into a list
	* then filtering out any that aren't the given pronoun
	* then returning the number of words that are left
	*/
	public static long countOccurrences(String msg, String target) {
	    return Arrays.stream(msg.split("[ ,\\.]")).filter(s -> s.equals(target)).count();
	}
	
	// this does the same as above, but without filtering any words out
	public static long countWords(String msg) {
		return Arrays.stream(msg.split("[ ,\\.]")).count();
	}
	
	// this just writes out the results below, so we can look for any errors
	public void printResults(){
		// System.out.println( fileName.replaceAll(".txt", "") );
		System.out.println("For the text '"+bookName+"': (Published in "+year+")");
		System.out.println("The author mentioned male pronouns: "+results[0]+" times,");
		System.out.println("and female pronouns: "+results[1]+" times,");
	}
	
	// and here is where we take the entire text and turn it into a single excell row
	// by setting the first column to the book name, the second to the publish year
	// and then looking through 'results' and setting each next column to that integer
	public String getCsv() {
		String csvLine = bookName+","+year;
		for(int i=0; i<results.length; i++) {
			csvLine+= ","+results[i];
		}
		return csvLine;
	}

}
