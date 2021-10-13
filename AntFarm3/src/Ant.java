import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public abstract class Ant extends WorldObject {


	private Comparator tc = new TaskComparitor();
	public PriorityQueue<Task> selfQueue = new PriorityQueue<Task>(10, tc);
	LinkedList<Point> moveList = new LinkedList<Point>();
	LinkedList<PathPoint> pathList = new LinkedList<PathPoint>();
	Stack<LinkedList<Point>> moveSetStack = new Stack<LinkedList<Point>>();
	
	public Ant(int x, int y, Color c, Task t){
		super(x,y,8,8);
		selfQueue.offer(t);
		selfQueue.offer(new Task("chill", 10));
		
		ArrayList<Waypoint> wps = Farm.waypoints;
	}	
	
	public void tick(){
	}
	
	public void path(Point dest) {
		
		// Start the pathing
		if(pathList.isEmpty()) pathList.add(new PathPoint((x/8)*8,(y/8)*8, 0, dest));
		// Found a good path!
		else if(pathList.getLast().equals(dest)){
			for(PathPoint p : pathList){
				moveList.add(p.toPoint());
			}
			pathList.clear();
			selfQueue.poll();
		}
		// Add the next path point
		else {
			pathList.add(pathList.getLast().getPeers(pathList).poll());
		}
	}
	
	public void overWeightPath(Point dest) {
		
		if(pathList.isEmpty()) pathList.add(new PathPoint((x/8)*8,(y/8)*8, 0, dest, true));
		else if(pathList.getLast().equals(dest)){
			for(PathPoint p : pathList){
				moveList.add(p.toPoint());
			}
			pathList.clear();
			selfQueue.poll();
		}
		else pathList.add(pathList.getLast().getPeers(pathList).poll());
	}
	
	public void fall() {
		if(y<10*8) y+=1; // Falling to ground
	}

	public void move() {
		int nx = moveList.peek().x;
		int ny = moveList.peek().y;
//		System.out.println("Moving x: "+x+" y: "+y+
//				" next x: "+nx+" next y: "+ny);
//		System.out.println("Move list size: "+moveList.size()+" Last move: "+moveList.getLast());
		
		if(nx  > this.x) this.x+=1;
		else if(nx < this.x) this.x -=1;
		else if(ny > this.y) this.y+=1;
		else if(ny < this.y) this.y -=1;
		else if(nx==x && ny == y) moveList.poll();
		
	}
}