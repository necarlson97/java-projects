import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HateBot extends Canvas implements Runnable {
	
	Random r = new Random();

	private boolean isFullScreen =false;
	static boolean running = true;
	Thread hateBotThread;
	int count;

	static int windowWidth;
	static int windowHeight;

	static String inputString = "";
	List<Message> messages = new LinkedList<Message>();
	static Message workingMessage = new UserMessage(">");
	
	List<MessageArt> asciiArt;
	
	List<ResponceGroup> responces;
	MessageGroup defaultLines;
	
	static int hateLevel = 0; // 0-9 (for now)
	MessageGroup[] hateLevels; // 10 arrays (one per hate level) of lists of
							// strings, based on script parsed;

	public static void main(String[] args) {
		new HateBot();
	}

	public HateBot() {
		this.addKeyListener(new KeyInput());
		createWindow();
		loadScripts();

		hateBotThread = new Thread(this);
		hateBotThread.start();
	}

	public void createWindow() {
		if (isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this);
		} else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, "Hate Bot", this);
		}
	}

	@Override
	public void run() {
		while (running) {

			if (!inputString.equals("")) {
				messages.add(new UserMessage(inputString));
				assessInputString();
				inputString = "";
			}
			
			workingMessage.run(count);
			boolean typed = false;
			for(Message m : messages){
				if(m instanceof BotMessage){
					BotMessage b = (BotMessage) m;
					if(!typed &&  b.charIndex < b.fullString.length()){
						b.increment(count);
						typed = true;
					}
				}
				
				m.run(count);
			}
			
			corruptMessages();
			
			if(count % 100 == 0 && !typed) newIdleMessage();

			render();
			count++;
			try {
				hateBotThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void corruptMessages() {
		for(Message m : messages){
			if(m instanceof BotMessage && r.nextInt(200) < hateLevel*hateLevel 
					&& m.drawString.length() > 4) {
				char c = (char)(r.nextInt(90) + '!');
				int split = r.nextInt(m.drawString.length()-1)+1;
				
				String part1 = m.drawString.substring(0, split-1);
				String part2 = m.drawString.substring(split);
				m.drawString = part1+c+part2;
			}
		}
		
		
	}

	private void assessInputString() {
		String assessString = inputString.toLowerCase();
		
		boolean negative = detectNegatives(assessString);
		
		MessageGroup tempResponce = defaultLines;
		for(ResponceGroup r : responces){
			for(String k : r.keys){
				if(negative && assessString.contains(k) ) tempResponce = r.negativeResponce;
				else if(assessString.contains(k)) tempResponce = r;
				
				if(r.name.equals(" Hateful") && assessString.contains(k)) hateLevel++;
			}
		}
		
		BotMessage addMessage = new BotMessage(tempResponce.getLine());
		addMessage.delay = 1;
		messages.add(addMessage);
		
	}

	private boolean detectNegatives(String assessString) {
		String[] negatives = {"no, not, don't"};
		boolean negative = false;
		for(String s : negatives)
			if(assessString.contains(s)) negative = true;
				
		return negative; 
	}

	private void newIdleMessage() {
		messages.add(new BotMessage(hateLevels[hateLevel].getLine()));	
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		renderBackground(g);
		renderMessages(g);

		g.dispose();
		bs.show();

	}

	private void renderBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
	}

	private void renderMessages(Graphics g) {
		int y = 20;
		for (Message m : messages) {
			m.render(g, y);
			y += m.pointSize + 5;
		}
		workingMessage.render(g, y);
		if (y > windowHeight-50)
			messages.remove(0);
	}

	public void loadScripts() {
		hateLevels = ScriptLoader.loadIdleHateLines();
		asciiArt = ScriptLoader.loadAsciiArt();
		defaultLines = ScriptLoader.loadDefaultLines();
		responces = ScriptLoader.loadResponceLines();
	}
	
	private void addArtToScreen(MessageArt art){
		for(Message m : art.messages){
			messages.add(m);
		}
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

}
