import java.awt.Color;
import java.awt.Font;

public class BlankThought implements Thought {

	@Override
	public String getThoughtString() {
		return "";
	}

	@Override
	public Color getBGColor() {
		return Color.black;
	}

	@Override
	public Color getFontColor() {
		return Color.black;
	}

	@Override
	public Font getFont() {
		return null;
	}

	@Override
	public int getDelay() {
		return 0;
	}
	
	@Override
	public int getPause() {
		return 0;
	}

	@Override
	public String generateThoughtString() {
		return "";
	}

	@Override
	public int generateWander() {
		return 0;
	}

	@Override
	public int getVibrate() {
		return 0;
	}

	@Override
	public int getWanderX() {
		return 0;
	}

	@Override
	public int getWanderY() {
		return 0;
	}

	@Override
	public String getName() {
		return "blank";
	}

}
