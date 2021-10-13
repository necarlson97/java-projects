import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterTyper extends Canvas implements Runnable{
	
	Random r = new Random();
	
	String[] selectUsers = {"syd_rob25", "manda_sampson", "realDonaldTrump", "MeganAmram", "Lesdoggg", "DannyZuker"};
	
	static String typeFace = "Press Start 2P";
	
	public static int DELAY = 1000 / 60;
	
	public static int score = 0;

	private boolean isFullScreen = false;
	static boolean running = true;
	static boolean debug = false;
	Thread thread;
	int count;
	
	static boolean speedUp;

	static int windowWidth;
	static int windowHeight;
	
	LinkedList<TweetEnemy> enemies = new LinkedList<TweetEnemy>();
	
	TweetLoader tweetLoader;
	
	
	int batch = 1;
	
	static char inputChar = ' ';
	static boolean changeChar = false;
	
	boolean loading = true;
	
	public static void main(String[] args) {
		new TwitterTyper();
	}
	
	public TwitterTyper(){
		this.addKeyListener(new KeyInput());
		
		tweetLoader = new TweetLoader();
		createWindow();
		
		thread = new Thread(this);
		thread.start();
	}
    
    public void createWindow() {
		if (isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this);
		} else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, "Twitter Typer", this);
		}
	}
    
	@Override
	public void run() {

		
		while(running){
			
			if(enemies.size() < tweetLoader.BATCHSIZE) {
				String randUsername = selectUsers[r.nextInt(selectUsers.length)];
				enemies.addAll( tweetLoader.requestEnemies(randUsername) );
			}
			
			if(enemies.isEmpty()) {
				loading = true;
			}
			else {
				loading = false;
				
				for(TweetEnemy t : enemies) {
					if(speedUp) t.boost = 1;
					t.run();
				}
				
				if(enemies.getFirst().nextIndex < 0) enemies.getFirst().nextIndex = 0;
				
				if(enemies.getFirst().y > windowHeight || enemies.getFirst().finished) {
					enemies.poll();
				}
				
				if(inputChar == 10) { // Enter key pressed
					enemies.poll();
					inputChar = ' ';
				}
				
				if(changeChar) enemies.getFirst().lastChar = inputChar;
				changeChar = false;
			}

			render();
			waitForNextFrame();
			count++;
		}
		
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		renderBackground(g);
		renderHUD(g);
		
		if(loading) {
			rendderLoading(g);
		}
		else {
			for(TweetEnemy t : enemies)
				t.render(g);
		}
	
		renderLastTyped(g);
		
		g.dispose();
		bs.show();
	}
	
	private void rendderLoading(Graphics g) {
		int pointSize =  (int) (windowWidth*.1);
		Font font = new Font(typeFace, Font.PLAIN, pointSize);
		g.setFont(font);
		
		g.setColor(Color.white);
		g.drawString("Loading...", (windowWidth/2)-(pointSize*5), (windowHeight/2));
		
	}

	private void renderLastTyped(Graphics g) {
		int pointSize =  (int) (windowWidth*.1);
		Font font = new Font(typeFace, Font.PLAIN, pointSize);
		g.setFont(font);
		
		g.setColor(Color.white);
		g.drawString(""+inputChar, (windowWidth/2)-(pointSize/2), windowHeight - pointSize);	
		
	}
	
	private void renderHUD(Graphics g) {
		int pointSize =  (int) (windowWidth*.03);
		Font font = new Font(typeFace, Font.PLAIN, pointSize);
		g.setFont(font);
		
		g.setColor(Color.white);
		String scoreString = ""+score;
		g.drawString(scoreString, windowWidth - (pointSize*scoreString.length()), pointSize);	
		if(speedUp) g.drawString("Speeding up...", 0, windowHeight - pointSize);	
		
	}
	
	private void renderBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		if(debug){
			g.setColor(new Color(100, 100, 255));
			g.drawRect((int)(windowWidth*.1), (int)(windowHeight*.1), 
					(int)(windowWidth*.8), (int)(windowHeight*.8));
			
			g.setColor(Color.red);
			g.drawLine((int)(windowWidth*.6), 0, (int)(windowWidth*.6), windowHeight);
		}
		
	}

	private void waitForNextFrame() {
		long tillNextFrame = DELAY - System.currentTimeMillis()%DELAY;
		if(tillNextFrame<0) return;
		try {
			thread.sleep(tillNextFrame);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	
}
