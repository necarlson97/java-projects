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
	
	static String[] fontNames;
	static int fontIndex = 0;
	
	static ImageConverter currConverter;
	static String[] imageNames = {};
	static int imageIndex = 0;
	
	static boolean showImage = false;
	static boolean showAscii = true;
	static boolean showHUD = true;
	
	public static void main(String[] args){
		new ImageToAscii();
	}
	
	public ImageToAscii(){
		System.out.println("Insantiated");
	}
	
	@Override
	void setup() {
		System.out.println("Setup started");
		margin = windowWidth / 100;
		
//		FontLoader fontLoader = new FontLoader();
//		fontNames = fontLoader.getAvailableFontNames();
		fontNames = new String[] {"Monospaced"};
		System.out.println("Got fonts");

		imageNames = loadImageNames();
		System.out.println(Arrays.toString(imageNames));
		currConverter = new ImageConverter(imageNames[imageIndex]);
		System.out.println("Setup Finished");
	}

	private String[] loadImageNames() {
		File[] files = new File(".").listFiles();
		LinkedList<String> fileNames = new LinkedList<String>();
		
		for(File f : files){
			String fName = f.getName();
			if(fName.contains(".png")) fileNames.add(fName.substring(0, fName.length()-4));
		}
		return fileNames.toArray(new String[fileNames.size()]);
	}

	

	@Override
	void runGame() {
		if(currConverter != null) currConverter.run();
	}
	
	void handleScreenResize(){
		currConverter = new ImageConverter(currConverter.fileName, currConverter.image, currConverter.font);
	}
	
	static void output() {
		String fileName = imageNames[imageIndex];
		String contents = currConverter.imageString;
		
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
		if(currConverter != null) currConverter.render(g, showImage, showAscii, showHUD);	
	}

	public static void changeFontSize(int change) {
		if(currConverter != null) currConverter.changeFontSize(change);
	}

	public static void changeInterval(int change) {
		if(currConverter != null) currConverter.interval += change;
	}

	public static void changeImage(int change) {
		imageIndex = (imageIndex+change)%imageNames.length;
		if(imageIndex < 0 ) imageIndex = imageNames.length+change;
		currConverter = new ImageConverter(imageNames[imageIndex]);
	}
	
	public static void changeFont(int change) {
		fontIndex = (fontIndex+change)%fontNames.length;
		if(fontIndex < 0 ) fontIndex = fontNames.length+change;
		currConverter.changeFont(fontNames[fontIndex]);
	}
	
	public static void invert(){
		currConverter.invert();
	}
	

	

}
