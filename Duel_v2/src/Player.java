import java.awt.Color;
import java.awt.Graphics;

public class Player extends Duelist implements Runnable{
	
	int deathCount;
	
	public Player(int x, int y){
		super(x, y);
		this.duelistColor = Color.white;
	}
	
	public void run(){
		
		if(!alive) {
			Duel.player.gun = false;
			deathCount ++;
		}
		if(!alive && deathCount > 300){
			Duel.enemy.gun = false;
			Duel.player = new Player(x, y);
		}
		
	}
	
	public void render(Graphics g){
		
		super.render(g, duelistColor);
		
		if(gun){
			
			g.fillRect(x+width, y+35, 20, 5);
			
			g.setColor(Color.gray);
			g.fillRect(x+width+5, y+30, 30, 5);
			g.setColor(Color.gray);
			g.fillRect(x+width+10, y+35, 5, 10);
		}
	}

}
