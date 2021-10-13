import java.awt.Color;
import java.awt.Graphics;

public class Dig extends Action{
	
	int finalSize = 18;
	
	int size = 0;
	
	boolean finished = false;

	public Dig(Ant a,int x, int y) {
		super(a, "dig", x, y);
		priority = 8;
	}
	
	public void render(Graphics g){
		if(finished) g.setColor(new Color(200, 200, 200));
		else g.setColor(new Color(100, 100, 100));
		g.fillOval(destX-size/2, destY-size/2, size, size);
	
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Dig))
			throw new ClassCastException("An Action object expected.");
		Dig otherDig = (Dig) o;
		return (this.destX == otherDig.destX && this.destY == otherDig.destY);
	}

}
