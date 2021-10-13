import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Player {
	
	static Random rand = new Random();
	
	int weaponClass;
	int weaponHit[][] = { 
			// -3 = shatter/brutal, -1 = shatter, 0 = nothing,
			// 1 = weak, 2 = solid, 3 = brutal, 4 = mortal, 5 = lethal
			
		//    1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2}, // Fists
			{-1,-1,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, -3}, // Bad
			{-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3}, // Ok
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3}, // Good
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4}, // Great
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4}, // Superb
			{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 5} // Myhtic
			
	};
	
	int meleeMod;
	
	int x;
	int y;
	int size;
	
	int cuts;
	int bruses;
	
	boolean alive = true;
	
	Color color = Color.white;
	
	Team team;

	Player(int weaponClass, int y, Team t) {
		this.weaponClass = weaponClass;
		this.y = y;
		team = t;
		x = team.x;
		size = Simulator.playerSize;
		color = team.color;
	}
	
	void attack(Player enemy) {
		int result = roll() + meleeMod;
		if(result < 0) {
			shatter();
			if(result < -1) result *= 1;
			else return;
		}
		int hit = weaponHit[weaponClass][result];
		HitEffects.applyEffect(hit, enemy);
	}
	
	private void shatter() {
		weaponClass = 0;
	}
	
	int roll() {
		return rand.nextInt(20);
	}
	
	void kill() {
		alive = false;
		color = Color.gray;
		team.players.remove(this);
		team.dead.add(this);
	}
	
	void render(Graphics g) {
		g.setColor(color);
		g.fillOval(x-size/2, y-size/2, size, size);
		g.setColor(Color.white);
		g.drawString("Cuts: "+cuts+" Bruses: "+bruses+" Wep: "+weaponClass, x, y);
	}
}
