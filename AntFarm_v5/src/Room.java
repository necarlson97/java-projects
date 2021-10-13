import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class Room {
	
	int finalSize = 40;
	int size = 0;
	boolean finished;
	
	int x;
	int y;
	
	String name;
	
	int[] roomDistances = new int[AntFarm.MAXROOMS]; 
	
	Color color = Color.red;
	int index;
	
	public Room(String name, int i, int x, int y) {
		this.x=x;
		this.y=y;
		this.name=name;
		this.index=i;
		initalizeDistances();
	}
	
	int distanceBetween(Room first, Room second){
		return (int) Math.sqrt(Math.pow((first.x-second.x), 2) 
				+ Math.pow((first.y-second.y), 2));
	}
	
	void setDistances(Room first, Room second, int index){
		first.roomDistances[index] = (int) Math.sqrt(Math.pow((first.x-second.x), 2) 
				+ Math.pow((first.y-second.y), 2));
	}
	
	
	void initalizeDistances(){

		int i=0;
		for(Room f : AntFarm.rooms) {
			int j=0;
			for(Room s : AntFarm.rooms){
				setDistances(f, s, j);
				j++;
			}
			
			setDistances(this, f, i);
			setDistances(f, this, index);
			
			i++;
		}
		
	}
	
	
	
	public void render(Graphics g) {
	
		if(finished) g.setColor(new Color(200, 200, 200));
		else g.setColor(new Color(100, 100, 100));
		g.fillOval(x-size/2, y-size/2, size, size);
		
		g.setColor(color);
		g.drawOval(x-finalSize/2, y-finalSize/2, finalSize, finalSize);
		
		if(AntFarm.debug) {
			g.setColor(color.darker().darker());
			int i=0;
			for(Room r : AntFarm.rooms){
				int[] xs = {x, r.x, r.x, x};
				int[] ys = {y, r.y, r.y+roomDistances[i]/100, y+roomDistances[i]/100};
				g.fillPolygon(xs, ys, xs.length);
				i++;
			}
		}
		
	}
	
	public String toString(){
		return name + " ("+x+","+y+")";
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Room))
			throw new ClassCastException("An Action object expected.");
		Room otherRoom = (Room) o;
		return (this.x == otherRoom.x && this.y == otherRoom.y);
	}

}
