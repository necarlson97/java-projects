import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Duelist{
	
	int count;
	int drawCount;
	int holsterCount;
	int shootCount = -1;
	int pullTriggerCount;
	int deathCount;

	public Enemy(int x, int y, Color duelistColor) {
		super(x, y);
		this.duelistColor = duelistColor;
		drawCount = r.nextInt(20)*10;
		pullTriggerCount = drawCount + r.nextInt(10)*10;
		holsterCount = drawCount + r.nextInt(10)*10;
	}
	
	public void run(){
		
		if(Duel.player.deathCount == 299) {
			Duel.enemy = new Enemy(1000, 440, duelistColor);
			Duel.enemy.killCount = this.killCount;
			return;
		}
		
		if(alive){
			if(count == drawCount) gun=true;
			
			if(count == pullTriggerCount){
				if(Duel.player.gun){
					shootCount = count + r.nextInt(5)*10;
				}
				else {
					pullTriggerCount = count + r.nextInt(10)*10;
					holsterCount = count + r.nextInt(10)*10;;
				}
			}
			
			if(count == holsterCount){
				gun = false;
				drawCount = count + r.nextInt(10)*10;
				pullTriggerCount = drawCount + r.nextInt(10)*10;
				holsterCount = drawCount + r.nextInt(10)*10;;
			}
			
			if(count == shootCount){
				Duel.victim = Duel.player;
				Duel.shooter = Duel.enemy;
			}
		}
		else if (deathCount > 300) {
			Duel.player.gun = false;
			Duel.enemy.gun = false;
			Color randEnemyColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
			Duel.enemy = new Enemy(1000, 440, randEnemyColor);
		}
		else {
			gun = false;
			deathCount++;
		}
		
		count ++;
	}
	
	public void render(Graphics g){
		
		super.render(g, duelistColor);
		
		if(gun){
			g.fillRect(x-width, y+35, 20, 5);
			
			g.setColor(Color.gray);
			g.fillRect(x-35, y+30, 30, 5);
			g.setColor(Color.gray);
			g.fillRect(x-15, y+35, 5, 10);
		}
		
		
	}

}
