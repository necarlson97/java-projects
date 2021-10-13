import java.awt.image.BufferedImage;
import java.util.Arrays;

public class StringifyImage {
	
	
	BufferedImage image;
	AsciiFont asciiFont;
	
	double[][] grayValues;
	double grayMin = 1;
	double grayMax = 0;
	
	String imageString = "";
	
	int nextGrayCol = 0;
	int nextGrayRow = 0;
	
	public StringifyImage(BufferedImage image, AsciiFont asciiFont){
		this.image = image;
		this.asciiFont = asciiFont;
		
		fillGrayValues();

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
		int charIndex = (int) map(currGray, grayMin, grayMax, 0, ImageToAscii.grayScaleChars.length-1);
		
		imageString+=ImageToAscii.grayScaleChars[charIndex];
		
		nextGrayCol++;
		if(nextGrayCol >= grayValues.length){
			nextGrayCol = 0;
			nextGrayRow++;
			imageString+="\n";
		}
		return true;
	}
	
	private void fillGrayValues(){
		int cols = image.getWidth()/asciiFont.charWidth;
		int rows = image.getHeight()/asciiFont.charHeight;
		
		grayValues = new double[cols][rows];
		
		for(int c=0; c<cols; c++) {
			for(int r=0; r<rows; r++) {
				
				double sample = sampleValues(c, r, asciiFont.charWidth, asciiFont.charHeight);
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
			        sample[xAdd][yAdd] = grayLevel/255;
				}
				else 
					sample[xAdd][yAdd] = prev;
				
			}
		}
		
		return average2DArray(sample);
		
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
	

}
