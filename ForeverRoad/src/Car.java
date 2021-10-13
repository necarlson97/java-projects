import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Car {
	
	ForeverRoad fr;
	Random rand = Engine2D.rand;
	
	AffineTransform identity = new AffineTransform();
	AffineTransform trans = new AffineTransform();
	
	float turn = 0;
	float accel = 0;
	
	float x;
	float y;
	
	int width;
	int length;
	
	boolean totalled = false;
	
	float turnSpeed = 0.01f;
	int accelSpeed = 5;
	
	float diminishRate = 0.95f;
	
	float angle = (float) Math.PI/2;
	
	BufferedImage carSprite;
	BufferedImage totalledSprite;
	BufferedImage doorLSprite;
	BufferedImage doorRSprite;
	BufferedImage hoodSprite;
	
	float minSpeed;
	
	Whisker[] whiskers = new Whisker[5]; 
	
	int distance = 0;
	
	Car(ForeverRoad fr) {
		this.fr = fr;
		Road r = fr.road;
		x = r.xs[r.res/2]+r.width/2;
		y = r.ys[r.res/2];
		loadSprites();
		createWhiskers();
		minSpeed = fr.road.speed;
	}
	
	private void createWhiskers() {
		float angleOffset = (float) Math.PI/2;
		float angleInterval = (float) (Math.PI/4);
		for(int i=0; i<whiskers.length; i++) {
			whiskers[i] = new Whisker(angleOffset, length*2, this);
			angleOffset += angleInterval;
		}
	}

	private void loadSprites() {
		carSprite = loadSprite("Car","Car");
		totalledSprite = loadSprite("Car","Totalled");
		doorLSprite = loadSprite("Car","Left Door");
		doorRSprite = loadSprite("Car","Right Door");
		hoodSprite = loadSprite("Car","Hood");
		
		width = carSprite.getWidth();
		length = carSprite.getHeight();
	}
	
	static BufferedImage loadSprite(String folder, String spriteName) {
		BufferedImage sprite;
		try {
			sprite = ImageIO.read(new File(folder+"/"+spriteName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sprite;
	}

	void update() {
		
		if(!totalled) {
			if(turn < -1) turn = -1;
			else if(turn > 1) turn = 1;
			if(accel < -1) accel = -1;
			else if(accel > 1) accel = 1;
			
			checkCollisions();
		}
		
		move(-minSpeed + accel*accelSpeed);
		
		diminish();
		y+=fr.road.speed;
		
		angle = turnAngle(turn*turnSpeed);
		
		for(Whisker w : whiskers)
			w.update();
		
		if(y < -length || y > Engine2D.windowHeight+length ||
				x < -width || x > Engine2D.windowWidth+width)
			fr.carDeath();
	}
	
	private void move(float dist) {
		Point temp = getLineFromAngle(angle, new Point(x, y), dist);
		distance += y - temp.y;
		x = temp.x;
		y = temp.y;
	}
	
	static Point getLineFromAngle(float angle, Point p, float length){
		float newX = (float) (p.x + length * Math.cos(angle));
		float newY = (float) (p.y + length * Math.sin(angle));
		
		return new Point(newX, newY);
	}
	
	private float turnAngle(float turn){
		float temp = (float) (angle + turn % Math.PI*2);
		if(temp<0) temp +=  Math.PI*2;
		return temp;
	}
	
	private void diminish() {
		accel *= diminishRate;
		turn *= diminishRate;
	}
	
	public void checkCollisions() {
		Road r = fr.road;
		for(int i=0; i<r.ys.length-1; i++) {
			if(y <= r.ys[i] && y >= r.ys[i+1]) {
				if(x+width/2 >= r.xs[i]) offRoad(true);
				if(x-width/2 <= r.xs[r.xs.length-i-1]) offRoad(false);
			}
		}
		if(y > Engine2D.windowHeight) offRoad();
	}
	
	private void offRoad() {
		offRoad(true);
		offRoad(false);
	}
	
	private void offRoad(boolean left) {
		accel += 0.5f;
		if(left) turn -= 0.1f;
		else turn += 0.1f;
	}

	void crash() {
		totalled = true;
		carSprite = totalledSprite;
		fr.carDeath();
	}
	
	float[] whiskerIntersects() {
		float[] whisk = new float[whiskers.length];
		for(int i=0; i<whisk.length; i++) {
			whisk[i] = whiskers[i].roadIntersection();
		}
		return whisk;
	}
	
	void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		Graphics2D g2d = (Graphics2D)g.create();
		
		trans.setTransform(identity);
		
		g2d.translate(intX-width/2, intY-length/2);
		trans.rotate(angle-Math.PI/2, width/2, length/2);
		
		renderWhiskers(g);
		g2d.drawImage(carSprite, trans, null);
		if(fr.debug) renderDebug(g);

	}
	
	private void renderWhiskers(Graphics g) {		
		g.setColor(Color.white);
		for(int i=0; i<whiskers.length; i++)
			whiskers[i].render(g);
	}

	private void renderDebug(Graphics g) {
		String turnStr = ""+turn;
		g.setColor(Color.red);
		g.setFont(new Font("Arial", 0, 28));
		g.drawString(turnStr.substring(0, 3), (int)x, (int)y);
	}

}
