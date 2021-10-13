import java.util.LinkedList;
import java.util.Queue;

public class BotDot extends Dot{
	
	int FORWARD = 0;
	int LEFT = 1;
	int RIGHT = 2;
	int SHOOT = 3;
	
	Queue<Integer> actions = new LinkedList<Integer>();
	

	public BotDot(int x, int y, int teamId) {
		super(x, y, teamId);
	}
	
	public void nextAction(){
		forward = false;
		left = false;
		right = false;
		
		int next = actions.poll();
		if(next == FORWARD) forward = true;
		if(next == LEFT) left = true;
		if(next == RIGHT) right = true;
		if(next == SHOOT) shoot();
	}
	
	private void addAction(int action, int steps){
		
		for(int i=0; i<steps; i++){
			actions.add(action);
		}

	}
	
	public void run(){
		
		if(actions.isEmpty()) {
			int rAction = r.nextInt(4);
			addAction(rAction, r.nextInt(10));
		}
		else nextAction();
		
		super.run();
	}

}
