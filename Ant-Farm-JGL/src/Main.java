import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.LWJGLException;

public class Main {

	private Main(){
		try{
			new SetupDisplay(1800, 1000, false, "Ant Farm").create();
		}
		catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	public static void gameLoop(){
		while(!Display.isCloseRequested()){
			//Where the gmae happens
			Display.update();
		}
	}
	
	public static void main(String[] args){
		new Main();
	}
}
