import java.awt.Font;
import java.awt.Graphics;

public class Face {
	
	Emotion currEmotion;
	
	int scale;
	Font font;
	
	Face(Emotion e) {
		currEmotion = e;
		scale = TweetTaster.windowHeight/5;
		font = new Font("Consolas", 0, scale);
	}
	
	void setEmotion(Emotion e) {
		currEmotion = e;
	}
	
	void render(Graphics g) {
		g.setColor(currEmotion.color);
		g.setFont(font);
		g.drawString(currEmotion.getDisplay(), 0, TweetTaster.windowHeight/2);
	}

}
