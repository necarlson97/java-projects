import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {
	public static final int WIDTH = BottledShip.WIDTH, HEIGHT = BottledShip.HEIGHT;
	BottledObject ship;
	Barrier[] barriers = new Barrier[5];
	ArrayList<WaterDrop> waterDrops = new ArrayList<WaterDrop>();
	ArrayList<BottledObject> allObjects = new ArrayList<BottledObject>();
	
	
	public Handler(){
		ship = new Ship(this, 200, 400);
		barriers[1] = new Barrier(this, 50, HEIGHT-100, WIDTH-100, 50);
		
		for(int w=0; w<100; w++){
			waterDrops.add(new WaterDrop(this, w*10, 50));
		}
		for(int w=1; w<100; w++){
			waterDrops.add(new WaterDrop(this, w*10, 75));
		}
		for(int w=0; w<100; w++){
			waterDrops.add(new WaterDrop(this, w*10, 100));
		}
		
		allObjects.add(ship);
		for(int i=0; i<barriers.length; i++){
			if(barriers[i] != null) allObjects.add(barriers[i]);
		}
		allObjects.addAll(waterDrops);
	}

	public void run() {
		
		for(int i=0; i<allObjects.size(); i++){
			allObjects.get(i).run();
		}
		
	}

	public void render(Graphics g) {
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i=0; i<allObjects.size(); i++){
			allObjects.get(i).render(g);
		}
		
	}

}
