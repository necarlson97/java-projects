import java.util.Scanner;   
public class DogDriver{       
  public static void main(String[] args){      
    System.out.println("Enter the size of the track (an int greater than 3):");     
    Scanner s = new Scanner(System.in);      
    int trackSize = s.nextInt();      
    System.out.println("track Size: " + trackSize);      
    DogTrack d = new DogTrack(trackSize);      
    d.playGame();    
  } 
}   