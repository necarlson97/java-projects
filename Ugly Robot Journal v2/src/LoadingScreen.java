import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class LoadingScreen extends JournalTextWindow{
	
	Font titleFont;
	
	static int loading = 0;
	static boolean stillLoading = true;
	static int loadingMax = 100;
	float drawLoading = 0;
	
	public LoadingScreen(){
		titleFont = Journal.titleFont;
	}

	@Override
	public void run() {
		super.run();
		if(loading < loadingMax) loading++;
		if(loading > 30 && Journal.sidebar == null) Journal.sidebar = new DateSidebar();
		if(loading > 60 && Journal.entry == null) Journal.entry = new Entry(Journal.sidebar.selectedDateName());
		handleDrawLoading();
	}
	
	private void handleDrawLoading() {
		if(drawLoading < loading) drawLoading += (loading-drawLoading)*.08; 
	}

	@Override
	public void render(Graphics g) {
		
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
		
		renderHelpText(g);
		renderBotLogo(g);
	
	}
	
	private void renderHelpText(Graphics g) {
	
		g.setFont(titleFont);
		
		g.setColor(Color.white);
		int shadowY = (int) (margin*(15-Journal.oscillateNumber(count, 60, .4)));
		g.drawString("Ugly Robot Journal v2", margin*30, shadowY);
		
		int textY = (int) (margin*(15-Journal.oscillateNumber(count, 60, .8)));
		g.setColor(Color.pink);
		g.drawString("Ugly Robot Journal v2", margin*30, textY);
		
		int intX = margin*60;
		int intY;
		int leftX;
		
		// Fully loaded continue prompt
		if(loading >= loadingMax) {
			g.setFont(bodyFont);
			g.setColor(Color.white);
			leftX = intX - ("press enter ".length()*bodyPointSize);
			
			intY = (int) (margin* (25 - Journal.oscillateNumber(count, 40, .3)));
			intY += (headerPointSize+margin)*3;
			
			g.drawString("press enter ", leftX, intY);
			
			g.setFont(headerFont);
			g.setColor(Color.pink);
			
			g.drawString("to continue ", intX, intY);
		}
		
		intY = (int) (margin* (25 - Journal.oscillateNumber(count, 40, .6)));
		
		// Right lables
		g.setFont(headerFont);
		g.setColor(Color.WHITE);
		
		String[] rightLables = {"scroll text", "change date", "goto today", "to continue"};
		for(String s : rightLables) {
			g.drawString(s, intX, intY);
			intY += headerPointSize+margin;
		}
		
		
		intY = (int) (margin* (25 - Journal.oscillateNumber(count, 40, .6)));
		
		// Left lables
		g.setFont(bodyFont);
		g.setColor(Color.pink);
		String[] leftLables = {"▲ ▼  ", "shift  ▲ ▼  ", "shift enter ", "press enter "};
		for(String s : leftLables) {
			leftX = intX - (s.length()*bodyPointSize);
			g.drawString(s, leftX, intY);
			intY += headerPointSize+margin;
		}
		
		
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

}
