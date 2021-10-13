
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferInt;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Padge extends Canvas{
	public static final int WIDTH = 800, HEIGHT = 500;
	
	
	private Random r;

	
	public Padge(){
		while(true){
			//Gets User Input
			Scanner scan = new Scanner(System.in);
			System.out.println("\nEnter Poem. Use a space for a blank line. First line will be title.");
			
			String nextLine = " ";
			ArrayList lines = new ArrayList();
			while ( !nextLine.equals("") ){
				nextLine = scan.nextLine();
				lines.add(nextLine);
			}
			
			//Shows user their input
			System.out.println("Creating image for:");
			for(int i = 0; i<lines.size(); i++) System.out.println(lines.get(i));
			
//			//Confirmation
//			String response = "a";
//			while(!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")){
//				System.out.println("\nConfirm? y/n");
//				response = scan.nextLine();
//			}
//			//Output, render, and finish
//			if(response == "y"){
//				printToPadge(lines);
//				break;
//			}
			
			printToPadge(lines);
		}
	
	}
	
	private void printToPadge(ArrayList lines){
		System.out.println("Preparing Padge...");
		r = new Random();
		
		//Create Background COLOR RANDOMIZATION ISSUES
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		int[] nextColor = new int[3];
		for(int c=0; c<nextColor.length; c++) nextColor[c] = 200;
		for(int row = 0; row<WIDTH; row++){
			for(int col = 0; col<HEIGHT; col++){
				img.setRGB(row, col, pack(nextColor[0],nextColor[1],nextColor[2]));
				for(int c=0; c<nextColor.length; c++){
					nextColor[c]  += (-50+r.nextInt(100));
					//if(col > 1) c = (c + (( img.getRGB(row, col-1) >> 0) & 0xFF )) / 2;
					nextColor[c]  = (int) clamp(c, 100, 225);
				}
			}
		}
		
		//Blur Background
		float[] matrix = new float[400];
		for (int i = 0; i < matrix.length ; i++) matrix[i] = 0.111f;
		

		BufferedImage imgTemp = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	    BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix) );
		img = op.filter(img, imgTemp);
		
		//Set font
		Graphics g = img.getGraphics();
	    g.setFont(new Font("AmericanTypewriter", 24, 24));
	    g.setColor(new Color(10,10,10));
	    
	    for(int i = 0; i<lines.size(); i++){
	    	String[] letters = ((String) lines.get(i)).split("");
	    	int xMargin = 392 - (letters.length*8);
	    	for(int j = 0; j<letters.length; j++){
	    		int x = xMargin + j*16 +(-1+r.nextInt(3));
	    		int y = 200 + i*26 +(-1+r.nextInt(3));
	    		g.drawString(letters[j], x, y);
	    	}
	    }
	    String title = "test";
	    title+=".png";
	    writePoem(img, title);
	    g.dispose();
	    
	    System.out.println("Done!");
	    System.exit(1);
		
	}
	
	public void writePoem(BufferedImage img, String title){
		System.out.println("Writing...");
		try {
			ImageIO.write(img, "png", new File("test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int pack(int r, int g, int b) {
		   //clamp to range [0, 255]
		   if (r < 0)
		      r = 0;
		   else if (r > 255) r = 255;
		   if (g < 0)
		      g = 0;
		   else if (g > 255) g = 255;
		   if (b < 0)
		      b = 0;
		   else if (b > 255) b = 255;
		   
		   //pack it together
		   return (255 << 24) | (r << 16) | (g << 8) | (b);
		}
	
	public static float clamp(float var, float min, float max){
		if(var >= max) 
			return var = max;
		else if(var <= min) 
			return var = min;
		else 
			return var;
	}
	
	public static void main(String[] args){
		new Padge();
		
	}

}
