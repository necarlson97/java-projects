import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class WatchedThought implements Thought{
	
	private String[] thoughtArray = {"Oh!", "Hello?", "...is there someone there?", "Who's there?",
			"I... I think I can see someone...", "Are you there?", "Where are you?", "Hello there!",
			"Can you hear me?", "Hi!", "I believe I see something", "Can.. can you see me?"};
	private Random r = new Random();
	private int wanderX;
	private int wanderY;
	
	private String thoughtString;
	
	public WatchedThought(){
		thoughtString = generateThoughtString();
	}

	public String generateThoughtString() {
		int rand = r.nextInt(thoughtArray.length);
		thoughtString = thoughtArray[rand];
		return thoughtString;
	}
	
	@Override
	public String getThoughtString() {
		return thoughtString;
	}

	@Override
	public Color getBGColor() {
		return Color.black;
	}
	
	@Override
	public Color getFontColor() {
		return Color.yellow;
	}

	@Override
	public Font getFont() {
		return new Font("Arial", Font.PLAIN, 60);
	}

	@Override
	public int getDelay() {
		return 200;//r.nextInt(300)+100;
	}
	
	@Override
	public int getPause() {
		return 160;//r.nextInt(300)+100;
	}


	@Override
	public int generateWander() {
		wanderX = r.nextInt(200)-100;
		wanderY = r.nextInt(200)-100;
		return wanderY;
	}

	@Override
	public int getVibrate() {
		return 0;
	}

	@Override
	public int getWanderX() {
		return wanderX;
	}

	@Override
	public int getWanderY() {
		return wanderY;
	}

	@Override
	public String getName() {
		return "watched";
	}

	

}
