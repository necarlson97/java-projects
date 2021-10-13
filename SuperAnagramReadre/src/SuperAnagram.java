//Nils Carlson
//Super Anagram Project
public class SuperAnagram {
	
	public boolean isSuperAnagram(String first, String second){
		  
		//Prepares the strings for checking
		first = first.toLowerCase();
		second = second.toLowerCase();
		// I knew there was a metacharacter way to remove punctuation, but I had to look up regex
		first = first.replaceAll("\\p{P}", "");
		second = second.replaceAll("\\p{P}", "");
		first = first.replaceAll(" ", "");
		second = second.replaceAll(" ", "");
		  
		
		//Checks every letter in 'first' for a match in 'second
		int s = 0; 
		while(s<second.length() && first.length() > 0){ 

			String sStr = "" + second.charAt(s);
			String fStr = "" + first.charAt(0);
			  
			if(second.charAt(s) == first.charAt(0)){
				//Letters are removed from both strings once found
				first = first.substring(1);
				second = second.substring(0,s) + second.substring(s+1); 
				s=0;
			}
			else s++;
			  
		}
		  			
		//If all letters have been found, 'first' will be an empty string
		if(first.length() == 0) return true;
		else return false;
		  
	}

}
