package Boxes;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import Actions.Action;
import Actions.DigOut;
import Actions.Move;
import Main.AntFarm;

public abstract class Ant extends Box{
	
	Stack<Action> actionStack = new Stack<Action>();
	
	int weight;

	public Ant(int row, int col, Color color) {
		super(row, col, color);
	}
	public void run(){
		while(row < AntFarm.boxRows-1 && AntFarm.bgBoxes[row+1][col] instanceof EmptyBox) row++;
		if(!actionStack.isEmpty()) {
			if(actionStack.peek() instanceof Move) 
				moveToNext((Move) actionStack.pop());
			else if(actionStack.peek() instanceof DigOut) 
				digAt((DigOut) actionStack.pop());
		}
	}
	
	public Queue<Move> findPath(Destination dest){
		Queue<Move> path = new LinkedList<Move>();
		
		while(row+path.peek().rows != dest.row || col+path.peek().cols != dest.col){
			if(row+path.peek().rows < dest.row) path.add(new Move(1, 0));
			if(row+path.peek().rows > dest.row) path.add(new Move(-1, 0));
			
			if(col+path.peek().cols < dest.col) path.add(new Move(0, 1));
			if(col+path.peek().cols < dest.col) path.add(new Move(0, -1));
		}
		
		return path;
		
	}
	
	public void addPathToStack(Queue<Move> movePath){
		actionStack.push(movePath.poll());
	}
	
	private void digAt(DigOut toDig) {
		if(toDig.row < 0 || toDig.row > AntFarm.boxRows 
				|| toDig.col < 0 || toDig.col >= AntFarm.boxCols){
			System.out.println(toDig.name+" tried to dig off of map");
		}
		
		else if(AntFarm.bgBoxes[toDig.row][toDig.col] instanceof DirtBox){
			AntFarm.bgBoxes[toDig.row][toDig.col] = new EmptyBox(toDig.row, toDig.col);
		}
		
		
	}
	public void moveToNext(Move move){
		if(row+move.rows < 0 || row+move.rows >= AntFarm.boxRows 
				|| col+move.cols < 0 || col+move.cols >= AntFarm.boxCols){
			System.out.println(move.name+" tried to move off of map");
		}
			
		else if(AntFarm.bgBoxes[row+move.rows][col+move.cols] instanceof EmptyBox){
			row+=move.rows;
			col+=move.cols;
		}
		else {
			actionStack.push(move);
			actionStack.push(new DigOut(row+=move.rows, col+=move.cols));
		}
			
	}
	
	
	
}
