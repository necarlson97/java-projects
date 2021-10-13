import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Simulator {

	static Random rand = new Random();
	
	Team team1;
	Team team2;
	
	static int playerSize = 50;
	
	int victory = 0;
	
	String showString = "";
	
	int fights;
	int turns;
	
	boolean everRun;
	
	Simulator() {
		team1 = new Team(rand.nextInt(5)+1, Color.red, 100, this);
		team2 = new Team(rand.nextInt(5)+1, Color.blue, 500, this);

		team1.setEnemies(team2);
		team2.setEnemies(team1);
	}
	
	void update() {
		if(everRun) takeTurn();
	}
	
	void takeTurn() {
		turns ++;
		team1.takeTurn();
		team2.takeTurn();
	}

	void lost(Team losers) {
		fights++;
		if(losers == team1) team1 = new Team(losers);
		else team2 = new Team(losers);
		team1.setEnemies(team2);
		team2.setEnemies(team1);
	}

	public void render(Graphics2D g) {
		team1.render(g);
		team2.render(g);
		
		g.setColor(Color.white);
		g.drawString("Average turns: "+(float)turns/fights, 50, 20);
		g.setColor(Color.white);
		g.drawString(showString, 50, 50);
		
	}
}
