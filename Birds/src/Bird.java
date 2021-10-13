import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Bird extends SpritedObject{
	
	
	float xVel;
	float yVel;
	
	static float wingPower; //a weak value is 5, a strong value is 10
	static float glideAt; //a weak value is 2, a strong value is .5
	
	static int weight; //between 0 and 10
	
	static boolean flapUp;
	static boolean flapDown;
	
	static boolean idling;
	
	static BufferedImage restSprite;
	static BufferedImage[] flapSprites;
	static int flapStage = -1;
	static BufferedImage[] idleSprites;
	static int idleStage = -1;
	static BufferedImage[] eatSprites;
	static BufferedImage currentSprite;

	public Bird(int x, int y, String spriteSheetString) {
		super(x, y, spriteSheetString, 32);
		xVel = 1;
	}
	
	public void run(){
		if(flapUp) wingsUp();
		else if(flapDown) wingsDown();
		move();
	}
	
	public void move(){
		depreciateVelocity();
		
		if(flapStage == 2 && yVel > glideAt) yVel -= .2;
		else if(flapStage == 2 && yVel < 0) yVel += .2;
		else if(yVel < 5) yVel += .4;
		
		y+=yVel;
	}
	
	private void depreciateVelocity() {
		double resistance = .01;
//		if(xVel > 0) xVel -= resistance;
//		else if(xVel < 0) xVel -= resistance;
		if(yVel < 0) yVel -= resistance;
		else if(yVel > 0) yVel -= resistance;
		if(Math.abs(xVel) < .01) xVel = 0;
		if(Math.abs(yVel) < .01 ) yVel = 0;
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.drawImage(currentSprite, intX-drawWidth/2, intY-drawHeight, drawWidth, drawHeight, null);
	}
	
	public void wingsUp(){
		flapDown = false;
		if(flapStage >= flapSprites.length-1) {
			flapUp = false;
			return;
		}
		else if(flapStage < flapSprites.length-1) flapStage ++;
		currentSprite = flapSprites[flapStage];
	}
	
	public void wingsDown(){
		flapUp = false;
		if(flapStage <=0) {
			flapDown = false;
			if(y>0 && yVel > -7) yVel -= wingPower - weight/100;
			if(y>0 && y<100) yVel += y/20;
			return;
		}
		else if(flapStage > 0){
			flapStage --;
			currentSprite = flapSprites[flapStage];
		}
		
	}
	
	public boolean inHitbox(float givenX, float givenY) {
		int intX = (int) (givenX-x)/scale;
		int intY = (int) (givenY-y)/scale;
		
		if(intX <= 0 || intX >= spriteWidth) return false;
		if(intY <= 0 || intY >= spriteHeight) return false;
		
		Color colorAt = new Color(currentSprite.getRGB(intX, intY), true);
		return (colorAt.getAlpha() != 0);
	}
	
	public boolean atClaw(float givenX, float givenY) {
		boolean atX = (givenX > (x+drawWidth/2)-scale) && (givenX < (x+drawWidth/2)+scale);
		boolean atY = givenY > (y+drawHeight-scale) && givenY < (x+drawHeight+scale);
		return atX && atY;
	}

}
