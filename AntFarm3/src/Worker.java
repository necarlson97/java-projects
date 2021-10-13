

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Worker extends Ant{
	
	Random r = new Random();
	
	//private String task;
	
	private int weight = 0;
	

	public Worker(int x, int y, Task t) {
		super(x, y, Color.orange, t);

	}

	public void tick() {
		if(y<9 *8){
			fall();
			return;
		}
		super.tick();
		ArrayList<Waypoint> wps = Farm.waypoints;
		
		if(Farm.farmBlocks[(int) (y/8)][(int) (x/8)] == Color.black){
			Farm.farmBlocks[(int) (y/8)][(int) (x/8)] = Color.gray;
			weight +=1;
		}
		
		if(moveList.isEmpty() && selfQueue.peek().taskName.equals("path")) {
			path(selfQueue.peek().target);
		}
		
		if(moveList.isEmpty() && selfQueue.peek().taskName.equals("overweight path")) {
			overWeightPath(selfQueue.peek().target);
		}
		
		// Dump
		if(selfQueue.peek().taskName.equals("dump") && moveList.isEmpty()){
			Farm.holeWeight += weight;
			weight=0;
			selfQueue.poll();
		}
		// Must dump weight
		boolean tryingToGetToHole = (selfQueue.peek().taskName == "dump" 
				|| (selfQueue.peek().taskName == "path" && selfQueue.peek().target.equals(wps.get(0))) );
		if(weight>=10 && !tryingToGetToHole) {
			selfQueue.add(new Task("dump", 1));
			selfQueue.add(new Task("overweight path", wps.get(0), 0));
			moveSetStack.push(moveList);
			moveList.clear();
		}
		
		// Walk along path
		if(!moveList.isEmpty()) move();
		else if(!moveSetStack.isEmpty() && pathList.isEmpty()) moveList = moveSetStack.pop();
		// if tasks purpose was to visit, and that was done
		else if(pathList.isEmpty() && !selfQueue.peek().taskName.equals("chill")) selfQueue.poll();
		
		// Just for testing
		else if(pathList.isEmpty()){
			if(r.nextInt(5) == 0) {
				selfQueue.add(new Task("visit", "hole", 8));
				selfQueue.add(new Task("path", wps.get(0), 0));
			}
			if(r.nextInt(5) == 0) {
				selfQueue.add(new Task("visit", "trash", 7));
				selfQueue.add(new Task("path", wps.get(1), 0));
			}
			if(r.nextInt(5) == 0) {
				selfQueue.add(new Task("visit", "food", 6));
				selfQueue.add(new Task("path", wps.get(2), 0));
			}
			if(r.nextInt(5) == 0) {
				selfQueue.add(new Task("visit", "brood", 6));
				selfQueue.add(new Task("path", wps.get(3), 0));
			}
		}
		
	}

	public void render(Graphics g) {
//		System.out.print("Worker Render x: "+x+" y: "+y+" w: "+weight);
//		if(!selfQueue.isEmpty()) System.out.print(" t: "+selfQueue.peek());
//		if(!moveList.isEmpty()) System.out.print(" ml: "+moveList.peek());
//		if(!pathList.isEmpty()) System.out.print(" pl: "+pathList);
//		System.out.println();
		
		g.setColor(Color.orange);
		g.fillRect((int) x, (int) y, 8, 8);
		
		for(PathPoint p : pathList){
			p.render(g);
		}
		
		for(Point m : moveList){
			m.render(g);
		}
		
	}


}
