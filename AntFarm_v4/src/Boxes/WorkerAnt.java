package Boxes;

import java.awt.Color;

import Actions.Move;
import Main.AntFarm;

public class WorkerAnt extends Ant{
	
	Destination currDest;

	public WorkerAnt(int row, int col) {
		super(row, col, new Color((float).6, (float).2, (float).2));
	}
	
	public void run(){
		if(currDest == null) currDest = AntFarm.destinations[0];
		if(actionStack.isEmpty()) addPathToStack( findPath(currDest) );
		super.run();
	}

}
