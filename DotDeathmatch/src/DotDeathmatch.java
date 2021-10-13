import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class DotDeathmatch extends Canvas implements Runnable{
	
	Random r = new Random();
	
	public static int DELAY = 1000 / 60;

	private boolean isFullScreen = false;
	static boolean running = true;
	static boolean debug = true;
	Thread thread;
	int count;

	static int windowWidth;
	static int windowHeight;
	
	static final int MAXDOTS = 10;
	static BotDot[] bots = new BotDot[MAXDOTS];
	
	static final int MAXTEAMS = 2;
	static Team[] teams = new Team[MAXTEAMS];
	
	static final int MAXWALLS = 10;
	Wall[] walls = new Wall[MAXWALLS];
	
	static PlayerDot player;
	
	public static void main(String[] args) {
		new DotDeathmatch();
	}
	
	public DotDeathmatch(){
		this.addKeyListener(new KeyInput());
		
		createWindow();
		
		thread = new Thread(this);
		thread.start();
	}
    
    public void createWindow() {
    	String windowName = this.getClass()+"";
    	windowName = windowName.replace("class ", "");
		if (isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this);
		} else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, windowName, this);
		}
	}
    
    private void createTeams(){
    	int i=0;
    	float rRed = 1;
    	float rBlu = 1;
    	float rGrn = 1;
    	for(Team t : teams){
    		if(t == null) {
    			teams[i] = new Team("Team "+i, i, new Color(rRed, rBlu, rGrn));
    			rRed = (r.nextFloat()+1)/2;
    			rBlu = (r.nextFloat()+1)/2;
    			rGrn = (r.nextFloat()+1)/2;
    		}
    		i++;
    	}
    }
    
	@Override
	public void run() {
		
		createTeams();
		int i=0;
		for(BotDot d : bots){
			if(d==null) bots[i] = new BotDot(r.nextInt(windowWidth), r.nextInt(windowHeight),
					r.nextInt(MAXTEAMS));
			i++;
		}
		player = new PlayerDot(windowWidth/2, windowHeight/2, 0);
		
		while(running){
			
			player.run();
			for(BotDot d : bots){
				if(d!=null) d.run();
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
		
		Graphics g1 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g1;
		g.setStroke(new BasicStroke(3));;
		renderBackground(g);
		
		for(BotDot d : bots){
			if(d!=null) d.render(g);
		}
		player.render(g);
		
		g.dispose();
		bs.show();
	}
	
	private void renderBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		if(debug){
			
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
