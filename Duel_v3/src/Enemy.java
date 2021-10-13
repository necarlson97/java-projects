import java.awt.Color;
import java.awt.Graphics;

public abstract class Enemy extends Duelist{	

	int drawCountStop;
	int reflexTime;
	boolean drawCountingUp = true;
	
	int deathCountDown = 300;

	public Enemy(int x, int y, Color duelistColor, String spriteSheetString) {
		super(x, y, duelistColor, spriteSheetString);
		
		
		drawCountStop = reflexTime + r.nextInt(100);
	}
	
	public void run(){
		
		if(alive) continueDraw();
		else deathCountDown--;
		if(deathCountDown == 0) Duel.enemy = new RedWoman(x, y);
		
	}
	
	private void continueDraw() {
		if(drawCounter > drawCountStop - reflexTime){
			if(drawCounter > 90 && Duel.player.looksThreatening()) { // Shoot the player
				Duel.shooter = this;
				Duel.victim = Duel.player;
				drawCountingUp = false;
			}
			else drawCountingUp = false; // Prepare to holster gun
		}
		
		if(drawCounter > 80){ gunDrawn = true; drawingGun = false; }
		else if(drawCounter > 40){ drawingGun = true; gunDrawn = false; }
		else{ gunDrawn = false; drawingGun = false; }

			
		if(drawCounter <= 0) { //Gun fully holstered
			drawCountStop = r.nextInt(50)*10;
			drawCountingUp = true;
		}
		if(drawCountingUp) drawCounter++;
		else drawCounter--;
		
	}

	public void render(Graphics g){
		super.render(g, duelistColor);	
	}

}
