import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class FolderVisual extends ManedgerVisual{
	
	
	
	private int[] topRhombusXPoints = {50, FileVisualizer.WIDTH-50, FileVisualizer.WIDTH-100, 100};
	private int[] topRhombusYPoints = {50, 50, 100, 100};
	
	private int[] bottomRhombusXPoints = {100, 50, FileVisualizer.WIDTH-50, FileVisualizer.WIDTH-100};
	private int[] bottomRhombusYPoints = {FileVisualizer.HEIGHT-100, FileVisualizer.HEIGHT-50, FileVisualizer.HEIGHT-50, FileVisualizer.HEIGHT-100};
	
	private int[] leftRhombusXPoints = {50, 100, 100, 50};
	private int[] leftRhombusYPoints = {50, 100, FileVisualizer.HEIGHT-100, FileVisualizer.HEIGHT-50};
	
	private int[] rightRhombusXPoints = {FileVisualizer.WIDTH-100, FileVisualizer.WIDTH-50, FileVisualizer.WIDTH-50, FileVisualizer.WIDTH-100};
	private int[] rightRhombusYPoints = {100, 50, FileVisualizer.HEIGHT-50, FileVisualizer.HEIGHT-100};
	
	public ParentDoor parentDoor;
	public ArrayList<Door> childrenDoors;
	public ArrayList<Item> childrenItems;
	
	
	public FolderVisual(File file){
		super(file);
		
		parentDoor = new ParentDoor(file.getParentFile(), FileVisualizer.WIDTH/2, FileVisualizer.HEIGHT-100, nameColor);
		if(file.listFiles() != null) createDoorsAndItems();
		
	}
	
	public void run(){
		checkDoors();
	}

	public void render(Graphics g) {
		
		g.setColor(Color.white);
		g.setFont(bodyFont);
		
		g.drawString(name, 5, 10);
		g.drawString(String.valueOf(file.getFreeSpace()), 5, 20);

		renderWallsAndFloor(g);		
		renderDoorsAndItems(g);
			
	}

	private void checkDoors(){
		Player player = FileVisualizer.player;
		boolean wasNearSomething = false;
		for(Item i : childrenItems){
			if(i.isNearby(player)){
				player.isNear = i;
				wasNearSomething = true;
			}
		}
		for(Door d : childrenDoors) {
			if(d.canEnter(player)){
				player.isNear = d;
				wasNearSomething = true;
			}
		}
		if(parentDoor.canEnter(player)) {
			player.isNear = parentDoor;
			wasNearSomething = true;
		}
		if(!wasNearSomething) player.isNear = null;
	}
	
	private void renderDoorsAndItems(Graphics g) {
		parentDoor.render(g);
		for(Door d : childrenDoors) d.render(g);
		for(Item i : childrenItems) i.render(g);
	}

	private void createDoorsAndItems() {
		childrenDoors = new ArrayList<Door>();
		childrenItems = new ArrayList<Item>();
		int doorX=110;
		int doorY=70;
		int hiddenDoorX=70;
		int hiddenDoorY=FileVisualizer.HEIGHT-130;
		int itemX=130;
		int itemY=200;
		Door newDoor;
		for(File child : file.listFiles()){
			if(child.isDirectory()) {
				newDoor = new Door(child, (float)doorX, (float)doorY, nameColor);
				if(newDoor.hidden) {
					newDoor = new HiddenDoor(newDoor, hiddenDoorX, hiddenDoorY, nameColor);
					hiddenDoorY-=30;
				}
				else doorX+=30;
				childrenDoors.add(newDoor);
			}
			if(child.isFile()) {
				childrenItems.add( new Item(child, (float)itemX, (float)itemY, nameColor) );
				if(itemX > FileVisualizer.WIDTH-130) {
					itemX=130;
					itemY+=30;
				}
				else itemX+=20;
			}
		}
	}
	
	public void renderWallsAndFloor(Graphics g){
		g.setColor(nameColor);
		g.fillRect(50, 50, FileVisualizer.WIDTH-100, FileVisualizer.HEIGHT-100);
		//g.fillRect(100, 100, FileVisualizer.WIDTH-200, FileVisualizer.HEIGHT-200); floor
		
		//g.setColor(new Color(nameColor.getRGB()/4 * 3));
		g.setColor(nameColor.brighter());
		g.fillPolygon(topRhombusXPoints, topRhombusYPoints, 4);
		g.fillPolygon(bottomRhombusXPoints, bottomRhombusYPoints, 4);
		g.fillPolygon(leftRhombusXPoints, leftRhombusYPoints, 4);
		g.fillPolygon(rightRhombusXPoints, rightRhombusYPoints, 4);
		
		g.setColor(Color.black);
		g.drawPolygon(topRhombusXPoints, topRhombusYPoints, 4);
		g.drawPolygon(bottomRhombusXPoints, bottomRhombusYPoints, 4);
		g.drawPolygon(leftRhombusXPoints, leftRhombusYPoints, 4);
		g.drawPolygon(rightRhombusXPoints, rightRhombusYPoints, 4);
	}

}
