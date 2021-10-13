import java.awt.Color;
import java.awt.Graphics;

public class TreeMidground extends StageTile{

	public TreeMidground() {
		super((float).5, Birds.horizonY-60*5, "TreesMidground.png", 256, 64);
	}
	
	public void render(Graphics g){
		g.setColor(new Color(0, 80, 0));
		g.fillRect(0, shiftingY+drawHeight/2, Birds.windowWidth, (int) (Birds.windowHeight - y));
		super.render(g);
	}
	
	

}
