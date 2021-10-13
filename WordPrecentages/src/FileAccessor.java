

import java.util.Scanner;
import java.io.*;
/**
   Text file line processor. Reads a text file containing lines of data
   and processes each line.
*/
public abstract class FileAccessor{
  protected String fileName;
  private Scanner scan;

  public FileAccessor(String f) throws IOException{
    fileName = f;
    scan = new Scanner(new FileReader(fileName));
  }

  public void processFile() { 
    while(scan.hasNext()){
      processLine(scan.nextLine());
    }
    scan.close();
  }
  
  protected abstract void processLine(String line);
  
  public void writeToFile(String data, String fileName) throws IOException{
		PrintWriter pw = new PrintWriter(fileName);
      pw.print(data);
      pw.close();
   }
}