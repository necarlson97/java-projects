
public class DigRoom extends Action{
	
	Room room;

	public DigRoom(Ant a, Room r) {
		super(a, "dig room", r.x, r.y);
		room = r;
	}
	
	

}
