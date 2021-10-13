import java.awt.Color;
import java.awt.Graphics;

public class Triangle {
	
	float x;
	float y;
	float z;
	
	int size = 100;
	Point[] points = new Point[3];
	Color color = Color.blue;
	
	public Triangle(int x, int y, int z){
		initilizePoints(x, y, z);
	}

	private void initilizePoints(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		points[0] = new Point(x-size/2, y+size/2, 20);
		points[1] = new Point(x+size/2, y+size/2, 20);
		points[2] = new Point(x, y-size/2, 20);
	}
	
	public void run(){
		
	}
	
	public void render(Graphics g){
		
		int[] intXs = getCoord(points, 'x');
		int[] intYs = getCoord(points, 'y');
		int[] intZs = getCoord(points, 'z');
				
		g.setColor(color);
		g.drawPolygon(intXs, intYs, points.length);
		g.setColor(Color.WHITE);
		g.drawOval((int) x-size/20, (int) y-size/20, size/10, size/10);
	}

	private int[] getCoord(Point[] getPoints, char coord) {
		int[] coords = new int[getPoints.length];
		
		int i=0;
		for(Point p : getPoints){
			if(coord == 'x') coords[i] = (int) p.x;
			if(coord == 'y') coords[i] = (int) p.y;
			if(coord == 'z') coords[i] = (int) p.z;
			
			i++;
		}
		
		return coords;
	}

}
