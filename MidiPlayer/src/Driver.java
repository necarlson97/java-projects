import java.awt.Graphics2D;

public class Driver extends Game{
	
	static MidiPlayer player;
	static FileSelect fileSelect;
	
	public static void main(String[] args) {
		new Driver();
	}

	@Override
	void handleScreenResize() {
		if(player != null) player.screenResize();
	}

	@Override
	void setup() {
		fileSelect = new FileSelect();
	}

	@Override
	void runGame() {
		
		
	}
	
	static void up() {
		if(fileSelect != null) fileSelect.up();
	}
	
	static void down() {
		if(fileSelect != null) fileSelect.down();
	}
	
	static void enter() {
		if(fileSelect != null) {
			String fileName = fileSelect.enter();
			player = new MidiPlayer(fileName);
			fileSelect = null;
		} else if (player != null) {
			player.close();
			player = null;
			fileSelect = new FileSelect();
		}
	}

	@Override
	void renderGame(Graphics2D g) {
		if(player != null) player.render(g);
		else if(fileSelect != null) fileSelect.render(g);
	}
	
	

}
