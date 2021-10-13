import java.awt.Color;
import java.awt.Graphics;

public class Ship {
	
	float x;
	float y;
	
	float xVel;
	float yVel;
	
	float frontY;
	float frontX;
	float frontYVel;
	int frontDistance = 100;
	
	int width = 100;
	int height = 100;
	
	float resistance = (float).02;
	float gravity = (float).2;
	float boyancy = (float).4;
	float surfaceTension = (float)1;
	
	boolean powerOn = false;
	float shipPower = (float).1;
	
	floatTuple prevToFloat = new floatTuple(0, 0);
	
	public Ship(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void run(){
		
		if(powerOn) xVel -= shipPower;
		if(x > BottledShip.windowWidth*.7) powerOn = true;
		else if(x < BottledShip.windowHeight*.3) powerOn = false;
		
		getBoyancyMotion();
		applyMotion();
	}
	
	private void getBoyancyMotion() {
		floatTuple middleUp = BottledShip.water.checkCollision(x, y, height, boyancy);
		floatTuple frontUp = BottledShip.water.checkCollision(x+frontX, y+frontY, height, boyancy);
		floatTuple backUp = BottledShip.water.checkCollision(x+frontX, y+frontY, height, boyancy);
		
		frontYVel -= frontUp.a;
		
		yVel -= middleUp.a;
		xVel += middleUp.b;
		
		if( (prevToFloat.a == 0 && middleUp.a != 0) || prevToFloat.a != 0 && middleUp.a == 0){
			yVel = depreciate(yVel, surfaceTension, 0);
			xVel = depreciate(xVel, surfaceTension, 0);
			frontYVel = depreciate(frontYVel, surfaceTension, 0);
		}
			
		prevToFloat = middleUp;
		
		if(middleUp.a == 0) yVel += gravity;
		if(frontUp.a == 0) {
			if(frontY > 0) frontYVel -= gravity;
			if(frontY < 0) frontYVel += gravity;
		}
		
		yVel = depreciate(yVel, resistance, 0);
		xVel = depreciate(xVel, resistance, 0);
		frontYVel = depreciate(frontYVel, resistance, 0);
	}

	public void applyMotion(){
		if(xVel > 5) xVel = 5;
		if(xVel < -5) xVel = -5;
		
		if(yVel > 5) yVel = 5;
		if(yVel < -5) yVel = -5;
		
		x+=xVel;
		y+=yVel;
		
		if(Math.abs(frontY) < frontDistance) frontY += frontYVel;
		else {
			frontY*=-1;
			if(frontY<0) frontY+=5;
			else frontY-=5;
		}
		
		frontX = (float) Math.sqrt((frontDistance*frontDistance) - (frontY*frontY));
	}
	
	public float depreciate(float f, float r, float base){
		if(f > base) return f-r;
		else if(f < base) return f+r;
		if(Math.abs(f)-base < r) return base;
		return f;
	}
	
	public void render(Graphics g){
		
		if(BottledShip.debug) renderDebug(g);
		
	}
	
	private void renderDebug(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(Color.white);
		g.drawRect(intX-(width/2), intY-(height/2), width, height);
		
		g.setColor(Color.red);
		g.drawOval(intX-5, intY-5, 10, 10);
		g.drawLine(intX, 0, intX, BottledShip.windowHeight);
		g.drawLine(0, intY, BottledShip.windowWidth, intY);
		
		int intFrontY = (int)frontY;
		int intFrontX = (int)frontX;
		
		g.setColor(Color.green);
		g.drawLine(intFrontX+intX, intFrontY+intY, intX, intY);
		
		g.setColor(Color.green);
		g.drawLine(intX-intFrontX, intY-intFrontY, intX, intY);
		
	}

}
