import java.awt.image.BufferedImage;

public class FrameState {
	
	public String actionName;
	public BufferedImage sprite;
	
	
	public FrameState(String actionName, BufferedImage sprite){
		this.actionName = actionName;
		this.sprite = sprite;
	}

}
