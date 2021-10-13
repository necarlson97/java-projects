import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Stripper {

	static String[] inBag;
	static String[] outBag;

	static LinkedList<Card> trainingCards = new LinkedList<Card>();
	static LinkedList<Card> testCards = new LinkedList<Card>();

	Driver driver;
	
	String folder = "cards";

	Stripper(Driver d) {
		driver = d;
		getCards(folder);
	}

	private void getCards(String folder) {
		LinkedList<Path> paths = getPaths(folder);

		LinkedList<List<String>> data = new LinkedList<List<String>>();
		
		Set<String> inSet = new HashSet<String>();
		Set<String> outSet = new HashSet<String>();
		
		try {
			for(Path p : paths)
				data.add(Files.readAllLines(p, Charset.defaultCharset()));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		
		for(List<String> lines: data) {
			for(String l: lines) {
				String[] line = l.split(",");
				
				String[] arr = Card.split(line[0]);
				if(line.length > 1) {
					outSet.add(line[0]);
					arr = Card.split(line[1]);
				}
				
				
				for(int i=0; i<arr.length; i++)
					inSet.add(arr[i]);
			}
		}
		outSet.remove("");
		inBag = inSet.toArray(new String[inSet.size()]);
		outBag = outSet.toArray(new String[outSet.size()]);
		
		for(List<String> lines: data) {
			for(String l: lines) {
				String[] line = l.split(",");
				
				if(line.length == 1) testCards.add(new Card(line[0]));
				else if(line[0].equals("") || line[0] == null) testCards.add(new Card(line[1]));
				else trainingCards.add(new Card(line[1], line[0]));
			}
		}
		
		Collections.shuffle(trainingCards);
		Collections.shuffle(testCards);
	}

	private LinkedList<Path> getPaths(String directory) {
		LinkedList<Path> paths = new LinkedList<>();
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
			for (Path path : directoryStream) { 
				if(path.toString().endsWith(".csv")) paths.add(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return paths;
	}

	public void render(Graphics g) {
		if(inBag != null && outBag !=null) renderBags(g);
		if(trainingCards != null) renderCards(g);
	}

	private void renderBags(Graphics g) {
		g.setColor(Color.WHITE);

		int y = 50;
		int x = 20;
		g.drawString("Output Bag", x, y);
		for(int i=0; i<outBag.length; i++) {
			y += g.getFont().getSize();
			g.drawString(i+" : "+outBag[i], x, y);
		}
		
		y += g.getFont().getSize()*2;
		x = 20;
		g.drawString("Input Bag", x, y);
		for(int i=0; i<inBag.length; i++) {
			y += g.getFont().getSize();
			g.drawString(i+" : "+inBag[i], x, y);
		}
	}

	private void renderCards(Graphics g) {
		int y = 100;
		int x = 120;
		for(int i=0; i<60; i++) {
			if(i >= trainingCards.size()) break;
			g.drawString(trainingCards.get(i).toString(), x, y);
			y += g.getFont().getSize();
		}

		g.drawString("Training Size: "+trainingCards.size(), 120, 50);
		g.drawString("Test Size: "+testCards.size(), 120, 70);

	}

}
