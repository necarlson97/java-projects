import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	
	float x;
	float y;
	float z;
	
	float angle;
	
	int size = 10;
	int tipLength = size*2;
	
	int moveSpeed = 2;
	float turnSpeed = (float) .02;
	
	int viewDistance = size*20;
	float viewAngle = (float) .4;
	
	Color color = Color.RED;
	
	boolean forward;
	boolean backward;
	boolean left;
	boolean right;
	
	public Player(){
		x = Game.windowWidth/4;
		y = Game.windowHeight/2;
		z = 10;
	}
	
	public void run(){
		if(forward) move(moveSpeed);
		else if(backward) move(-moveSpeed);
		
		if(left) turn(-turnSpeed);
		else if(right) turn(turnSpeed);
	}
	
	public void move(float toMove){
		
		Point temp = getLineFromAngle(angle, new Point(x, y), toMove);
		x = temp.x;
		y = temp.y;
		
	}
	
	public void turn(float turnSpeed) {
		angle = turnAngle(turnSpeed);
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
		
		g.setColor(color);
		g.drawOval(intX-size/2, intY-size/2, size, size);
		g.setColor(Color.white);
		g.drawLine(intX, intY, tipX, tipY);
		
	}

}
