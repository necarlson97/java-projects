import java.awt.Color;

public class Tmily extends Fighter{

	public Tmily(int x) {
		super("Tmily", x, FriendFighter.floorY, Color.BLUE, 100);
		attackSet.add(new Attack("light", (float).2, 1, 25, 25, -45, this));
		attackSet.add(new Attack("medium", (float).5, 7, 15, 15, -40, this));
	}
}
