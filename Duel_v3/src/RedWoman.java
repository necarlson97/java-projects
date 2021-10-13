import java.awt.Color;

public class RedWoman extends Enemy{

	public RedWoman(int x, int y) {
		super(x, y, new Color(225, 190, 64), "Red Woman v1.png");
	
		gunBarrelX = x+65;
		gunBarrelY = y+55;
		torsoX = x+110;
		torsoY = y+60;
		
		reflexTime = r.nextInt(20)+30;
	}

}
