import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

public class AnagramFinder {
	
	int HASHSIZE = 1000000; // This number was found by a bit of trial and error, and works well 
	
	// Here is the array that hold our hash, see the 'AnagramGroup' class for more info
	AnagramGroup[] hashedAnagrams = new AnagramGroup[HASHSIZE];
	String fileSuffix; //And this is just to help with naming the output
	
	int anagramCount = 0; // Only because the assignment asks for it, not used in algorythm
	
	// Here is where we create our two anagram finders, one for each dict
	public static void main(String[] args){  
		AnagramFinder dict1Anagrams = new AnagramFinder("dict1");
		AnagramFinder dict2Anagrams = new AnagramFinder("dict2");
	}
	
	public AnagramFinder(String dictName){
		ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
		long before = System.currentTimeMillis(); // Just for timing purposes
		
		this.fileSuffix = dictName.substring(4); // Again, just to help name the output programatically
		parseDict(dictName); // Here is where the magic happens, the input is hashed, see below
		outputAnagrams("Anagrams"+fileSuffix); // And here is where the hash is output to a new file
		
		double result = (System.currentTimeMillis() - before)/1000.0; // Again, just for timing
		System.out.println("For hashsize: "+HASHSIZE+" with "+dictName+"\nReal time: "+result+" seconds");
		result = bean.getCurrentThreadUserTime()/1000000000.0;
		System.out.println("User time: "+result+" seconds");
		
		System.out.println("Anagram count: "+anagramCount);
	}
	
	private void outputAnagrams(String fileName) {
		try{
		    PrintWriter writer = new PrintWriter(fileName+".txt", "UTF-8"); // Open output file
		    for(int i=0; i<hashedAnagrams.length; i++){ // Iterate through the hash array
				if(hashedAnagrams[i] != null) {
					AnagramGroup currNode = hashedAnagrams[i]; // If there is an anagram there...
					while(currNode != null){ // Traverse the linked anagrams, writing them to the file as you go
						writer.println(currNode.outputString());
						currNode = currNode.next;
						anagramCount++;
					}
				}
					
			}
		    writer.close();
		} 
		
		catch (IOException e) {
			System.out.println("Error writing to file '");
		}
		
	}

	public void parseDict(String fileName){
		String line = "";
		try {
			FileReader fileReader = new FileReader(fileName); // Open the file to read 
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) 
				if(!line.equals("")) addToHash(line); // Read each line and send it off to be hashed
			
			
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		
	}

	private void addToHash(String line) { // Here is where the algorythm takes over
		String orderedLine = insertionOrder(line); // The word is ordered by the function below
		int hash = getHash(orderedLine); // The hash is gotten from the function below
		
		// These are the four cases that can occur when inserting a new hash
		
		// #1 - Nothing exists at that location yet, add new AnagramGroup
		if(hashedAnagrams[hash%HASHSIZE] == null) {
			AnagramGroup newGroup = new AnagramGroup(orderedLine);
			newGroup.group.add(line);
			hashedAnagrams[hash%HASHSIZE] = newGroup;
		}
		// #2 - Something exists there, and it is the anagram group where our word belongs
		else if(hashedAnagrams[hash%HASHSIZE].ordered.equals(orderedLine)) {
			hashedAnagrams[hash%HASHSIZE].group.add(line);
		}
		else {
			AnagramGroup currNode = hashedAnagrams[hash%HASHSIZE];
			while(currNode.next != null && !currNode.ordered.equals(orderedLine))
				currNode = currNode.next;
			
			// #3 - Something exists there, and we have to go traversing the list for the right group
			if(currNode.ordered.equals(orderedLine)){
				currNode.group.add(line);
				return;
			}
				
			// #4 - There was no group that where our word belongs, so we add a new AnagramGroup
			AnagramGroup newGroup = new AnagramGroup(orderedLine);
			newGroup.group.add(line);
			currNode.next = newGroup;
		}
		
	}
	
	// The following three functions are for ordering the word so that we can hash them
	// togeather with other anagrams, insertionOrder seems to be reasonably fast
	
	// Here is an order using a system method
	// NOTE: this is unused, I just put it here to show what my comparisons are against
	private String systemOrder(String unordered){
		char[] letters = unordered.toCharArray();
		Arrays.sort(letters);
		String ordered = new String(letters);
		return ordered;
	}
	
	// Here is an ordering using insertion sort
	// only added about a second of delay compared to the system sort
	private String insertionOrder(String unordered){
		char temp;
		char[] input = unordered.toCharArray();
		for(int i=1; i<input.length; i++){
			for(int j=i; j>0; j--){
				if(input[j] < input[j-1]){
					temp = input[j];
					input[j] = input[j-1];
					input[j-1] = temp;
				}
			}
		}
		String ordered = new String(input);
		return ordered;
	}
	
	// And is an ordering using radix sort
	// it unfortunatly added about 8 seconds of delay, but it was worth a shot
	private String radixOrder(String unordered){
		char[] input = unordered.toCharArray();
		char[] output = new char[input.length];
		int[] count = new int[27];
		
		for(int i=0; i<input.length; i++){
			int charInt = charToInt(input[i]);
			
			count[charInt]++;
		}
		
		for(int i=1; i<count.length; i++){
			count[i] += count[i-1];
		}
		
		for(int i=input.length-1; i>=0; i--){
			int charInt = charToInt(input[i]);
			
			output[count[charInt]-1] = input[i];
			count[charInt]--;
		}
		
		String ordered = new String(output);
		return ordered;	
	}
	
	// A simple method to turn a char to an int (useful for radix)
	private int charToInt(char c){
		int charInt = (int)c;
		if(charInt >= 64 && charInt < 91) charInt -= 65;
		else if(charInt >= 97 && charInt < 123) charInt -= 97;
		else charInt = 26;
		return charInt;
	}
	
	// And a very simple hash, does a good enough job of avoiding collisions given its simplicity
	private int getHash(String orderedLine) {
		int hash = 7;
		for (int i = 0; i < orderedLine.length(); i++) {
		    hash = hash*31 + orderedLine.charAt(i);
		}
		return Math.abs(hash);
	}
	
}
