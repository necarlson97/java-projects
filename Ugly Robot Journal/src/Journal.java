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
	
	static Date date = new Date();
	static DateFormat formatter;
	static Calendar calendar;
	
	static FileLoader fileLoader = new FileLoader();
	
	Color offWhite = new Color(210, 210, 210);
	
	int partitionX = (int) (windowWidth*.3);
	static String[] typeFaces = {"Press Start 2P", "Courier", "Monaco"};
	static int selectedType = 0;
	
	static int margin;
	
	static int headerPointSize;
	static int bodyPointSize;
	static int titlePointSize;
	
	static Font bodyFont;
	static Font headerFont;
	static Font titleFont;
	
	static boolean textUp = false; 
	static boolean textDown = false; 
	
	static boolean dateUp = false; 
	static boolean dateDown = false; 
	
	static int entryYBase;
	static float entryY;
	float entryYVel = 0;
	
	float entrySpeed = (float) .2;
	
	int entryLineCutoff = (int) (windowWidth * .95);
	
	static int dateStart = -10;
	static String selectedDate = "";
	
	static String entryString;
	
	static int loading = 0;
	static boolean stillLoading = true;
	static int loadingMax = 100;
	float drawLoading = 0;
		
	public static void main(String[] args) {
		new Journal();
	}

	@Override
	void setup() {
		formatter = new SimpleDateFormat("dd MMM yyyy");
		calendar = Calendar.getInstance(); 
		
		formatter.setTimeZone(TimeZone.getTimeZone("EST")); 
		
		margin = windowWidth / 100;
		entryYBase = margin*8;
		entryY = entryYBase;
		
		headerPointSize =  (int) (windowWidth*.025);
		bodyPointSize = (int) (windowWidth*.02);
		titlePointSize = (int) (windowWidth*.03);
		
		bodyFont = new Font(typeFaces[selectedType], Font.PLAIN, bodyPointSize);
		headerFont = new Font(typeFaces[selectedType], Font.PLAIN, headerPointSize);
		titleFont = new Font(typeFaces[selectedType], Font.PLAIN, titlePointSize);
		
		load();
		
	}

	@Override
	void runGame() {
		
		if(count%800==0) save();
		
		if(stillLoading) {
			if(count%(r.nextInt(20)+1)==0 && loading < loadingMax) loading ++;
			handleDrawLoading();
		}
		else {
			upAndDown();
			depreciateVelocity();
			
			entryY += entryYVel;
		}
		
	}
	
	static void load(){
		calendar.setTime(date); 
		calendar.add(Calendar.DATE, dateStart+10);
		selectedDate = formatter.format(calendar.getTime());
		
		entryString = fileLoader.readFile(selectedDate);
	}
	
	static void save(){
		fileLoader.writeFile(selectedDate, entryString);
	}
	
	private void upAndDown(){
		if(textUp && entryYVel > -1.5) entryYVel-= entrySpeed;
		else if(textDown && entryYVel < 1.5) entryYVel+= entrySpeed;

	}
	
	private void depreciateVelocity() {
		double resistance = 1.1;
		if(entryYVel < 0) entryYVel/=resistance;
		else if(entryYVel > 0) entryYVel/=resistance;
		if(Math.abs(entryYVel) < .01 ) entryYVel = 0;
	}
	
	private void handleDrawLoading() {
		if(drawLoading < loading) drawLoading += (loading-drawLoading)*.08; 
	}

	@Override
	void renderGame(Graphics2D g) {		
		
		g.setStroke(new BasicStroke((float) JournalUtils.oscillateNumber(count, 100, 6)));
		
		if(debug) {
			g.setColor(Color.red);
			g.drawLine(entryLineCutoff, 0, entryLineCutoff, windowHeight);
		}
			
		if(loading > loadingMax*.3) renderDates(g);
		if(loading > loadingMax*.6)renderEntry(g);
		
		if(stillLoading) renderLoading(g);
	
	}

	private void renderLoading(Graphics g) {
		
		g.setColor(Color.black);
		int height = windowHeight;
		if(debug) height = (int) (windowWidth * (1 - ((float)loading/loadingMax)));
		g.fillRect(0, 0, windowWidth, height);
		
		int loadingBarY = (int) (windowHeight*.75);
		
		g.setColor(offWhite.darker());
		g.drawLine(0, loadingBarY, windowWidth, loadingBarY);
		
		g.setColor(Color.WHITE);
		g.drawLine(0, loadingBarY, loading*margin, loadingBarY);
		if(debug) g.drawString((int)loading+"%", loading*margin, loadingBarY);
		
		g.setColor(Color.PINK);
		g.drawLine(0, loadingBarY, (int) (drawLoading*margin), loadingBarY);
		
		renderHelpTest(g);
		renderBotLogo(g);
	
	}
	
	private void renderHelpTest(Graphics g) {
		
		
		int intX = margin*60;
		int intY = margin*25;
		
		g.setFont(headerFont);
		g.setColor(Color.WHITE);
		
		g.drawString("scroll text", intX, intY);
		intY += headerPointSize + margin;
		g.drawString("change date", intX, intY);
		intY += headerPointSize + margin;
		g.drawString("goto today", intX, intY);
		intY += headerPointSize + margin;
		g.drawString("to continue", intX, intY);
		
		g.setFont(bodyFont);
		g.setColor(Color.pink);
		
		intY = margin*25;
		intX = margin*60-(bodyPointSize*"▲ ▼  ".length());
		g.drawString("▲ ▼", intX, intY);
		
		intY += headerPointSize + margin;
		intX = margin*60-(bodyPointSize*"shift  ▲ ▼  ".length());
		g.drawString("shift  ▲ ▼", intX, intY);
		
		intY += headerPointSize + margin;
		intX = margin*60-(bodyPointSize*"shift enter ".length());
		g.drawString("shift enter", intX, intY);
		
		intY += headerPointSize + margin;
		intX = margin*60-(bodyPointSize*"enter ".length());
		g.drawString("enter", intX, intY);
		
		
		
		g.setFont(titleFont);
		
		g.setColor(Color.white);
		int shadowY = (int) (margin*(15+JournalUtils.oscillateNumber(count, 60, .4)));
		g.drawString("Ugly Robot Journal", margin*30, shadowY);
		
		int textY = (int) (margin*(15+JournalUtils.oscillateNumber(count, 60, .8)));
		g.setColor(Color.pink);
		g.drawString("Ugly Robot Journal", margin*30, textY);
		
		
	}

	private void renderBotLogo(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(margin*5, margin*20, margin*20, margin*20);
	
		g.drawLine(margin*10, margin*20, margin*10, margin*40);
		g.drawLine(margin*15, margin*10, margin*15, margin*40);
		g.drawLine(margin*20, margin*20, margin*20, margin*40);
		
		g.drawLine(margin*5, margin*25, margin*25, margin*25);
		g.drawLine(margin*5, margin*30, margin*25, margin*30);
		g.drawLine(margin*5, margin*35, margin*25, margin*35);
		
		// Fill robo head
		g.setColor(offWhite.darker());
		int height = (int) (margin*20 * (drawLoading/ loadingMax));
		g.fillRect(margin*5, margin*40-height, margin*20, height);
		if(debug) {
			height = (int) (margin*20 * ((float)loading/ loadingMax));
			g.setColor(Color.RED);
			g.drawRect(margin*5, margin*40-height, margin*20, height);
		}
		
		g.setColor(Color.pink);
		g.fillOval((int) (margin*7.5), (int) (margin*22.5), margin*5, margin*5);
		g.fillOval((int) (margin*17.5), (int) (margin*22.5), margin*5, margin*5);
		
		g.fillOval((int) (margin*13.75), (int) (margin*8.75), (int) (margin*2.5), (int) (margin*2.5));
		
		g.fillRect(margin*10, (int) (margin*32.5), margin*10, margin*5);
		
		
	}

	void renderDates(Graphics g){
		int dateNumb = (windowHeight - margin) / (headerPointSize + margin);
		
		g.setFont(headerFont);
		g.setColor(offWhite.darker());
		
		calendar.setTime(date); 
		calendar.add(Calendar.DATE, dateStart);
		
		int intY = margin*3;
		for(int i=0; i<dateNumb; i++) {
			if(i==0 || i==dateNumb-1)  g.setColor(offWhite.darker().darker());
			else g.setColor(offWhite.darker());
			
			
			if(calendar.getTime().equals(date)) 
				g.setColor(Color.pink);
			
			if(i == 10) {
				g.setColor(Color.pink);
				g.fillRect(0, intY-headerPointSize-margin/2, partitionX, headerPointSize+margin);
				g.setColor(Color.BLACK);
				g.drawString(formatter.format(calendar.getTime()), margin, intY);
			}
			else 
				g.drawString(formatter.format(calendar.getTime()), margin, intY);
			
			calendar.add(Calendar.DATE, 1);
			intY += headerPointSize+margin;
		}

	}
	
	private void renderEntry(Graphics g) {
		
		g.setColor(offWhite);
		g.setFont(bodyFont);
		int intY = (int) (entryY);
		int intX = partitionX+margin*2;
		
		for(String w : entryString.split("((?<=[ \\n])|(?=[ \\n]))")){
			
			if ( needNewLine(w, intX) ) {
				intX = partitionX+margin*2;
				intY += bodyPointSize+margin;
				if(w.contains("\n")) {
					if(debug) g.drawOval(intX, intY, margin, margin);
					continue;
				}
			}
			
			if(debug) g.drawLine(intX+margin/2, intY, intX+w.length()*bodyPointSize-margin/2, intY);
			g.drawString(w, intX, intY);
			
			intX += w.length()*bodyPointSize;
			
		}
		int gray = (int) JournalUtils.oscillateNumber(count, 100, 225);
		g.setColor(new Color(gray, gray, gray));
		g.fillRect(intX, intY-bodyPointSize, margin, bodyPointSize);
		
		g.setColor(Color.black);
		g.fillRect(partitionX, 0, windowWidth-partitionX, margin*5);
		
		g.setFont(headerFont);
		
		g.setColor(Color.white);
		
		g.drawLine(partitionX, 0, partitionX, windowHeight);
		
		calendar.setTime(date); 
		g.drawString(selectedDate, partitionX+margin, margin*5);
		
	}
	
	private boolean needNewLine(String w, int intX){
		return w.contains("\n") || 
				(intX + w.length()*bodyPointSize > entryLineCutoff &&
				intX != partitionX+margin*2);
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
	
}
