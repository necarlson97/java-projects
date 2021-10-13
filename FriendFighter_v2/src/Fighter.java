import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;

public class Fighter extends SpritedObject{
	
	float xVel;
	float yVel;
	
	boolean left;
	boolean right;
	
	boolean facingLeft;
	
	float dmgVel;
	
	Action forward;
	Action backward;
	Action idle;
	
	Action block;
	Action finishThem; 
	Action fall;
	
	Stack<FrameState> frameStack = new Stack<FrameState>();
	ArrayList<Attack> attackSet = new ArrayList<Attack>();
	
	Action myAction;
	BufferedImage currSprite;
	String currActionName;
	
	int speed = 10;
	int maxSpeed = 20;
	int maxHealth = 10;
	float health;
	float prevHealth;
	
	Color color = Color.white;
	
	Fighter enemy;
	
	HitSparkle[] hitParticles = new HitSparkle[10];
	int hitParticleIndex;
	
	public Fighter(String name, int x, int y, Color color, int maxHealth){
		super(x, y, 64, 64, name);
		
		forward = new Action("Forward", this);
		backward = new Action("Backward", this);
		idle = new Action("Idle", this);
		fall = new Fall("Fall", this);
		
		this.color = color;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.prevHealth = health;
	}
	
	public void run(){
		for(HitSparkle h : hitParticles)
			if(h!=null) h.run();
		
		if(health > 0){
			if(left && xVel > -maxSpeed) xVel -= speed;
			if(right && xVel < maxSpeed) xVel += speed;
			
			if(enemy != null && enemy.x < x) facingLeft = false;
			if(enemy != null && enemy.x > x) facingLeft = true;
			
			//System.out.println(myAction);
			
			if(dmgVel == 0) prevHealth = health;
			
			move();
		}
		takeDmgVel();
		
		
		if(!frameStack.isEmpty()) {
			currSprite = frameStack.peek().sprite;
			currActionName = frameStack.pop().actionName;
			
			//System.out.println("\t\t"+currActionName);
			
			if(currActionName.contains("hit frame")) hitEnemy((Attack) myAction);
			if(currActionName.contains("last frame")) {
				if(currActionName.contains("repeat frame")) 
						frameStack.push(new FrameState(currActionName, currSprite));
				else myAction = null;
			}
		}
		else if(myAction == null && health > 0){
			if(left || right) myAction = getMotion();
			else myAction = idle;
		}
		else {
			if(health <0 ) myAction = fall;
			addFramesToStack(myAction);
		}
		
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
	
	public void hitEnemy(Attack attack){
		int range = attack.range;
		float damedge = attack.damedge;
		
		if(enemy != null && enemyIsInRange(range)) {
			enemy.dmgVel += damedge;
			if(facingLeft) enemy.x += attack.knockback;
			else enemy.x -= attack.knockback;
			
			for(int i=0; i<attack.hitPause; i++)
				frameStack.add(new FrameState("hit pause", currSprite));
			
			float sparkleX;
			float sparkleY;
			if(facingLeft) {
				sparkleX = x+(attack.xOffset*scale);
				sparkleY = y+(attack.yOffset*scale);
			}
			else {
				sparkleX = x-(attack.xOffset*scale);
				sparkleY = y+(attack.yOffset*scale);
			}
			if(facingLeft && sparkleX > enemy.x) sparkleX = enemy.x;
			if(!facingLeft && sparkleX < enemy.x) sparkleX = enemy.x;
			
			enemy.hitParticles[hitParticleIndex] = new HitSparkle(sparkleX, sparkleY, attack.name);
			enemy.hitParticleIndex = (hitParticleIndex+1)%hitParticles.length;
		}
	}
	
	public boolean enemyIsInRange(int range){
		//System.out.println((x-enemy.x) / scale);
		if(enemy.x > x && enemy.x < x+(range*scale)) return true;
		else if(enemy.x < x && enemy.x > x-(range*scale)) return true;
		else return false;
	}
	

	private Action getMotion() {
		if(facingLeft) {
			if(xVel < 0) return backward;
			else return forward;
		}
		else {
			if(xVel < 0) return forward;
			else return backward;
		}
	}
	
	public void addFramesToStack(Action action){
		for(int i=action.frameStates.length-1; i>0; i--)
			frameStack.push(action.frameStates[i]);
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		int drawWidth = (int) (spriteWidth*scale);
		int drawHeight = (int) (spriteHeight*scale);
		
		if(facingLeft) g.drawImage(currSprite, intX-drawWidth/2, intY-drawHeight,  drawWidth, drawHeight, null);
		else g.drawImage(currSprite, intX+drawWidth/2, intY-drawHeight,  drawWidth*-1, drawHeight, null);
		
		for(HitSparkle h : hitParticles)
			if(h!=null) h.render(g);
	}

}
