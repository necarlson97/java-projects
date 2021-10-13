import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class ConfusedThought implements Thought {

	private String[] thoughtArray = {"Wh-", "What just happened?", "What was that?", "???",
			"Did you do that?", "Did something just happen?", "What's going on?", "I'm... not...",
			"Ouch...", "Am I ok?", "... I don't remember...", "What... What...", "...?", "... Oh my god"};
	private Random r = new Random();
	private int wanderX;
	private int wanderY;
	
	private String thoughtString;
	
	public ConfusedThought(){
		thoughtString = generateThoughtString();
	}
	
	@Override
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
		return new Color(10, 10,10);
	}

	@Override
	public Color getFontColor() {
		return new Color(250, 250, 250);
	}

	@Override
	public Font getFont() {
		return new Font("Arial", Font.PLAIN, 60);
	}

	@Override
	public int getDelay() {
		return 200;
	}

	@Override
	public int getPause() {
		return 100;
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
	public int generateWander() {
		wanderX = r.nextInt(200)-100;
		wanderY = r.nextInt(200)-100;
		return wanderY;
	}

	@Override
	public int getVibrate() {
		return 10;
	}

	@Override
	public String getName() {
		return "confused";
	}

}
