import java.awt.Color;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;

public abstract class Ant extends GameObject implements Runnable {
	
	int speed = 1;
	Thread thread;
	
	public Ant(int row, int col, Color color) {
		super(row, col, color);
		thread = new Thread(this);
		thread.start();
	}
	
	public void update() {
		super.update();
	}
	
	public Stack<Cell> aStar(Cell start, Cell finish) {
		Stack<Cell> path = new Stack<Cell>();
		PriorityQueue<Node> front = new PriorityQueue<Node>();
		Hashtable<Cell, Boolean> visited = new Hashtable<Cell, Boolean>();
		
		int cost = 0;
		Node n = new Node(start, finish, cost, null);
		front.add(n);
		while(n.cell != finish && !front.isEmpty()) {
			n = front.poll();
			Farm.get(n.cell).color = Color.red;
			visited.put(n.cell, true);
			Node[] neighbors = n.getNeighbors();
			for(Node neighbor : neighbors) {
				if(!visited.contains(neighbor.cell)) front.add(neighbor);
			}
			try {
				thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Farm.get(n.cell).color = Color.blue;
		}
		
		while(n.parent != null) {
			path.push(n.cell);
			n = n.parent;
		}
		
		return path;
	}
	
	public void moveTo(Cell c) {
		if(Math.abs(c.col - cell.col) > 1 || Math.abs(c.col - cell.col) > 1) {
			System.out.println("Cannot move from "+cell+" to "+c);
			return;
		}
		if(Farm.objs.containsKey(c)) {
			try {
				thread.sleep(Farm.objs.get(c).moveCost * 100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cell = c;
		System.out.println("Moved to "+c);
	}
	
	public void pathTo(Cell dest) {
		for(Cell c : aStar(cell, dest)) {
			moveTo(c);
		}
	}
	
	public class Node implements Comparable<Node> {
		Cell cell;
		Cell dest;
		int cost;
		int value;
		Node parent;
		Node(Cell _cell, Cell _dest, int _cost, Node _parent) {
			cell = _cell;
			dest = _dest;
			cost = _cost;
			value = getValue();
			parent = _parent;
		}
		
		private int getValue() {
			int dist = Math.abs(dest.row - cell.row) + 
					Math.abs(dest.col - cell.col);
			return cost + dist;
		}
		
		public Node[] getNeighbors() {
			Node[] neighbors = new Node[4];
			
			Cell up = new Cell(cell, -1, 0);
			neighbors[0] = new Node(up, dest, cost+1, this);
			Cell right = new Cell(cell, 0, 1);
			neighbors[1] = new Node(right, dest, cost+1, this);
			Cell down = new Cell(cell, 1, 0);
			neighbors[2] = new Node(down, dest, cost+1, this);
			Cell left = new Cell(cell, 0, 1);
			neighbors[3] = new Node(left, dest, cost+1, this);
			
			return neighbors;
		}

		@Override
		public int compareTo(Node n) {
			return value - n.value;
		}
	}
	

}
