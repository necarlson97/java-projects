import java.util.LinkedList;

public class AnagramGroup {
	
	// Here is our simple class for storing a group of anagrams

	// Simply stores a list of the words in this anagram
	LinkedList<String> group = new LinkedList<String>();
	// The ordered version of this anagram
	String ordered = "";
	// And a link to a possible next anagram (one at the same hash, to handle collisions)
	AnagramGroup next = null;
	
	public AnagramGroup(String ordered){
		this.ordered = ordered;
	}
	
	// Just for my debugging purposes
	public String toString(){
		String str = ordered + ": " + group.toString();
		if(next != null) str+=" Next: "+next.toString();
		
		return str;
	}
	
	// And simply cleans up the list so that the output is as desired
	public String outputString(){		
		return group.toString().replaceAll("[\\[\\],]", "");
	}
	
}








































































