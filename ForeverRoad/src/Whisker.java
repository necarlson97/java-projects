import java.awt.Color;
import java.awt.Graphics;

public class Whisker {

	Car car;
	float angleOffset;
	int length;
	
	int x;
	int y;
	
	Whisker(float angleOffset, int length, Car car) {
		this.angleOffset = angleOffset;
		this.car = car;
		this.length = length;
	}
	
	void update() {
		Point p = Car.getLineFromAngle(car.angle + angleOffset, new Point(car.x, car.y), length);
		this.x = (int) p.x;
		this.y = (int) p.y;
	}
	
	void render(Graphics g) {
		g.setColor(Color.white);
		g.drawLine(x, y, (int)car.x, (int)car.y);
		g.setColor(Color.blue);
		g.drawOval(x-10, y-10, 20, 20);
	}
	
	float roadIntersection() {
		float intersect = 1;
		
		Road r = car.fr.road;
		
		Point c = new Point(car.x, car.y);
		Point w = new Point(x, y);
		for(int i=0; i<car.fr.road.xs.length-1; i++) {
			Point r1 = new Point(r.xs[i], r.ys[i]);
			Point r2 = new Point(r.xs[i+1], r.ys[i+1]);
			Point sect = lineSegmentIntersection(c, w, r1, r2);
			if(sect != null) {
				float l = Math.abs(sect.x - c.x) / Math.abs(sect.y - c.y);
				if(l < length) return l / length;
			}
		}
		
		return intersect;
	}
	
	Point lineSegmentIntersection(Point p1, Point p2, Point q1, Point q2) {
		Point s1 = new Point(p2.x - p1.x, p2.y - p1.y);
		Point s2 = new Point(q2.x - q1.x, q2.y - q1.y);
		
		float t = (-s1.y * (p1.x - q1.x) + s1.x * (p1.y - q1.y)) / (-s2.x * s1.y + s1.x * s2.y);
		float s = ( s2.x * (p1.y - q1.y) - s2.y * (p1.x - q2.x)) / (-s2.x * s1.y + s1.x * s2.y);
		
		if( s >= 0 && s <= 1 && t >=0 && t <= 1)
			return new Point(p1.x + (t * s1.x), p1.y + (t * s1.y));
		
		return null;
	}
	
	
}
