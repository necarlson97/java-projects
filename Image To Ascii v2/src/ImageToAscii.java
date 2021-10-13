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
	
	static int margin;
	
	static StringifyImage currImage;
	static String[] imageNames = {};
	static int imageIndex = 0;
	
	static String[] fontNames;
	
	static boolean showImage = true;
	static boolean showAscii = true;
	
	public static void main(String[] args){
		new ImageToAscii();
	}
	
	public ImageToAscii(){
		
	}
	
	@Override
	void setup() {
		margin = windowWidth / 100;
		
		fontNames = new FontLoader().getAvailableFontNames();
		imageNames = loadImageNames();
		
		loadImage(imageNames[imageIndex]);	
	}

	private String[] loadImageNames() {
		File[] files = new File(".").listFiles();
		LinkedList<String> fileNames = new LinkedList<String>();
		
		int i=0;
		for(File f : files){
			String fName = f.getName();
			if(fName.contains(".png")) fileNames.add(fName.substring(0, fName.length()-4));
			i++;
		}
		
		System.out.println(fileNames);
		return fileNames.toArray(new String[fileNames.size()]);
	}

	private static void loadImage(String imageName) {
		try {
		    currImage = new StringifyImage(ImageIO.read(new File(imageName+".png")));
		} catch (IOException e) {
			System.out.println("Could not load "+imageName);
			return;
		}
	}

	@Override
	void runGame() {
		if(currImage != null) currImage.run();
	}
	
	static void output() {
		String fileName = imageNames[imageIndex];
		String contents = currImage.imageString;
		
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

	@Override
	void renderGame(Graphics2D g) {
		if(currImage != null) currImage.render(g, showImage, showAscii);
	
		
	}

	public static void changeFont(int change) {
		if(currImage != null) currImage.asciiFont.changeFont(change);
		
	}

	public static void changeFontSize(int change) {
		if(currImage != null) currImage.changeFontSize(change);
	}

	public static void changeInterval(int change) {
		if(currImage != null) currImage.interval += change;
	}

	public static void changeImage(int change) {
		imageIndex = (imageIndex+change)%imageNames.length;
		if(imageIndex < 0 ) imageIndex = imageNames.length+change;
		loadImage(imageNames[imageIndex]);
	}
	
	public static void invert(){
		currImage.invert();
	}

}
