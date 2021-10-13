import java.awt.Color;
import java.awt.Graphics;

public class Player extends Ship {
	
	public Player(ShapeInvaders s) {
		super(40, 20, Game.windowWidth / 2, (int) (Game.windowHeight * .8), Color.white, s);
	}
	
	public void shoot() {
	
		int starting = nextBulletIndex;
		nextBulletIndex = (nextBulletIndex+1)%3;
		while(nextBulletIndex != starting) {
			nextBulletIndex = (nextBulletIndex+1)%3;
			if(bullets[nextBulletIndex] == null) {
				bullets[nextBulletIndex] = new PlayerBullet(this);
				break;
			}
		}
		
	}

}
