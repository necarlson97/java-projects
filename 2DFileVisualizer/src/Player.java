import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	public float x;
	public float y;
	
	int width = 10;
	int height = 20;
	
	public float xVel;
	public float yVel;
	
	public float speed = (float) .02;
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	
	private int xLowerBound = 100;
	private int yLowerBound = 100;
	private int xUpperBound = FileVisualizer.WIDTH - 100;
	private int yUpperBound = FileVisualizer.HEIGHT - 100;
	
	public FolderVisual currentRoom;
	
	ManedgerVisual isNear;
	
	public Player(float x, float y, FolderVisual currentRoom){
		this.x = x;
		this.y = y;
		
		this.currentRoom = currentRoom;
	}
	
	public void run(){
		
		addVelocity();
		depreciateVelocity();
		checkBarriers();
	
		x+=xVel;
		y+=yVel;
	}
	
	private void addVelocity(){
		if(up && yVel > -1.5) yVel-= speed;
		else if(down && yVel < 1.5) yVel+= speed;
		if(left && xVel > -1.5) xVel-= speed;
		else if(right && xVel < 1.5) xVel+= speed;
	}
	
	private void depreciateVelocity() {
		double resistance = 1.2;
		if(xVel > 0) xVel/=resistance;
		else if(xVel < 0) xVel/=resistance;
		if(yVel < 0) yVel/=resistance;
		else if(yVel > 0) yVel/=resistance;
		if(Math.abs(xVel) < .01) xVel = 0;
		if(Math.abs(yVel) < .01 ) yVel = 0;
	}

	public boolean checkBarriers(){
		if(x-width/2<xLowerBound) xVel = (float) .3;
		if(x+width/2>xUpperBound) xVel = (float) -.3;
		if( y<=yLowerBound) yVel = (float) .3;
		if(y+height>=yUpperBound) yVel = (float) -.3;
		return y<=yLowerBound || y>=yUpperBound || x<=xLowerBound || x>=xUpperBound;
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(Color.WHITE);
		g.fillRect(intX-width/2, intY, width, height);
		if(isNear != null) g.drawString(isNear.name, intX, intY-10);
		
		g.setColor(Color.black);
		
		if(down) g.fillRect(intX-2, intY+4, 5, 5);
		else if(right) g.fillRect(intX+2, intY+2, 5, 5);
		else if(left) g.fillRect(intX-7, intY+2, 5, 5);
		else if(!up)g.fillRect(intX-2, intY+2, 5, 5);
		
		
		
	}
	
	public String toString(){
		return "("+x+","+y+") "+currentRoom;
	}

	public void changeRoom(ManedgerVisual isNearTo) {
		if(isNearTo.name.equals("exit")) System.exit(1);
		if(isNearTo.file.isFile()) return;
		FolderVisual prevRoom = currentRoom;
		currentRoom = new FolderVisual(isNearTo.file);
		x = FileVisualizer.WIDTH/2;
		y = FileVisualizer.HEIGHT/2;
		if(currentRoom.childrenDoors != null){
			for(Door d : currentRoom.childrenDoors){
				if(d.name.equals(prevRoom.name)) {
					x = d.x+d.width/2;
					y = d.y+d.height+10;
				}
			}
		}
		if(currentRoom.parentDoor.name.equals(prevRoom.name)){
			x = currentRoom.parentDoor.x+currentRoom.parentDoor.width/2;
			y = currentRoom.parentDoor.y-currentRoom.parentDoor.height-10;
		}
		
	}

	public void enterRoom() {
		if(isNear != null) changeRoom(isNear);
	}

}
