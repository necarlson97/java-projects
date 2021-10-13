package Actions;

public class Move extends Action {

	public int rows;
	public int cols;
	
	public Move(int rows, int cols) {
		super("move ");
		
		this.rows = rows;
		this.cols = cols;
		
		if(rows<0) name+="up";
		if(rows>0) name+="down";
		if(cols<0) name+="left";
		if(cols>0) name+="right";
	}

	
}
