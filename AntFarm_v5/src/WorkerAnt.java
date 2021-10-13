import java.awt.Color;

public class WorkerAnt extends Ant{

	public WorkerAnt(int x, int y) {
		super(x, y, Color.white);
		float red = 0;
		float grn = r.nextFloat();
		float blu = r.nextFloat();
		color = new Color(red, grn, blu);
	}
	
	public void run(){
		super.run();
	}

}
