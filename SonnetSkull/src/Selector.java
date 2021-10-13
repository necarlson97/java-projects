import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Selector {

	Sonnet[] sonnets = new Sonnet[154];
	
	Selector() {
		load();
	}

	public void load() {	
		Scanner scan = null;
		try {
			scan = new Scanner(new File("assets/sonnets.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int i=-1;
		String roman = "";
		String poem = "";
		while(scan.hasNext()) {
			String line = scan.nextLine().toLowerCase();

			if(line.matches("^[ivxlc]+\\.")) {
				if(roman != "") sonnets[i] = new Sonnet(i, roman, poem);
				roman = line;
				poem = "";
				i++;
			} else {
				poem += line+"\n";
			}

		}

		sonnets[i] = new Sonnet(i, roman, poem);
	}

	public String get(String s) {
		s = s.trim().toLowerCase();
		System.out.println("Getting: "+s);
		if(s.matches("\\d+")) return get(Integer.parseInt(s));

		int max = 0;
		int maxIndex = -1;
		int c;
		for(int i=0; i<sonnets.length; i++) {

			c = count(sonnets[i].poem, s);
			if(c > max) {
				max = c;
				maxIndex = i;
			}
		}

		if(maxIndex == -1) return "No sonnet containing "+s;
		else return sonnets[maxIndex].poem;
	}

	private String get(int i) {
		if(i < 1 || i > 154) return "No sonnet #"+i;
		else return sonnets[i-1].poem;
	}

	private int count(final String string, final String substring) {
		int count = 0;
		int idx = 0;

		while ((idx = string.indexOf(substring, idx)) != -1) {
			idx++;
			count++;
		}
		
		return count;
	}

}
