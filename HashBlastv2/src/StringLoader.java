import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public class StringLoader {

	static String[] load(String name) {
		Scanner scan = null;
		LinkedList<String> words = new LinkedList<String>();
		try {
			scan = new Scanner(new FileReader(name+".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		while(scan.hasNext()) {
			words.add(scan.nextLine());
		}
		
		return words.toArray(new String[words.size()]);
	}
}
