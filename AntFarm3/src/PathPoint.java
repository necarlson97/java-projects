import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class PathPoint extends Point{
	
	public Point destination;
	public int myG;
	public int myH;
	public int prevG;
	private Comparator pc = new PathPointComparitor();
	
	public boolean overWeight;

	public PathPoint(int x, int y, int prevG, Point dest) {
		super(x, y);
		this.destination = dest;
		this.myG = calculateG();
		this.myH = calculateH();
		this.prevG = prevG;
	}
	
	public PathPoint(int x, int y, int prevG, Point dest, boolean overWeight) {
		super(x, y);
		this.destination = dest;
		this.overWeight = overWeight;
		if(overWeight) this.myG = calculateOverWeightG();
		else this.myG = calculateG();
		this.myH = calculateH();
		this.prevG = prevG;
		
	}

	public PriorityQueue<PathPoint> getPeers(LinkedList<PathPoint>moveList) {
		PriorityQueue<PathPoint> peers = new PriorityQueue<PathPoint>(16000, pc);
		if(x<99*8) peers.offer(new PathPoint(x+8, y, prevG+myG, destination));
		if(x>1*8) peers.offer(new PathPoint(x-8, y, prevG+myG, destination));
		if(y<99*8) peers.offer(new PathPoint(x, y+8, prevG+myG, destination));
		if(y>8*8) peers.offer(new PathPoint(x, y-8, prevG+myG, destination));
		peers.removeIf(p-> moveList.contains(p));
		while(peers.isEmpty()) peers = moveList.poll().getPeers(moveList);
		return peers;
	}

	private int calculateH() {
		return 10 * (Math.abs(x-destination.x) + Math.abs(y-destination.y));
	}

	private int calculateG() {
		if(Farm.farmBlocks[y/8][x/8] == Color.gray) return 3;
		else if(Farm.farmBlocks[y/8][x/8] == Color.black) return 20;
		else return 1;
	}
	
	private int calculateOverWeightG() {
		if(Farm.farmBlocks[y/8][x/8] == Color.gray) return 3;
		else if(Farm.farmBlocks[y/8][x/8] == Color.black) return 5;
		else return 1;
	}
	
	public String toString(){
		return super.toString() + " h: "+myH+" g: "+myG+" f: "+(myG+myH);
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(Color.pink);
		g.fillRect((int) x, (int) y, 8, 8);
	}

	public Point toPoint() {
		return new Point(x,y);
	}

}
