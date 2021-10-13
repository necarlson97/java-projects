
public class PlayerDot extends Dot{
	
	static boolean left;
	static boolean right;
	static boolean forward;
	static boolean shoot;

	public PlayerDot(int x, int y, int teamId) {
		super(x, y, teamId);
	}
	
	public void run(){
		super.forward = forward;
		super.left = left;
		super.right = right;
		
		if(shoot) shoot();
		super.run();
		shoot = false;
	}

}
