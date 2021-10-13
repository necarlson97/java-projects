import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.util.LinkedList;

public class FileSelect {
	
	int selected=0;
	LinkedList<String> fileNames;
	Font font;
	int fontSize;
	
	public FileSelect() {
		fontSize = (int) (Game.windowHeight*.03);
		font = new Font("Press Start 2p", Font.PLAIN, fontSize);
		fileNames = loadFileNames();
	}

	private LinkedList<String> loadFileNames() {
		File[] files = new File(".").listFiles();
		LinkedList<String> fileNames = new LinkedList<String>();
		
		for(File f : files){
			String fName = f.getName();
			if(fName.contains(".mid")) fileNames.add(fName);
		}
		return fileNames;
	}

	public void render(Graphics2D g) {
		g.setFont(font);

		int x = (int) (Game.windowWidth*.1);
		int y = (int) (Game.windowWidth*.1);
		
		int i=0;
		for(String f : fileNames) {
			g.setColor(Color.white);
			if(i == selected) {
				g.fillRect(x-5, y-fontSize-5, f.length()*fontSize+5, fontSize+10);
				g.setColor(Color.black);
				g.drawString(f, x, y);
			} else {
				g.drawString(f, x, y);
			}
			
			y+=fontSize;
			i++;
		}
		
		
	}

	public void up() {
		if(selected > 0) selected --;
	}

	public void down() {
		if(selected < fileNames.size()-1) selected ++;
	}
	
	public String enter() {
		return fileNames.get(selected);
	}
	
	

}
