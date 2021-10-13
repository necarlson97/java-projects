import java.awt.Graphics2D;
import java.util.Hashtable;
import java.util.LinkedList;

public class Farm extends DisplayEngine1{

	static int rows;
	static int cols;
	static int pixelSize;
	
	static Hashtable<Cell, GameObject> objs = new Hashtable<Cell, GameObject>();
	static LinkedList<Ant> ants = new LinkedList<Ant>();
	
	static final int startingGround = 5;
	
	public Farm(int _rows) {
		rows = _rows;
		pixelSize = windowHeight/rows;
		cols = windowWidth/pixelSize;
	}

	@Override
	public void setup() {
		for(int r = startingGround; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				GameObject go;
				if(c == 0 || c == cols-1 || r == rows-1)
					go = new Wall(r, c);
				else
					go = new Dirt(r, c);
				objs.put(go.cell, go);
			}
		}
		
		ants.add(new Worker(10, 10));
	}

	@Override
	public void update() {
		for(Cell c : objs.keySet())
			objs.get(c).update();
		for(Ant a : ants)
			a.update();
	}

	@Override
	void render(Graphics2D g) {
		for(Cell c : objs.keySet())
			objs.get(c).render(g);
		for(Ant a : ants)
			a.render(g);
	}
	
	public static GameObject get(Cell c) {
		if(Farm.objs.containsKey(c)) return Farm.objs.get(c);
		else return new Dirt(c.row, c.col);
	}

}
