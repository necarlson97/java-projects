import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ScriptLoader {
	
	public static MessageGroup[] loadIdleHateLines(){
		MessageGroup[] hateLevels = new MessageGroup[10];

		for (int i = 0; i < hateLevels.length; i++)
			hateLevels[i] = new MessageGroup("Hate Level "+i);

		String fileName = "HateBotIdleScript.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int i = -1;
			while ((line = bufferedReader.readLine()) != null && i <= 10) {
				if (line.contains("Hate Level")) {
					i++;
					continue;
				}
				if(!line.equals("")) hateLevels[i].lines.add(line);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		
		return hateLevels;
	}
	
	public static List<MessageArt> loadAsciiArt(){
		List<MessageArt> asciiArt = new LinkedList<MessageArt>();
		
		String fileName = "AsciiScript.txt";
		String line = null;
		String[] asciiScript = new String[countLines(fileName)];
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int i=0;
			int prevI=0;
			while ((line = bufferedReader.readLine()) != null) {
				asciiScript[i]=line.replaceAll("/n", "");	
				i++;
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		
		int prevI = 1;
		String name = asciiScript[0];
		for(int i=0; i<asciiScript.length; i++){
			if(asciiScript[i].contains("Ascii ") && i!=0){
				asciiArt.add(new MessageArt(name, Arrays.copyOfRange(asciiScript, prevI, i)));
				name = asciiScript[i];
				i++;
				prevI = i;
			}
		}
		asciiArt.add(new MessageArt(name, Arrays.copyOfRange(asciiScript, prevI, asciiScript.length)));
		
		return asciiArt;
	}
	
	private static int countLines(String fileName) {
		int lineCount = 0;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while (bufferedReader.readLine() != null) lineCount++;
			
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		return lineCount;
	}

	public static MessageGroup loadDefaultLines() {
		MessageGroup defaultLines = new MessageGroup("Default");
		
		String fileName = "DefaultScript.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				if(!line.equals("")) defaultLines.lines.add(line);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		
		return defaultLines;
	}

	public static List<ResponceGroup> loadResponceLines() {
		List<ResponceGroup> responces = new ArrayList<ResponceGroup>();
		
		String fileName = "ResponceScript.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			MessageGroup negative = new MessageGroup("NEGATIVE BLANK");
			ResponceGroup positive = new ResponceGroup("POSITIVE BLANK", negative);
			
			boolean pos = true;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.contains("#")){
					negative = new MessageGroup("Negative"+line.substring(1));
					positive = new ResponceGroup(line.substring(1), negative);
					
					responces.add(positive);
				}
				else if(line.contains("Key Words")) addKeys(positive, line);
				else if(line.contains("Positive")) pos = true;
				else if(line.contains("Negative")) pos = false;
				else if(!line.equals("") && pos) positive.lines.add(line);
				else if(!line.equals("") && !pos) negative.lines.add(line);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		
		return responces;
	}

	private static void addKeys(ResponceGroup positive, String line) {
		String all = line.substring(line.indexOf(':')+1);
		for(String k : all.split(","))
			positive.keys.add(k.trim());
		
	}

}
