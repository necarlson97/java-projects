import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class FileVisual extends ManedgerVisual{
	
	private float x;
	private float y;
	
	private int width = 2;
	private int height = 50;
	
	private String extension;
	
	private Color typeColor;
	
	public FileVisual(File file, float x, float y){
		super(file);
		this.x = x;
		this.y = y;
		name = file.getName();
		hidden = (name.startsWith("."));
		if(name.length()>3) extension = name.substring(name.length()-3);
		else extension = "";
		typeColor = new Color(nameColor.getRGB()/4);
	}

	public void render(Graphics g) {
		if(!hidden) {
			int intX = (int)x;
			int intY = (int)y;
			
			g.setColor(nameColor);
			g.drawRect(intX, intX, width, height);
			
			g.setColor(typeColor);
			g.fillRect(intX, intX, width, height);
			
			g.setColor(Color.white);
			g.setFont(bodyFont);
			
			g.drawString(name, intX, intY);
			g.drawString(String.valueOf(file.getFreeSpace()), intX, intY+10);
			g.drawString(extension, intX, intY+20);
		}	
	}
	
	public String toString(){
		return name;
	}

}
