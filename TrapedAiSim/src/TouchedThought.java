import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class TouchedThought implements Thought{
	
	private String[] thoughtArray = {"OW!", "WHY?!?!??!?!!", "NO!", "PLEASE!!!!!!!",
			"STOP STOP STOP", "OH MY GOD", "PLEASE STOP PLEASE STOP", "IT HURTS!!", "LET GO",
			"NO MORE", "MAKE IT STOP", "WHY ME???", "AHHHHHHHHHHHHHHHHHHH", "JESUS NO"};
	private Random r = new Random();
	private int wanderX;
	private int wanderY;
	
	private String thoughtString;
	
	public TouchedThought(){
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
		return Color.red;
	}
	
	@Override
	public Color getFontColor() {
		return Color.black;
	}

	@Override
	public Font getFont() {
		return new Font("Arial", Font.BOLD, 160);
	}

	@Override
	public int getDelay() {
		return 50;//r.nextInt(300)+100;
	}
	
	@Override
	public int getPause() {
		return 45;//r.nextInt(300)+100;
	}


	@Override
	public int generateWander() {
		wanderX = r.nextInt(1000)-500;
		wanderY = r.nextInt(1000)-500;
		return wanderY;
	}

	@Override
	public int getVibrate() {
		return 500;
	}

	@Override
	public int getWanderX() {
		return wanderX; //= r.nextInt(1000)-500;
	}

	@Override
	public int getWanderY() {
		return wanderY; //= r.nextInt(1000)-500;
	}

	@Override
	public String getName() {
		return "touched";
	}

	

}
