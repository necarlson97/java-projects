import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
	
	Game game;
	public MouseInput(Game g) {
		this.game = g;
	}
	
	public void mousePressed(MouseEvent e) {
	    game.click(e);
	}

}
