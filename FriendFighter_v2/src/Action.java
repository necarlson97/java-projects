import java.awt.image.BufferedImage;

public class Action {
	
	String name;
	FrameState[] frameStates;
	
	public Action(String givenName, Fighter fighter){
		name = givenName;
		BufferedImage sprites[] = fighter.getSpritesFromSheet(fighter.name + name + ".png");
		
		frameStates = new FrameState[sprites.length];
		for(int i=0; i<sprites.length; i++){
			frameStates[i] = new FrameState(name+i, sprites[i]);
		}
		frameStates[sprites.length-1].actionName += " last frame";
	}
	
	public String toString(){
		return name;
	}

}
