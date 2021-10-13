import java.awt.Color;
import java.awt.Graphics;
import java.util.PriorityQueue;
import java.util.Random;

public abstract class Ant {
	
	Random r = new Random();
	
	float x;
	float y;
	
	int width = 10;
	int height = 10;
	
	float speed = 1;
	
	Color color;
	
	PriorityQueue<Action> actionQueue = new PriorityQueue<Action>();
	
	Room lastVisited;
	
	public Ant(int x, int y, Color c){
		this.x = x;
		this.y = y;
		this.color = c;
	}
	
	public void preformAction(){
		Action action = actionQueue.peek();
		
		boolean completed = false;
		if(action.name.equals("move")) {
			completed = preformMove((Move) action);
		}
		else if(action.name.equals("dig")) {
			completed = preformDig((Dig) action);
		}
		else if(action.name.equals("path")) {
			completed = preformPath((Path) action);
		}
		else if(action.name.equals("dig room")) {
			completed = preformDigRoom((DigRoom) action);
		}
		
		if(completed) actionQueue.poll();
	}

	private boolean preformMove(Move m){
		
		if(!isInTunnel()) {
			actionQueue.add(new Dig(this, (int)x, (int)y));
			return false;
		}
		
		int wWidth = AntFarm.windowWidth;
		int wHeight = AntFarm.windowHeight;
		
		if(m.destX < 0 || m.destX > wWidth) {
			System.out.println("Invalid to x");
			return true;
		}
		if(m.destY < 0 || m.destY > wHeight) {
			System.out.println("Invalid to y");
			return true;
		}
		
		if(m.destX > x) x+=speed;
		else if(m.destX < x) x-=speed;
		
		if(m.destY > y) y+=speed;
		else if(m.destY < y) y-=speed;
		
		if(x == m.destX && y == m.destY) return true;
		return false;
	}
	
	private boolean isInTunnel() {
		return y<=AntFarm.horizonY || AntFarm.isInTunnel(x, y);
	}
	
	private boolean preformDig(Dig d){
		if(!AntFarm.digs.contains(d)) AntFarm.digs.add(d);
		if(!d.finished) {
			d.size++;
			if(d.size ==d.finalSize) d.finished = true;
		}
		
		return d.finished;
	}
	
	private boolean preformPath(Path p) {
		Move last = p.moves.getLast();
		if(p.foundPath)
			return true;
		else p.tryPath();
		return false;
	}
	
	private boolean preformDigRoom(DigRoom dr) {
		if(dr.room.finished)
			return true;
		else {
			dr.room.size++;
			if(dr.room.size ==dr.room.finalSize) dr.room.finished = true;
		}
		return false;
	}

	public void run() {
		if(y<AntFarm.horizonY) {
			y++;
			return;
		}
		if(actionQueue.isEmpty()) {
			
//			int rX = r.nextInt(AntFarm.windowWidth);
//			int rY = r.nextInt(AntFarm.windowHeight-AntFarm.horizonY)+AntFarm.horizonY;
			
			int rIndex = r.nextInt(AntFarm.rooms.size());
			Room rRoom = AntFarm.rooms.get(rIndex);
			
			actionQueue.add(new Move(this, rRoom));
			actionQueue.add(new DigRoom(this, rRoom));
		}
		else preformAction();
	}
	
	public void render(Graphics g){
		int intX = (int) x;
		int intY = (int) y;
		g.setColor(color);
		g.fillRect(intX-width/2, intY-height/2, width, height);
	}
	
	public void renderQueue(Graphics g){
		g.setColor(color.brighter().brighter().brighter());
		for(Action a : actionQueue) {
			if(a.destX != -1 && a.destY != -1) g.drawOval(a.destX-width, a.destY-height, width*2, height*2);
		}
	}

}
