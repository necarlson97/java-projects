import java.awt.Color;
import java.awt.Font;

public interface Thought{
	
	
	public String generateThoughtString();
	
	public String getThoughtString();
	
	public Color getBGColor();
	
	public Color getFontColor();
	
	public Font getFont();
	
	public int getDelay();
	
	public int getPause();
	
	public int getWanderX();
	
	public int getWanderY();
	
	public int generateWander();
	
	public int getVibrate();
	
	public String getName();
	
	
	
}