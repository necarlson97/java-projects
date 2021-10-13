import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Display extends Engine2D{
	
	static Setting setting;
	static Player player;
	
	Bot[] bots;
	
	boolean debug;
	
	static float pixelPerMeter; 

	@Override
	public void setup() {
		setting = new Setting();
		player = new Player();
		pixelPerMeter = (float) (player.plane.sprites[0].getWidth() / player.plane.length);
	}

	@Override
	public void update() {
		if(setting != null) setting.update(); 
		if(player != null) player.update();
	}

	@Override
	void render(Graphics2D g) {
		if(setting != null) setting.render(g); 
		if(player != null) player.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(player != null) player.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(player != null) player.keyReleased(e);
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		if(key == KeyEvent.VK_F1) debug = true;
	}
	
	static Point cameraLocation() {
		float x = player.plane.loc.x * pixelPerMeter;
		float y = player.plane.loc.y * pixelPerMeter;
		return new Point(x, y);
	}
	
	public static void main(String[] args) {
		new Display();
	}

}
