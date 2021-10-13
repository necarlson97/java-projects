
public class Move extends Action{

	public Move(Ant a, int x, int y) {
		super(a, "move", x, y);
	}
	
	public Move(Ant a, Room r) {
		super(a, "move", r.x, r.y);
	}

}
