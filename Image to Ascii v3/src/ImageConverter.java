import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImageConverter {
	
	int margin = ImageToAscii.margin;
	
	static String grayScaleString = "$$$$$$$$$$@@@@@@BBBB%%%8&&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"\"^^^^``````''''''''...........                    ";
	static char[] grayScaleChars = grayScaleString.toCharArray();
	
	Font headerFont;
	
	String fileName;
	BufferedImage image;
	Font font;
	
	double[][] grayValues;
	double grayMin = 1;
	double grayMax = 0;
	
	String imageString = "";
	
	int nextGrayCol = 0;
	int nextGrayRow = 0;
	boolean invert = false;
	
	static int interval = 100;
	boolean hasNext = true;
	
	int sampleHeight;
	int sampleWidth;
	
	int desiredRows = 50;
	int scaleMult;
	float drawScale;
	
	
	// For totally new, first image
	public ImageConverter(String fileName){
		this.fileName = fileName;
		image = loadImage(fileName);
		sampleHeight = image.getHeight()/desiredRows;
		font = new Font("Monospaced", Font.PLAIN, sampleHeight);
		
		setup();
	}
	
	// For the same image, with a new font
	public ImageConverter(String fileName, BufferedImage givenImage, Font givenFont){
		this.fileName = fileName;
		image = givenImage;
		sampleHeight = givenFont.getSize();
		font = givenFont;
		
		setup();
	}
	
	// For a new image image, using previous image's font
	public ImageConverter(String fileName, Font givenFont){
		this.fileName = fileName;
		image = loadImage(fileName);
		sampleHeight = givenFont.getSize();
		font = givenFont;
		
		setup();
	}
	
	public void setup(){
		
		sampleWidth = getFontWidth(font);
		scaleMult = (Game.windowHeight-4*margin)/desiredRows;
		drawScale = ((float)desiredRows / image.getHeight()) * scaleMult; 
		
		if(image.getWidth()*drawScale > (Game.windowWidth-2*margin)) {
			int cols = image.getWidth()/sampleWidth;
			scaleMult = (Game.windowWidth-2*margin)/cols;
			drawScale = ((float)cols / image.getWidth()) * scaleMult; 
		}
		
		headerFont = font.deriveFont( (float)(Game.windowWidth*.015) );
		
		
		
		fillGrayValues();
		
//		System.out.println("Sample height: "+sampleHeight+", Draw scale: "+drawScale);
//		System.out.println("Draw height: "+image.getHeight()*drawScale);
	}

	private BufferedImage loadImage(String imageName) {
		try {
			BufferedImage newImage = ImageIO.read(new File(imageName+".png"));
			
			if(newImage.getWidth() > Game.windowWidth-2*margin) newImage = resize(newImage, 'w');
			if(newImage.getHeight() > Game.windowHeight-2*margin) newImage = resize(newImage, 'h');
			
		    return newImage;
		} catch (IOException e) {
			System.out.println("Could not load "+imageName);
			return null;
		}
	}
	
	private BufferedImage resize(BufferedImage toResize, char bigDimension) {
		float imageRatio = (float) toResize.getWidth() / toResize.getHeight();
		int newWidth;
		int newHeight;
		
		if(bigDimension == 'w') {
			newWidth = (int) (Game.windowWidth-2*margin * imageRatio);
			newHeight = (int) (newWidth * imageRatio);
		}
		
		else if(bigDimension == 'h') {
			newHeight = (int) (Game.windowHeight-2*margin * imageRatio);
			newWidth = (int) (newHeight * imageRatio);
		}
		
		else {
			System.out.println("Unknown dimension: "+bigDimension);
			return null;
		}
		
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resized.createGraphics();
        
        g.drawImage(toResize, 0, 0, newWidth, newHeight, null); 
        g.dispose();
        
        writeResizedImage(resized);
		
		return resized;
	}

	
	private void writeResizedImage(BufferedImage resized) {
		
		try {
		    File outputfile = new File(fileName+".png");
		    ImageIO.write(resized, "png", outputfile);
		} catch (IOException e) {
		   System.out.println("Could not save resized image "+fileName);
		}
		
	}

	public int getFontWidth(Font font) {
		FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, 
				RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
		
		return (int) Math.round(font.getStringBounds("m", frc).getWidth());
	}
	
	void resetString(){
		nextGrayCol = 0;
		nextGrayRow = 0;
		imageString = "";
		hasNext = true;
	}
	
	private void fillGrayValues(){
		int cols = image.getWidth()/sampleWidth;
		int rows = image.getHeight()/sampleHeight;
		
		grayValues = new double[cols][rows];
		
		for(int c=0; c<cols; c++) {
			for(int r=0; r<rows; r++) {
				
				double sample = sampleValues(c, r, sampleWidth, sampleHeight);
				if(sample < grayMin) grayMin = sample;
				if(sample > grayMax) grayMax = sample;
				grayValues[c][r] = sample;
			}
		}	
		
	}
	
	private double sampleValues(int col, int row, int width, int height){
		int x = col*width;
		int y = row*height;
		
		double sample[][] = new double[width][height];
		double prev = 0;
		
		for(int xAdd=0; xAdd<width; xAdd++){
			for(int yAdd=0; yAdd<height; yAdd++){
				if(x+xAdd < image.getWidth() && y+yAdd < image.getHeight()) {
					int rgb = image.getRGB(x+xAdd, y+yAdd);
			        int red = (rgb >> 16) & 0xFF;
			        int grn = (rgb >> 8) & 0xFF;
			        int blu = (rgb & 0xFF);
			        
			        double grayLevel = (red + grn + blu) / 3;
			        prev = grayLevel/255;
			        sample[xAdd][yAdd] = 1-(grayLevel/255);
				}
				else 
					sample[xAdd][yAdd] = prev;
				
			}
		}
		
		return average2DArray(sample);
		
	}
	
	public void run(){
		if(hasNext)
			hasNext = nextChar(interval);
	}
	
	public boolean nextChar(int interval){
		boolean hasNext = true;
		for(int i=0; i<interval; i++){
			if(hasNext) hasNext = nextChar();
		}
		return hasNext;
	}
	
	public boolean nextChar(){
		if(nextGrayRow >= grayValues[0].length) return false;
		double currGray = grayValues[nextGrayCol][nextGrayRow];
		int charIndex = (int) map(currGray, grayMin, grayMax, 0,  grayScaleChars.length-1);
		
		if(invert) charIndex = grayScaleChars.length - 1 - charIndex;
		
		imageString+= grayScaleChars[charIndex];
		
		nextGrayCol++;
		if(nextGrayCol >= grayValues.length){
			nextGrayCol = 0;
			nextGrayRow++;
			imageString+="\n";
		}
		return true;
	}
	
	private double average2DArray(double[][] array) {
        return Arrays.stream(array).flatMapToDouble(Arrays::stream).average().getAsDouble();
	}
	
	static double map(double x, double in_min, double in_max, double out_min, double out_max) {
	  double mappedValue = (x - in_min) / (in_max - in_min) * (out_max - out_min) + out_min;
	  
	  if(mappedValue > out_max) {
		  System.out.println("Maxed out "+x+" over "+in_max+" to "+mappedValue+" over "+out_max);
		  mappedValue = out_max;
	  }
	  if(mappedValue < out_min) {
		  System.out.println("Minned out "+x+" under "+in_min+" to "+mappedValue+" under "+out_min);
		  mappedValue = out_min;
	  }
	  return mappedValue;
	}
	
	public void render(Graphics2D g, boolean showImage, boolean showAscii, boolean showHUD){
		if(showImage) renderOrigonal(g);
		if(showAscii) renderAscii(g);
		if(showHUD) renderHUD(g);
	
	}
	
	private void renderHUD(Graphics2D g) {
		
		int hudLeftCornerX = (int) (Game.windowWidth*.2);
		int hudLeftCornerY = (int) (Game.windowHeight*.7);
		int hudWidth = (int) (Game.windowWidth*.6);
		int hudHeight = (int) (Game.windowWidth*.12);
		
		g.setColor(Color.black);
		g.fillRect(hudLeftCornerX, hudLeftCornerY, hudWidth, hudHeight);
		
		g.setColor(Color.white);
		g.drawRect(hudLeftCornerX, hudLeftCornerY, hudWidth, hudHeight);
		
		g.setFont(headerFont);
		
		int intX = hudLeftCornerX + margin;
		int intY = hudLeftCornerY + margin + headerFont.getSize();
		g.drawString(fileName, intX, intY);
		intY += 2*headerFont.getSize();
		g.drawString("press: < or >  (letters per second: "+interval*Game.FPS+")", intX, intY);
		intY += headerFont.getSize();
		g.drawString("               (text size: "+font.getSize()+")", intX, intY);
		intY += headerFont.getSize();
		g.drawString("press: u       (inverted: "+invert+")", intX, intY);
		intY += headerFont.getSize();
		g.drawString("               (finished: "+!hasNext+")", intX, intY);
		intY += headerFont.getSize();
		g.drawString("font name: "+font.getFontName(), intX, intY);
		
	}

	public void renderOrigonal(Graphics g){
		int drawWidth = (int) (image.getWidth() * drawScale);
		int drawHeight = (int) (image.getHeight() * drawScale);
		
		g.drawImage(image, margin, margin, drawWidth, drawHeight, null);
	}
	
	private void renderAscii(Graphics2D g) {
		g.setColor(Color.white);
		Font drawFont = font.deriveFont(font.getSize() * drawScale);
		g.setFont(drawFont);
		int intY = 2*margin+drawFont.getSize();
		int intX = margin;
		
		for(String l : imageString.split("\n")) {
			g.drawString(l, intX, intY);
			intY+=drawFont.getSize();
		}
		
	}

	public void invert() {
		invert = !invert;
		resetString();
	}
	
	void changeFontSize(int change){
		if(font.getSize() + change < 1 || font.getSize() + change > image.getHeight()) return;
		font = font.deriveFont((float) font.getSize() + change); 
		ImageToAscii.currConverter = new ImageConverter(fileName, image, font);	
	}

	public void changeFont(String newFontName) {
		Font newFont = new Font(newFontName, Font.PLAIN, font.getSize());
		ImageToAscii.currConverter = new ImageConverter(fileName, image, newFont);	
	}

}
