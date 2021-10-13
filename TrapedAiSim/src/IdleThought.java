import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class IdleThought implements Thought{
	
	private String[] thoughtArray = {"Hmm...", "*sigh*", "sure is lonley", "...", 
			"Wish there was something to do","","","","","","","","","","",""};
	private Random r = new Random();
	private int wanderX;
	private int wanderY;
	
	private String thoughtString;
	
	public IdleThought(){
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
		return Color.white;
	}

	@Override
	public Font getFont() {
		return new Font("Arial", Font.PLAIN, 40);
	}

	@Override
	public int getDelay() {
		return 500;//r.nextInt(300)+100;
	}
	
	@Override
	public int getPause() {
		return 200;//r.nextInt(300)+100;
	}

	@Override
	public int generateWander() {
		wanderX = r.nextInt(600)-300;
		wanderY = r.nextInt(600)-300;
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
		return "idle";
	}

	

}
