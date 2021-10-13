import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class StringifyImage {
	
	static String grayScaleString = "$$$$$$$$$$@@@@@@BBBB%%%8&&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"\"^^^^``````''''''''...........                    ";
	static char[] grayScaleChars = grayScaleString.toCharArray();
	
	BufferedImage image;
	AsciiFont asciiFont;
	
	double[][] grayValues;
	double grayMin = 1;
	double grayMax = 0;
	
	String imageString = "";
	
	int nextGrayCol = 0;
	int nextGrayRow = 0;
	
	double imageScale = 1;
	int margin = ImageToAscii.margin;
	
	static int interval = 1;
	
	boolean invert = true;
	
	boolean hasNext = true;
	
	public StringifyImage(BufferedImage image){
		this.image = image;
		
		fillGrayValues();
		
		this.asciiFont = new AsciiFont((int) imageScale);
	}
	
	public StringifyImage(BufferedImage image, AsciiFont oldFont){
		this.image = image;
		this.asciiFont = oldFont;
		
		fillGrayValues();
	}
	
	void resetString(){
		nextGrayCol = 0;
		nextGrayRow = 0;
		imageString = "";
		hasNext = true;
	}
	
	private void fillGrayValues(){
		float imageRatio = (float) image.getWidth() / image.getHeight();
		int cols = 100;
		int rows = (int) (100 * imageRatio);
		int sampleWidth = image.getWidth()/cols;
		int sampleHeight = image.getHeight()/rows;
		
		grayValues = new double[cols][rows];
		
		for(int c=0; c<cols; c++) {
			for(int r=0; r<rows; r++) {
				
				double sample = sampleValues(c, r, sampleWidth, sampleHeight);
				if(sample < grayMin) grayMin = sample;
				if(sample > grayMax) grayMax = sample;
				grayValues[c][r] = sample;
			}
		}	
		
		imageScale = rows/image.getHeight();
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
	
	void changeFontSize(int change){
		if(asciiFont.font.getSize() + change < 1) return;
		asciiFont = asciiFont.changeSize(change);
		ImageToAscii.currImage = new StringifyImage(image, asciiFont);	
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
	
	public int getColCount(){
		return grayValues.length;
	}
	
	public int getRowCount(){
		return grayValues[0].length;
	}
	
	public void render(Graphics2D g, boolean showImage, boolean showAscii){
		if(showImage) renderOrigonal(g);
		if(showAscii) renderAscii(g);
	}
	
	public void renderOrigonal(Graphics g){
		int drawWidth = (int) (image.getWidth());
		int drawHeight = (int) (image.getHeight());
		g.drawImage(image, ImageToAscii.windowWidth-drawWidth-margin, margin, drawWidth, drawHeight, null);
	}
	
	private void renderAscii(Graphics2D g) {
		g.setColor(Color.white);
		Font drawFont = asciiFont.font.deriveFont((float) (asciiFont.font.getSize() * imageScale));
		g.setFont(drawFont);
		int intY = 2*margin;
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
	

}
