import java.awt.Graphics;

public class Face {
	
	Emotion currEmotion;
	
	int scale;
	
	Face(Emotion e) {
		currEmotion = e;
		scale = TweetTaster.windowHeight;
	}
	
	void setEmotion(Emotion e) {
		currEmotion = e;
	}
	
	void render(Graphics g) {
		g.drawImage(currEmotion.getFrame(), 0, 0, scale, scale, null);
	}

}
