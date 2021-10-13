import java.util.Scanner;
import java.io.IOException;

public class WordPercentagesDriver{
  public static void main(String[] args) throws IOException{
      try{
          String fileName;
          Scanner scan = new Scanner(System.in);
          System.out.println("Enter a text file name to analyze:");
          fileName = scan.nextLine();
          System.out.println("Analyzed text: " + fileName);
          WordPercentages wp = new WordPercentages(fileName);
          wp.processFile();
          double [] results = wp.getWordPercentages();
          printWordSizePercentages(results);
          System.out.printf("average word length: %4.2f",wp.getAvgWordLength());
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
  }
  
  public static void printWordSizePercentages(double[] data){
      for(int i = 1; i < data.length; i++)
        if (i==data.length-1)
           System.out.printf("words of length " + (i) + " or greater: %4.2f%%\n",data[i]);
        else
            System.out.printf("words of length " + (i) + ": %4.2f%%\n",data[i]);
  }
}