import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public abstract class Dot {
	
	Random r = new Random();
	
	float x;
	float y;
	
	float angle;
	
	int size = 10;
	int tipLength = size*2;
	
	int moveSpeed = 2;
	float turnSpeed = (float) .02;
	
	int viewDistance = size*20;
	float viewAngle = (float) .4;
	
	Team team;
	Color color;
	
	boolean forward;
	boolean left;
	boolean right;
	
	int count;
	
	int ammo = 6;
	Point shot = null;
	
	Queue<Dot> sightedDots;
	
	public Dot(int x, int y, int teamId) {
		this.x = x;
		this.y = y;
		
		team = DotDeathmatch.teams[teamId];
		color = team.color;
	}
	
	public void run(){
		
		move();
		sightedDots = dotsInSight(); 
		
		count++;
	}
	
	public void shoot(){
		if(ammo > 0) {
			ammo--;
			float stray = (float) (r.nextFloat()*viewAngle/2 - viewAngle/4);
			shot = getLineFromAngle(angle + stray, new Point(x, y), viewDistance*2);
		}	
	}


	public void move(){
		if(left && right) {
			left = false;
			right = false;
		}
		
		else if(left) angle = turnAngle(-turnSpeed);
		else if(right) angle = turnAngle(+turnSpeed);
		
		if(forward) {
			Point temp = getLineFromAngle(angle, new Point(x, y), moveSpeed);
			x = temp.x;
			y = temp.y;
		}
	}
	
	public Queue<Dot> dotsInSight(){ 
		
		Queue<Dot> sightedDots = new LinkedList<Dot>();
		
		
		for(Dot d : DotDeathmatch.bots){
			if( isInSight(d) ) sightedDots.add(d);
		}
		if(DotDeathmatch.player != this && isInSight(DotDeathmatch.player))
			sightedDots.add(DotDeathmatch.player);
		sightedDots.remove(this);
		return sightedDots;
	}
	
	private boolean isInSight(Dot d) {
		Point left = getLineFromAngle(angle-viewAngle/2, new Point(x, y), viewDistance);
		Point right = getLineFromAngle(angle+viewAngle/2, new Point(x, y), viewDistance);
		Point me = new Point(x, y);
		Point him = new Point(d.x, d.y);
		
		int lrm = trangleArea(left, right, me);
		
		int hrm = trangleArea(him, right, me);
		int lhm = trangleArea(left, him, me);
		int lrh = trangleArea(left, right, him);
		
		int total = (hrm + lhm + lrh);
		
		return total-1 <= lrm && total+1 >= lrm;
	}
	
	int trangleArea(Point p1, Point p2, Point p3){
		
		return (int) Math.abs(((p1.x*(p2.y-p3.y)) + p2.x*(p3.y-p1.y) + p3.x*(p1.y-p2.y)));
	}

	private float turnAngle(float turn){
		float temp = (float) (angle + turn % Math.PI*2);
		if(temp<0) temp +=  Math.PI*2;
		return temp;
	}
	
	public static Point getLineFromAngle(float angle, Point p, float lenght){
		float newX = (float) (p.x + lenght * Math.cos(angle));
		float newY = (float) (p.y + lenght * Math.sin(angle));
		
		return new Point(newX, newY);
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		Point temp = getLineFromAngle(angle, new Point(x, y), size*2);
		int tipX = (int) temp.x;
		int tipY = (int) temp.y;
		
		if(DotDeathmatch.debug) renderSight(g);
		
		g.setColor(color);
		g.drawOval(intX-size/2, intY-size/2, size, size);
		g.drawLine(intX, intY, tipX, tipY);
		g.drawString(""+ammo, intX-size/2, intY-size/2);
		if(shot != null) g.drawLine(intX, intY, (int)shot.x, (int)shot.y);
		shot = null;
		
	}
	
	private void renderSight(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
			
		g.setColor(color.darker().darker());
		Point left = getLineFromAngle(angle-viewAngle/2, new Point(x, y), viewDistance);
		Point right = getLineFromAngle(angle+viewAngle/2, new Point(x, y), viewDistance);
	
		g.drawLine(intX, intY, (int)left.x, (int)left.y);
		g.drawLine(intX, intY, (int)right.x, (int)right.y);
	
		for(Dot d : sightedDots){
			g.drawRect((int)d.x-size/2, (int)d.y-size/2, size, size);
		}
		
	}

}
