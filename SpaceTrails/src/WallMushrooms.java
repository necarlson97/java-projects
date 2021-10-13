
public class WallMushrooms extends WallResource{
	
	public WallMushrooms(int x, int y) {
		super(x, y, "WallMushrooms.png", 10);
	}
	
	public WallMushrooms(LabModule lab, int row, int col) {
		super( lab, row, col, "WallMushrooms.png", 10);
	}
	
	public void run(int count){
		super.run(count);
	}
	
	@Override
	public void interact(){
		resourceLevel = 0;
	}

}
