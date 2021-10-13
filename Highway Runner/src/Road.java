import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Road {
	Random r = new Random();
	
	static int width = 20;
	
	int location;
	boolean horizontal = true;
	
	static int maxCars = 5;
	Car headCar;
	
	public Road(int location){
		this.location = location;
		headCar = new Car(horizontal, location, 0, this);
		Car currCar = headCar;
		for(int i=1; i<maxCars; i++){
			currCar.next = new Car(horizontal, location, i, this);
			currCar = currCar.next;
		}
	}
	
	public void run(){
		Car currCar = headCar;
		while(currCar != null) {
			currCar.run();
			currCar = currCar.next;
		}

	}
	
	public void render(Graphics g){
		
		g.setColor(new Color((float).2, (float).2, (float).2));
		if(horizontal) {
			g.fillRect(0, location, Game.windowWidth, width);
			g.setColor(Color.white);
			g.drawRect(0, location, Game.windowWidth, width);
		}
		else {
			g.fillRect(location, 0, width, Game.windowHeight);
			g.setColor(Color.white);
			g.drawRect(location, 0, width, Game.windowHeight);
		}
		
	}
	
	public void renderCars(Graphics g) {
		Car currCar = headCar;
		while(currCar != null) {
			currCar.render(g);
			currCar = currCar.next;
		}
	}

}
