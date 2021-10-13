import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class Operator implements Comparable<Operator> {
	
	Road road;
	Car car;
	
	int roadRes = 40;
	
	boolean left;
	boolean right;
	boolean up;
	boolean down;
	
	float turnChange = (float) 0.1;
	float accelChange = (float) 0.1;
	
	Font font = Display.font;
	
	int tempSuccess;
	int success;
	
	Operator(Car car, Road road) {
		this.car = car;
		this.road = road;
	}
	
	Operator() {
		road = new Road(roadRes, this);
		car = new Car(road, this);
	}
	
	abstract void getInputs();
	
	void update() {
		if(road != null) road.update();
		if(car != null) { 
			getInputs();
			if(left) car.turn -= turnChange;
			else if(right) car.turn += turnChange;
			if(up) car.accel -= accelChange;
			else if(down) car.accel += accelChange;
			
			car.update();
			tempSuccess = car.distance;
		}
	}
	
	void render(Graphics g) {
		if(road != null) road.render(g);
		if(car != null) car.render(g);
		renderOperator(g);
	}
	
	void renderOperator(Graphics g) {
		int fs = font.getSize();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, fs*10, fs*4);
		g.setFont(font);
		g.setColor(Color.white);
		if(left) g.drawString("left", fs, fs);
		else if(right) g.drawString("right", fs, fs);
		if(up) g.drawString("up", fs*5, fs);
		else if(down) g.drawString("down", fs*5, fs);
		
		g.drawString("Dist: "+tempSuccess, fs, fs*2);
		g.drawString("Success: "+success, fs, fs*3);
	}

	void drive() {
		while(!car.dead) {
			update();
			if(tempSuccess > 100000) {
				System.out.println("Great job!");
				car.dead = true;
			}
		}
		car = new Car(road, this);
	}
	
	void carKeysPressed(int key) {
		if(this instanceof Bot) return;
		if(key == KeyEvent.VK_LEFT) left = true;
		if(key == KeyEvent.VK_RIGHT) right = true;
		if(key == KeyEvent.VK_UP) up = true;
		if(key == KeyEvent.VK_DOWN) down = true;
	}
	
	void carKeysReleased(int key) {
		if(this instanceof Bot) return;
		if(key == KeyEvent.VK_LEFT) left = false;
		if(key == KeyEvent.VK_RIGHT)right = false;
		if(key == KeyEvent.VK_UP) up = false;
		if(key == KeyEvent.VK_DOWN) down = false;
	}
	
	@Override
	public int compareTo(Operator o) {
		if(this.success < o.success) return 1;
		else if(this.success == o.success) {
			if(this.tempSuccess < o.tempSuccess) return 1;
			else if(this.tempSuccess == o.tempSuccess) return 0;
			else return -1;
		}
		else return -1;
	}
}
