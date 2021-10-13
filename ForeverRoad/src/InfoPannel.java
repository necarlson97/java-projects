import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class InfoPannel extends Canvas{
	
	ForeverRoad fr;
	
	AffineTransform identity = new AffineTransform();
	AffineTransform trans = new AffineTransform();
	
	JFrame frame;
	static int windowWidth = 500;
	static int windowHeight = 300;
	
	BufferedImage wheel;
	int wheelX = 3 * windowWidth/4;
	int wheelY = windowHeight/2;
	
	InfoPannel(ForeverRoad fr) {
		this.fr = fr;
		createView();
		wheel = Car.loadSprite("Car", "Steering Wheel");
	}

	public void createView() {
		
		// We will just name this window the same thing we named our file
	    	String windowName = this.getClass()+"";
	    	windowName = windowName.replace("class ", "");	
	    	frame = new JFrame(windowName);
			
	    	// We will set the size of our window based on the varables above
		frame.setPreferredSize(new Dimension(windowWidth, windowHeight));
		frame.setMaximumSize(new Dimension(windowWidth, windowHeight));
		frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
		
		// We want the window to stay the same size
		frame.setResizable(false);
		// to appear to the left of the game
		frame.setLocation(0, fr.windowHeight-windowHeight);
		// And to be visible and 'ontop' when the game starts up
		frame.add(this);
		frame.setVisible(true);
	}

	void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g1 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g1;

		cleanFrame(g);
	
		renderInfo(g);
		
		g.dispose();
		bs.show();
	}
	
	private void cleanFrame(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
	}
	
	private void renderInfo(Graphics g) {
		fr.net.render(g);
		
		Graphics2D g2d = (Graphics2D)g.create();
		
		trans.setTransform(identity);
		
		g2d.translate(wheelX-wheel.getWidth()/2, wheelY-wheel.getHeight()/2);
		trans.rotate(fr.car.turn, wheel.getWidth()/2, wheel.getHeight()/2);
		
		g2d.drawImage(wheel, trans, null);
		
		g.drawString("Success: "+fr.net.success, wheelX, windowHeight-50);
	}
}
