import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class ForeverRoad extends Engine2D{
	
	Road road;
	Car car;
	Player player;
	Net net;
	NetBreeder netBreeder;
	
	int roadResolution = 50;
	
	boolean debug;
	
	Color bg = new Color(255, 200, 150);
	
	InfoPannel ip;
	
	int carDeaths = 0;

	@Override
	public void setup() {
		newSetup();
	}
	
	void newSetup() {
		road = new Road(roadResolution, this);
		car = new Car(this);
		net = new Net(5, 2, 20, 2);
		ip = new InfoPannel(this);
		
	}

	@Override
	public void update() {
		if(road != null) road.update();
		if(car != null) car.update();
		if(player != null) player.update();
	}

	@Override
	void render(Graphics2D g) {
		renderBG(g);
		if(road != null) road.render(g);
		if(car != null) car.render(g);
		if(player != null) player.render(g);
		if(ip != null) ip.render();
	}

	private void renderBG(Graphics2D g) {
		g.setColor(bg);
		g.fillRect(0, 0, windowWidth, windowHeight);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(player == null) player = new Player(this);
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		if(key == KeyEvent.VK_F1) debug = true;
		if(player != null && car != null) player.carKeysPressed(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_R) car = new Car(this);
		if(player != null && car != null) player.carKeysReleased(key);
		
	}
	
	public static void main(String[] args) {
		new ForeverRoad();
	}
	
	void carDeath() {
		carDeaths++;
		car = new Car(this);
		player = null;
	}

}
