import java.util.Scanner;

public class VowelCounter{

  public static void main(String[] args){

    Scanner scan = new Scanner(System.in);

    System.out.println("Enter a line of text, then press return.");
    String str = scan.nextLine();
    str.toLowerCase();

    int vowelCount = 0;
    for(int i = 0; i<str.length(); i++){
      char c = str.charAt(i);
      if( c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ) vowelCount += 1;
 
    }
   
   System.out.println( "There are: "+vowelCount+ " vowels.");
   
  }
}