import java.awt.Color;
import java.awt.Graphics;

public class SandForeground extends StageTile{

	public SandForeground() {
		super(1, Birds.windowHeight-420, "SandForeground.png", 256, 64);
	}
	
	public void render(Graphics g){
		g.setColor(new Color(255, 202, 108));
		g.fillRect(0, shiftingY+drawHeight, Birds.windowWidth, (int) (Birds.windowHeight - y));
		super.render(g);
	}
	
}
