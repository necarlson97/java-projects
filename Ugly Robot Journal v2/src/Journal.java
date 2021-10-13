import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Journal extends Game{
	
	static FileLoader fileLoader = new FileLoader();
	
	static Color offWhite = new Color(210, 210, 210);
	
	static int partitionX;
	static String[] typeFaces = {"Press Start 2P", "Courier", "Monaco"};
	static int selectedType = 0;
	
	static int margin;
	
	static int titlePointSize;
	static int headerPointSize;
	static int bodyPointSize;
	
	static Font titleFont;
	static Font bodyFont;
	static Font headerFont;
		
	
	static Entry entry;
	static DateSidebar sidebar;
	static LoadingScreen loadingScreen;
	
	public static void main(String[] args) {
		new Journal();
	}

	@Override
	void setup() {

		margin = windowWidth / 100;
		
		partitionX = (int) (windowWidth*.3);
		
		headerPointSize =  (int) (windowWidth*.025);
		bodyPointSize = (int) (windowWidth*.02);
		titlePointSize = (int) (windowWidth*.03);
		
		bodyFont = new Font(typeFaces[selectedType], Font.PLAIN, bodyPointSize);
		headerFont = new Font(typeFaces[selectedType], Font.PLAIN, headerPointSize);
		titleFont = new Font(typeFaces[selectedType], Font.PLAIN, titlePointSize);
		
		loadingScreen = new LoadingScreen();
	}

	@Override
	void runGame() {
		
		if(loadingScreen != null) loadingScreen.run();
		if(entry != null) entry.run();
		if(sidebar != null) sidebar.run();
		
	}

	@Override
	void renderGame(Graphics2D g) {		
		
		//g.setStroke(new BasicStroke((float) JournalUtils.oscillateNumber(count, 100, 6)));
		
		if(loadingScreen != null) loadingScreen.render(g);
		else {
			if(entry != null) entry.render(g);
			if(sidebar != null) sidebar.render(g);
		}
		
	}
	
	public static void nextFont(){
		selectedType = (selectedType+1) % typeFaces.length;

		bodyFont = new Font(typeFaces[selectedType], Font.PLAIN, bodyPointSize);
		headerFont = new Font(typeFaces[selectedType], Font.PLAIN, headerPointSize);
		titleFont = new Font(typeFaces[selectedType], Font.PLAIN, titlePointSize);
	}
	
	public static void changeFontSize(int change){
		bodyPointSize+=change;
		headerPointSize+=change;
		titlePointSize+=change;
	
		bodyFont = new Font(typeFaces[selectedType], Font.PLAIN, bodyPointSize);
		headerFont = new Font(typeFaces[selectedType], Font.PLAIN, headerPointSize);
		titleFont = new Font(typeFaces[selectedType], Font.PLAIN, titlePointSize);
	}
	
	public static double oscillateNumber(int numb, int period, double scale) {
	    return Math.sin(numb*2*Math.PI/period)*(scale/2) + (scale/2);
	}
	
}
