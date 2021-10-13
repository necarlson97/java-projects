import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class Water {
	
	int x = (int) (BottledShip.windowWidth*.1);
	int y = BottledShip.windowHeight/2;
	int width = (int) (BottledShip.windowWidth*.8);
	int height = BottledShip.windowHeight/4;
	
	int surfacePoints;
	float surfaceX[];
	int surfaceXEven[];
	float surfaceXVel[];
	
	float surfaceY[];
	float surfaceYVel[];
	
	int bottomX[] = {x+width, x};
	int bottomY[] = {(int)(BottledShip.windowHeight*.9), (int)(BottledShip.windowHeight*.9)};
	
	float wavePush = (float)1;
	float resistance = (float).02;
	float gravity = (float).1;
	
	int waveTip = 0;
	int wavePower = 2;
	int delay = 10;
	
	Color waveColor = new Color((float).5, (float).5, 1);
	
	public Water(int surfacePoints){
		this.surfacePoints = surfacePoints;
		surfaceX = perfectlySpaced();
		surfaceXEven = toInt(surfaceX);
		surfaceY = startingY();
		
		surfaceXVel = new float[surfacePoints];
		surfaceYVel = new float[surfacePoints];
		
		wavePower = surfacePoints/5;
	}

	private float[] perfectlySpaced() {
		float increment = width/(surfacePoints-1);
		float points[] = new float[surfacePoints];
		int curr = x;
		for(int i=0; i<surfacePoints; i++){
			points[i] = curr;
			curr += increment;
		}
		points[points.length-1] = bottomX[0];
		return points;
	}
	
	private float[] startingY() {
		float points[] = new float[surfacePoints];
		for(int i=0; i<surfacePoints; i++){
			points[i] = y;
		}
		return points;
	}

	public void run(int count){
		
		if(count%delay == 0) {
			surfaceYVel[waveTip] += wavePower;
			surfaceXVel[waveTip] += wavePower;
			waveTip = (waveTip+1)%surfacePoints;
		}
		
				
		for(int i=0; i<surfacePoints; i++){
			
			
			surfaceYVel[i] = depreciate(surfaceYVel[i], resistance, 0);
			surfaceXVel[i] = depreciate(surfaceXVel[i], resistance, 0);
			
			//surfaceYVel[i] = depreciate(surfaceY[i], gravity, y);
			
			
			applyGravity(i);
			applyEntropy(i);
						
			
			surfaceY[i]+=surfaceYVel[i];
			surfaceX[i]+=surfaceXVel[i];
			
			if(surfaceX[i]<x) surfaceX[i] = x;
			else if(surfaceX[i]>x+width) surfaceX[i] = x+width;
		}
		surfaceX[0] = x;
		surfaceX[surfacePoints-1] = x+width;
		
	}
	
	private void applyEntropy(int i) {
		if(surfaceX[i] < surfaceXEven[i]) surfaceXVel[i] += gravity;
		else if(surfaceX[i] > surfaceXEven[i]) surfaceXVel[i] -= gravity;
		else if(Math.abs(surfaceX[i]) > surfaceXEven[i]+gravity) {
			surfaceXVel[i] = 0;
			surfaceX[i] = surfaceXEven[i];
		}
		
	}

	private void applyGravity(int i) {
		if(surfaceY[i] < y) surfaceYVel[i] += gravity;
		else if(surfaceY[i] > y) surfaceYVel[i] -= gravity;
		else if(Math.abs(surfaceY[i]) > y+gravity) {
			surfaceYVel[i] = 0;
			surfaceY[i] = y;
		}
	}
	
	private float depreciate(float f, float r, int base){
		if(f > base) return f-=r;
		else if(f < base) return f+=r;
		else if(Math.abs(f) > r) return base;
		else return f;
	}
	
	private void depreciateVelocity(int i) {
		if(surfaceYVel[i] > 0) surfaceYVel[i]-=resistance;
		else if(surfaceYVel[i] < 0) surfaceYVel[i]+=resistance;
		else if(Math.abs(surfaceYVel[i]) > resistance) surfaceYVel[i] = 0;
	}

	public void render(Graphics g){
		
		g.setColor(waveColor);
		g.fillPolygon(join(toInt(surfaceX), bottomX), join(toInt(surfaceY), bottomY), surfacePoints+2);
		
		if(BottledShip.debug) renderDebug(g);

	}
	
	private void renderDebug(Graphics g) {
		g.setColor(Color.darkGray);
		g.drawPolyline(toIntPlusTimes(surfaceXVel, surfaceX, 1/gravity), toIntPlusTimes(surfaceYVel, y, 1/gravity), surfacePoints);
		
		int diameter = width/surfacePoints/4;
		for(int i=0; i<surfacePoints-1; i++){
			g.setColor(Color.orange);
			g.fillOval((int)surfaceX[i]-(diameter/2), (int)surfaceY[i]-(diameter/2), diameter, diameter);
			
			g.setColor(Color.red);
			int xAvg = (int) (surfaceX[i]+surfaceX[i+1])/2;
			int yAvg = (int) (surfaceY[i]+surfaceY[i+1])/2;
			g.fillOval(xAvg-(diameter/2), yAvg-(diameter/2), diameter, diameter);
			
			if(surfaceX[i]<BottledShip.ship.x && surfaceX[i+1]>BottledShip.ship.x){ //&& yAvg < BottledShip.ship.y){
				g.setColor(Color.blue);
				g.fillOval(xAvg-(diameter/2), yAvg-(diameter/2), diameter, diameter);
			}
		}	
	}

	public floatTuple checkCollision(float x, float y, int height, float boyancy){
		
		floatTuple ret = new floatTuple(0,0);
		
		for(int i=0; i<surfacePoints-1; i++){
			
			int yAvg = (int) (surfaceY[i]+surfaceY[i+1])/2;
			//int xAvg = (int) (surfaceX[i]+surfaceX[i+1])/2;
			
			if(surfaceX[i]<x && surfaceX[i+1]>x){
				
				if(yAvg < y) ret.a = boyancy;
				
				float waveLength = (surfaceX[i+1] - surfaceX[i]) / (BottledShip.windowWidth/(surfacePoints));
				if(yAvg < y+(height) && waveLength < .8)
					ret.b = 1-waveLength;
				
			}
			
		}
		return ret;
	}
	
	public int[] toInt(float[] floats){
		int ints[] = new int[floats.length];
		for(int i=0; i<ints.length; i++)
			ints[i] = (int)floats[i];
		return ints;
	}
	
	public int[] toIntPlusTimes(float[] floats, int plus, float times){
		int ints[] = new int[floats.length];
		for(int i=0; i<ints.length; i++)
			ints[i] = (int)(floats[i]*times) +plus;
		return ints;
	}
	
	public int[] toIntPlusTimes(float[] floats, float[] plus, float times){
		int ints[] = new int[floats.length];
		for(int i=0; i<ints.length; i++)
			ints[i] = (int)((floats[i]*times) +plus[i]);
		return ints;
	}
	
	public int[] join(int[] first, int[] second){
		int both[] = new int[first.length + second.length];
		for(int i=0; i<both.length; i++){
			if(i<first.length) both[i] = first[i];
			else both[i] = second[i-first.length];
		}
		return both;
	}

}
