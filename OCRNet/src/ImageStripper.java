import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ImageStripper {
	
	LinkedList<Card> trainingCards = new LinkedList<Card>();
	LinkedList<Card> testCards = new LinkedList<Card>();
	LinkedList<Card> ericCards = new LinkedList<Card>();
	
	BufferedImage renderSlice = null;
	
	Driver driver;
	
	boolean done = false;
	
	ImageStripper(Driver d) {
		driver = d;
	}
	
	public void loadTrainingData() {
		for(int i=0; i<10; i++) {
			nextImage(trainingCards, "Train", i);
		}
		Collections.shuffle(trainingCards);
	}
	
	public void loadTestData() {
		for(int i=0; i<10; i++) {
			nextImage(testCards, "Test", i);
		}
		Collections.shuffle(testCards);
	}
	
	public void loadEricData() {
		for(int i=0; i<10; i++) {
			nextImage(ericCards, "Eric", i);
		}
		Collections.shuffle(ericCards);
	}
	
	private void nextImage(LinkedList<Card> cardList, String folder, int lable) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(folder+"/"+lable+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int x=0; x<img.getWidth(); x+=Driver.cardSize) {
			for(int y=0; y<img.getHeight(); y+=Driver.cardSize) {
				addCard(cardList, lable, img, x, y);
			}
		}
	}
	
	private void addCard(LinkedList<Card> cardList, int lable, BufferedImage img, int x, int y) {
		BufferedImage slice = img.getSubimage(x, y, Driver.cardSize, Driver.cardSize);
		renderSlice = slice;
		float[] inputs = new float[Driver.cardSize * Driver.cardSize];
		
		int i=0;
		for(int sliceX = 0; sliceX < Driver.cardSize; sliceX++) {
			for(int sliceY = 0; sliceY < Driver.cardSize; sliceY++) {
				inputs[i] = floatFromRGB(slice.getRGB(sliceX, sliceY));
				i++;
			}
		}
		
		
		if(!emptyInput(inputs)) cardList.add(new Card(inputs, lable));
		
	}
	
	private boolean emptyInput(float[] f) {
		boolean empty = true;
		for(int i=0; i<f.length; i++) {
			if(f[i] != 0) empty = false;
		}
		return empty;
	}
	
	private float floatFromRGB(int rgb) {
		int blue = rgb & 0xff;
		int green = (rgb & 0xff00) >> 8;
		int red = (rgb & 0xff0000) >> 16;
		
		float gray = (float)(red * blue * green)/16581375;
		return gray;
	}
	
	public String toString() {
		String str = "Nope";
		return str;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		
		g.drawImage(renderSlice, 20, 50, Driver.cardSize*Driver.pixelSize, 
				Driver.cardSize*Driver.pixelSize, null);
		if(!done) g.drawString("Loading Images", 20, 50);
		else g.drawString("Done", 20, 50);
	}

}
