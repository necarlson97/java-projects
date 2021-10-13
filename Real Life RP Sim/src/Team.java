import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;

public class Team {
	
	static Random rand = new Random();

	LinkedList<Player> players = new LinkedList<Player>();
	LinkedList<Player> dead = new LinkedList<Player>();
	Team enemies;
	
	Color color;
	int x;
	
	Simulator sim;
	
	int maxSize;
	
	Team(int size, Color color, int x, Simulator sim) {
		this.color = color;
		this.x = x;
		this.sim = sim;
		maxSize = size;
		
		int y = 100;
		for(int i=0; i<maxSize; i++) {
			players.add(new Player(rand.nextInt(3), y, this));
			y += Simulator.playerSize*2;
		}
	}
	
	Team(Team t) {
		color = t.color;
		x = t.x;
		sim = t.sim;
		maxSize = rand.nextInt(5)+1;
		
		int y = 100;
		for(int i=0; i<maxSize; i++) {
			players.add(new Player(1, y, this));
			y += Simulator.playerSize*2;
		}
	}
	
	void takeTurn() {
		if(players.isEmpty()) sim.lost(this);
		for(Player p : players) {
			if(enemies != null) p.attack(enemies.randPlayer());
		}
	}
	
	Player randPlayer() {
		if(players.isEmpty()) return null;
		return players.get(rand.nextInt(players.size()));
	}
	
	void setEnemies(Team e) {
		enemies = e;
	}

	public void render(Graphics2D g) {
		for(Player p : players)
			p.render(g);
		for(Player p : dead)
			p.render(g);
		
	}
}
