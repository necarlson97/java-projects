import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ImageToAscii extends Game{
	
	static LinkedList<AsciiFont> availableFonts = new LinkedList<AsciiFont>();
	static int currFontIndex = 0;
	static AsciiFont currFont;
	
	int startingPointSize = 5;
	
	static int margin;
	
	static float windowScale = 1;
	//static float textScale = 1;
	
	static String grayScaleString = "$$$$$$$$$$@@@@@@BBBB%%%8&&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"\"^^^^``````''''''''...........                    ";
	static char[] grayScaleChars = grayScaleString.toCharArray();
	
	static StringifyImage currImage;
	String[] imageNames = {"Statue of liberty"};
	int imageIndex = 0;
	
	String overlayString = "";
	
	static boolean renderImage = true;
	static boolean renderAscii = true;
	
	boolean hasNext = true;
	static boolean output = false;
	
	static int interval = 1;
	
	public static void main(String[] args){
		new ImageToAscii();
	}
	
	public ImageToAscii(){
		
	}
	
	public void getAvailableFonts(){
		FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, 
				RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] allFonts = e.getAllFonts();
	    Rectangle2D monoBounds = new Font("AndaleMono", Font.PLAIN, startingPointSize).getStringBounds(grayScaleString, frc);
	    double monoWidth = monoBounds.getWidth();
	    
	    for (Font f : allFonts) {
	    	Font resized = f.deriveFont((float) startingPointSize);
	    	Rectangle2D iBounds = resized.getStringBounds("i", frc);
	        Rectangle2D mBounds = resized.getStringBounds("m", frc);
	        Rectangle2D fullBounds = resized.getStringBounds(grayScaleString, frc);
	        if (iBounds.getWidth() == mBounds.getWidth() && fullBounds.getWidth() == monoWidth) {
	        	AsciiFont newAsciiFont = new AsciiFont(resized, mBounds.getWidth());
	        	availableFonts.add(newAsciiFont);
	        }
	    }
	    changeFont(0);
	    changeFontSize(0);
	}
	
	@Override
	void setup() {
		margin = windowWidth / 100;
		getAvailableFonts();
		
		loadImage(imageNames[imageIndex]);	
	}

	private void loadImage(String imageName) {
		try {
		    currImage = new StringifyImage(ImageIO.read(new File(imageName+".png")), currFont);
		} catch (IOException e) {
			System.out.println("Could not load "+imageName);
			return;
		}
		
		float scaleX = (float) (windowWidth-margin*2)/currImage.image.getWidth();
		float scaleY = (float) (windowHeight-margin*4)/currImage.image.getHeight();
		
		if(scaleY < scaleX) windowScale = scaleY;
		else windowScale = scaleX;
	}

	@Override
	void runGame() {
		if(hasNext == true){
			hasNext = nextChar(interval);
			overlayString = currImage.imageString;
		}
		else if(output){
			output = false;
			ouputTextToFile(imageNames[imageIndex], currImage.imageString);
		}
	}
	
	private void ouputTextToFile(String fileName, String contents) {
		File desiredFile = new File(fileName+".txt");

		if(contents.length() == 0) {
			desiredFile.delete();
			return;
		}
		
		try{
		    PrintWriter writer = new PrintWriter(fileName+".txt", "UTF-8");
		    writer.println(contents);
		    writer.close();
		} catch (IOException e) {
		   System.out.println("Writing to "+fileName+" failed.");
		}
	}

	public static boolean nextChar(int interval) {
		if(currImage != null) return currImage.nextChar(interval);
		return false;
	}
	
	static void changeFont(int change){
		
		currFontIndex = (currFontIndex+change)%availableFonts.size();
		if(currFontIndex < 0 ) currFontIndex = availableFonts.size()+change;
		currFont = availableFonts.get(currFontIndex);
	}
	
	public static void changeFontSize(int change) {
		
		Font newFont = currFont.font.deriveFont((float) currFont.font.getSize() + change);
		AsciiFont newAsciiFont = new AsciiFont(newFont);
		if(newAsciiFont.charWidth > 0) {
			currFont = newAsciiFont;
			availableFonts.set(currFontIndex, currFont);
		}
		
		if(currImage != null) currImage = new StringifyImage(currImage.image, currFont);
	
		
	}

	@Override
	void renderGame(Graphics2D g) {
		if(currImage != null) {
			if(renderImage) renderOrigonal(g);
			if(renderAscii) renderAsciiOverlay(g);
		}
		
		if(debug) {
			renderAvailableFonts(g);
			renderFullGrayScaleChars(g);
		}
		
	}
	
	private void renderOrigonal(Graphics2D g) {
		
		int drawWidth = (int) (currImage.image.getWidth() * windowScale);
		int drawHeight = (int) (currImage.image.getHeight() * windowScale);
		g.drawImage(currImage.image, margin, margin, drawWidth, drawHeight, null);
		
	}
	
	private void renderAsciiOverlay(Graphics2D g) {
		g.setColor(Color.white);
		Font drawFont = currFont.font.deriveFont(currFont.font.getSize() * windowScale);
		g.setFont(drawFont);
		int intY = 2*margin;
		int intX = margin;
		
		for(String l : overlayString.split("\n")) {
			g.drawString(l, intX, intY);
			intY+=drawFont.getSize();
		}
		
	}

	void renderFullGrayScaleChars(Graphics g){
		g.setColor(Color.white);
		g.setFont(currFont.font);
		int intY = 2*margin;
		int intX = windowWidth/2;
		for(char c : grayScaleChars) {
			
			if(intX > windowWidth-margin) {
				intY += currFont.charHeight;
				intX = windowWidth/2;
			}
		
			g.drawString(c+"", intX, intY);
			intX += currFont.charWidth;
		}
	}
	
	void renderAvailableFonts(Graphics g) {
		int intY = 2*margin;
		int intX = 2*margin;
		
		int i=0;
		g.setColor(Color.white);
		for(AsciiFont f : availableFonts) {
			
			if(intY > windowHeight-margin) {
				intY = 2*margin;
				intX += windowWidth/2;
			}
			if(i == currFontIndex) {
				g.fillOval(intX-margin, intY-(f.charHeight+margin)/2, margin/2, margin);
			}
			g.setFont(f.font);
			g.drawString(f.font.getFontName(), intX, intY);
			intY+=f.charHeight+margin;
			i++;
		}
	}

}
