import java.awt.Color;

public class Sook extends Fighter{

	public Sook(int x) {
		super("Sook", x, FriendFighter.floorY, Color.PINK, 140);
		attackSet.add(new Attack("light", (float).2, 1, 17, 15, -40, this));
		attackSet.add(new Attack("medium", (float).5, 4, 10, 10, -45, this));
	}

}
