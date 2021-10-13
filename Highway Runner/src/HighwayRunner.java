import java.awt.Graphics2D;
import java.util.LinkedList;

public class HighwayRunner extends Game{
	
	static Player player;
	int maxRoads = 5;
	Road[] roads;
	
	public static void main(String[] args){
		new HighwayRunner();
	}
	

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		player = new Player();
		createRoads();
	}
	
	void createRoads() {
		roads = new Road[maxRoads];
		
		LinkedList<Integer> roadLocations = new LinkedList<Integer>();
		for(int i=Road.width*2; i<Game.windowHeight-(Road.width*4); i+=Road.width) {
			roadLocations.add(i);
		}
		
		for(int i=0; i<roads.length; i++){
			int randIndex = r.nextInt(roadLocations.size());
			roads[i] = new Road(roadLocations.remove(randIndex));
		}
	}

	@Override
	void runGame() {
		if(checkCollisions()) player.kill();
		player.run();
		for(Road r : roads)
			r.run();
	}
	
	private boolean checkCollisions(){
		
		for(Road currRoad : roads) {
			Car currCar = currRoad.headCar;
			while(currCar != null) {
				if(currCar.checkCollisions(player.x, player.y) || 
						currCar.checkCollisions(player.x+player.width/2, player.y+player.height)) {
					currCar.killedPlayer();
					return true;
				}
				currCar = currCar.next;
			}
		}
		
		return false;
	}

	@Override
	void renderGame(Graphics2D g) {
		for(Road r : roads)
			r.render(g);
		player.render(g);
		for(Road r : roads)
			r.renderCars(g);
	}

}
