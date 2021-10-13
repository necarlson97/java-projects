import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileLoader {
	
	String folder = "Journal/";
	
	public FileLoader(){
		
	}
	
	public void writeFile(String filename, String contents){
		
		File desiredFile = new File(folder+filename+".txt");
		if(contents.length() == 0) {
			desiredFile.delete();
			return;
		}
		
		try{
		    PrintWriter writer = new PrintWriter(folder+filename+".txt", "UTF-8");
		    writer.println(contents);
		    writer.close();
		} catch (IOException e) {
		   System.out.println("Writing to "+folder+filename+" failed.");
		}
		
	}
	
	public String readFile(String filename){
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File(folder+filename+".txt"));
		} catch (FileNotFoundException e) {
			return "";
		}
		String content = scanner.useDelimiter("\\Z").next();
		return content;
	}

}
