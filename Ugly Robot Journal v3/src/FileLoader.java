import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileLoader {
	
	static Scanner scanner;
	
	static String folder = "Journal/";
	
	
	public FileLoader(){

	}
	
	public static LinkedList<String> getExistingEntries(){
		LinkedList<String> fileLog = new LinkedList<String>();

		File[] files = new File(folder).listFiles();

		for (File file : files) {
		    if (file.isFile()) {
		    	String name = file.getName();
		        if(name.contains(".txt")) fileLog.add(name.replace(".txt", ""));
		    }
		}
		return fileLog;
	}


	public static void writeFile(String filename, String contents){
		
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
	
	public static String readFile(String filename){
		
		String content = "";
		try {
			scanner = new Scanner(new File(folder+filename+".txt"));
			content = scanner.useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			return "";
		} catch (NoSuchElementException e) {
			System.out.println("'No such element' of string ending delemeter at "+folder+filename+".txt");
		}
	
		return content;
	}

}
