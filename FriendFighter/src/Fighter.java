import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Fighter extends SpritedObject{
	
	float xVel;
	float yVel;
	
	float dmgVel;
	
	boolean left;
	boolean right;
	
	boolean idleUp = true;
	int idle = 0; 
	
	// If an index is at -2, it is not active
	// If it is -1, it will begin
	// And continue based on wether it is serial or cyclical
	int lightAttack = -2; 
	int heavyAttack = -2; 
	int specialAttack = -2; 
	int blocking = -2; 
	int finishing = -2; 
	
	int motion = -1;
	
	BufferedImage[] idleSprites; // 6 frame cycle
	
	BufferedImage[] backwardSprites; // 4 frame cycle?
	BufferedImage[] forwardSprites;
	
	BufferedImage[] lightAttackSprites; // 4 frame serial
	BufferedImage[] heavyAttackSprites; // 12 frame serial
	BufferedImage[] specialAttackSprites; // 20 frame serial
	
	BufferedImage[] blockSprites; // 2 frame serial, then static ?
	BufferedImage[] finishSprites; // 8 frames serial, 8 frames cycle
	BufferedImage[] fallSprites; // 6 frame serial, dependent on damedge taken
	
	BufferedImage currSprite ;
	
	int speed = 10;
	int maxSpeed = 20;
	int maxHealth = 100;
	float health;
	
	Fighter enemy;
	
	int lightHitFrame;
	int mediumHitFrame;
	int heavyHitFrame;
	
	float lightDamedge = (float) .2;
	float heavyDamedge = (float) .6;
	
	public Fighter(String name, int x, int y, int lightHitFrame, int mediumHitFrame, int heavyHitFrame){
		super(x, y, 64, 64, name);
		
		this.lightHitFrame = lightHitFrame;
		this.mediumHitFrame = mediumHitFrame;
		this.heavyHitFrame = heavyHitFrame;
		
		this.health = maxHealth;
		
		idleSprites = getSpritesFromSheet(name+"Idle.png");
		lightAttackSprites = getSpritesFromSheet(name+"Light.png");
		heavyAttackSprites = getSpritesFromSheet(name+"Heavy.png");
		backwardSprites = getSpritesFromSheet(name+"Backward.png");
		forwardSprites = getSpritesFromSheet(name+"Forward.png");
	}
	
	public void run(){
		
		if(left && xVel > -maxSpeed) xVel -= speed;
		if(right && xVel < maxSpeed) xVel += speed;
		
		move();
		takeDmgVel();
		
		if(lightAttack >= -1)
			currSprite = getAttackSprite(lightAttackSprites, lightHitFrame, lightAttack++, lightDamedge, 7);
		else if(heavyAttack >= -1)
			currSprite = getAttackSprite(heavyAttackSprites, heavyHitFrame, heavyAttack++, heavyDamedge, 5);
		else if(xVel == 0 && yVel ==0) currSprite =  getIdleSprite();
		else currSprite = getMotionSprite();
		
		if(lightAttack >= lightAttackSprites.length-1) lightAttack = -2;
		if(heavyAttack >= heavyAttackSprites.length-1) heavyAttack = -2;
	}

	public void move(){
		float resistance = 4;
		
		if(xVel > 0) xVel-=resistance;
		else if(xVel < 0) xVel+=resistance;
		if(yVel > 0) yVel-=resistance;
		else if(yVel < 0) yVel+=resistance;
		
		if(Math.abs(xVel)<resistance) xVel = 0;
		if(Math.abs(yVel)<resistance) yVel = 0;
		
		if(enemy != null){
			int distance = (int) (this.x-enemy.x);
			if(Math.abs(distance) < spriteWidth/2) this.x += (this.x-enemy.x)/2;
		}
		
		x+=xVel;
		y+=yVel;
	}
	
	public void takeDmgVel(){
		float resistance = (float)0.03;
				
		if(dmgVel > 0) dmgVel-=resistance;
		else if(dmgVel < 0) dmgVel+=resistance;
		
		if(Math.abs(dmgVel)<resistance) dmgVel = 0;

		health -= dmgVel;
	}
	
	public boolean enemyIsInRange(int range){
		int scale = FriendFighter.windowWidth/spriteWidth;
		
		if(enemy == null) return false;
		else if(enemy.x > x && enemy.x < x+(range*scale)) return true;
		else if(enemy.x < x && enemy.x > x-(range*scale)) return true;
		else return false;
	}
	

	private BufferedImage getMotionSprite() {
		motion = (motion+1)%backwardSprites.length;
		if(enemy == null || enemy.x > x) {
			if(xVel < 0) return backwardSprites[motion];
			else return forwardSprites[motion];
		}
		else {
			if(xVel < 0) return forwardSprites[motion];
			else return backwardSprites[motion];
		}
	}
	
	public BufferedImage getAttackSprite(BufferedImage[] attackSprites, int hitFrame, int frameIndex, float hitDamedge, int range){
		if(frameIndex < 0) return currSprite; //Something unusual, dont change
		if(frameIndex >= attackSprites.length-1) { // Last frame of attack
			return attackSprites[attackSprites.length-1];
		}
		if(frameIndex == hitFrame){ // Hitframe
			if(enemy != null && enemyIsInRange(range)) enemy.dmgVel += hitDamedge;
		}
		return attackSprites[frameIndex];
	}

	private BufferedImage getIdleSprite() {
		if(idle >= 2*idleSprites.length-1) idleUp = false;
		else if(idle <= 0) idleUp = true;
			
		if(idleUp) idle ++;
		else idle --;
		
		return idleSprites[idle/2];
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		int spriteWindowRatio = FriendFighter.windowWidth/spriteWidth;
		
		int drawWidth = (int) (spriteWidth*(spriteWindowRatio*.3));
		int drawHeight = (int) (spriteHeight*(spriteWindowRatio*.3));
		
		if(enemy == null || enemy.x > x) g.drawImage(currSprite, intX-drawWidth/2, intY-drawHeight,  drawWidth, drawHeight, null);
		else g.drawImage(currSprite, intX+drawWidth/2, intY-drawHeight,  drawWidth*-1, drawHeight, null);
//		g.setColor(Color.green);
//		g.drawRect(intX, intY-drawSize, drawSize, drawSize);
	}
	
	// Gets sprites for a single row sprite sheet with an 1:1 ratio
	public BufferedImage[] getSpritesFromSheet(String sheetFileName){
		BufferedImage spriteSheet;
		
		try {
			spriteSheet = ImageIO.read(new File(sheetFileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		int spriteRows = (spriteSheet.getWidth()/spriteWidth);
		int spriteCols = (spriteSheet.getHeight()/spriteHeight);
		BufferedImage[] sprites = new BufferedImage[spriteRows * spriteCols];
		
		int spriteIndex = 0;
		for(int col = 0; col < spriteCols; col++){
			for(int row = 0; row < spriteRows; row++){
				int spriteStartX = row*spriteWidth;
				int spriteStartY = col*spriteHeight;

				sprites[spriteIndex] = 
						spriteSheet.getSubimage(spriteStartX, spriteStartY, spriteWidth-1, spriteHeight-1);
				spriteIndex++;
			}
		}
		
		return sprites;
	}

}
