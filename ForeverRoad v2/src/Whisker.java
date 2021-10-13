import java.awt.Color;
import java.awt.Graphics;

public class Whisker {

	Car car;
	float angleOffset;
	int length;
	
	int x;
	int y;
	
	Point sect;
	
	float intersection;
	
	Whisker(float angleOffset, int length, Car car) {
		this.angleOffset = angleOffset;
		this.car = car;
		this.length = length;
		sect = Car.getLineFromAngle(car.angle + angleOffset, new Point(car.x, car.y), length);
		this.x = (int) sect.x;
		this.y = (int) sect.y;
	}
	
	void update() {
		Point p = Car.getLineFromAngle(car.angle + angleOffset, new Point(car.x, car.y), length);
		this.x = (int) p.x;
		this.y = (int) p.y;
		intersection = roadIntersection();
		System.out.println(intersection);
	}
	
	void render(Graphics g) {
		g.setColor(Color.white);
		g.drawLine(x, y, (int)car.x, (int)car.y);
		g.setColor(Color.blue);
		if(sect != null)
			g.fillOval((int)sect.x-5, (int)sect.y-5, 10, 10);		
	}
	
	private float roadIntersection() {	
		Road r = car.road;
		
		Point c = new Point(car.x, car.y);
		Point w = new Point(x, y);
		for(int i=0; i<r.xs.length-1; i++) {
			Point r1 = new Point(r.xs[i], r.ys[i]);
			Point r2 = new Point(r.xs[i+1], r.ys[i+1]);
			if((i < r.res && c.x < r1.x) || (i >= r.res && c.x > r2.x)) {
				sect = c;
				return 0;
			}
			sect = lineSegmentIntersection(w, c, r1, r2);
			if(sect != null) {
				float l = Math.abs(sect.x - c.x) / Math.abs(sect.y - c.y);
				System.out.println("\t"+l);
				if(l > 0 && l < length) return l / length;
				else return (float)Math.abs(r1.x - c.x)/ length;
			}
		}
		sect = new Point(x, y);
		if(y < 0) {
			sect.y = 0;
			float l = Math.abs(sect.x - c.x) / Math.abs(sect.y - c.y);
			if(l > 0 && l < length) return l / length;
		}
		return 1;
	}
	
	Point lineSegmentIntersection(Point p1, Point p2, Point q1, Point q2) {
		Point s1 = new Point(p2.x - p1.x, p2.y - p1.y);
		Point s2 = new Point(q2.x - q1.x, q2.y - q1.y);
		
		float t = (-s1.y * (p1.x - q1.x) + s1.x * (p1.y - q1.y)) / (-s2.x * s1.y + s1.x * s2.y);
		float s = ( s2.x * (p1.y - q1.y) - s2.y * (p1.x - q2.x)) / (-s2.x * s1.y + s1.x * s2.y);
		
		if( s >= 0 && s <= 1 && t >=0 && t <= 1)
			return new Point(p1.x + (s * s1.x), p1.y + (s * s1.y));
		
		return null;
	}
	
	
}
