import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class TrainCar extends SpritedBackgroundObject{
	
	int counter;
	int bump;
	int bumpHeight;
	
	static String[] differentCarNames ={"Passanger Train Car.png", "Cargo No-Rails Train Car.png", "Cargo Rails Train Car.png"}; 
	
	static Random r = new Random();
	
	public TrainCar(int x, int y, int bump) {
		super(x, y, 0, 0, differentCarNames[r.nextInt(differentCarNames.length)]);
		this.bump = bump;
		bumpHeight = r.nextInt(4)-1;
		
		width = sprite.getWidth() * 5;
		height = sprite.getHeight() * 5;
	}
	
	public void run(){
		if(counter%50 == bump) y += bumpHeight;
		if(counter%50 == bump+5) {
			y -= bumpHeight;
			bumpHeight = r.nextInt(4)-1;
		}
		if(counter%5 == 0) Duel.createSteam((int)x+815, (int)y+170);
		counter++;
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		g.drawImage(sprite, intX, intY, width, height, null);
	}
	

}
