import java.util.Scanner;

public class SuperAnTester {
	
	public static void main(String[] args){
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter a phrase: ");
		String first = scan.nextLine();
		
		System.out.println("Enter another phrase: ");
		String second = scan.nextLine();
		
		SuperAnagram anagram = new SuperAnagram();
		
		System.out.println(anagram.isSuperAnagram(first, second));
	}

}
