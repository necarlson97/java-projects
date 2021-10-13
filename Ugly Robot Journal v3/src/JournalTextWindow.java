import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public abstract class JournalTextWindow {
	
	static int headerPointSize = Journal.headerPointSize;
	static int bodyPointSize = Journal.bodyPointSize;
	
	static Font headerFont = Journal.headerFont;
	static Font bodyFont = Journal.bodyFont;
	
	static int bodyLetterWidht = Journal.bodyPointSize;
	
	static int windowWidth = Journal.windowWidth;
	static int windowHeight = Journal.windowHeight;
	static int margin = Journal.margin;
	
	static Color offWhite = Journal.offWhite;
	
	int partitionX = Journal.partitionX;
	
	static boolean debug;
	static int count;
	
	public void updateFonts(){

	}
	
	public void run() {
		debug = Journal.debug;
		count = Journal.count;
	}
	
	public abstract void render(Graphics g);

}
