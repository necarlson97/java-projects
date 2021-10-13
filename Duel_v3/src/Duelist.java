import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class Duelist implements Runnable {
	
	Random r = new Random();

	Thread duelistThread;
	
	public int x;
	public int y;
	
	public int width;
	public int height;
	
	int gunBarrelX;
	int gunBarrelY;
	
	public int torsoX;
	public int torsoY;
	
	public boolean alive = true;
	public boolean drawingGun;
	public boolean gunDrawn;
	public int drawCounter;
	
	public Color duelistColor;
	
	public int killCount;
	
	BufferedImage restSprite;
	BufferedImage drawSprite1;
	BufferedImage drawSprite2;
	BufferedImage deadSprite;
	
	public Duelist(int x, int y, Color duelistColor, String spriteSheetString){
		this.x = x;
		this.y = y;
		
		this.duelistColor = duelistColor;
		getSpritesFromSheet(spriteSheetString);
		
		width = restSprite.getWidth() * 5;
		height = restSprite.getHeight() * 5;
		
		duelistThread = new Thread(this);
		duelistThread.start();
	}
	
	public void run(){
		
	}
	
	public void render(Graphics g, Color duelistColor){
		renderKillCount(g);
		if(alive){
			if(gunDrawn) g.drawImage(drawSprite2, x, y, width, height, null);
			else if(drawingGun) g.drawImage(drawSprite1, x, y, width, height, null);
			else g.drawImage(restSprite, x, y, width, height, null);
		}
		else g.drawImage(deadSprite, x, y, width, height, null);
	
	}
	
	public boolean looksThreatening() {
		return  drawingGun || gunDrawn;
	}
	
	public void getSpritesFromSheet(String sheetFileName){
		BufferedImage spriteSheet;
		try {
			spriteSheet = ImageIO.read(new File(sheetFileName));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		restSprite = spriteSheet.getSubimage(0, 0, 31, 31);
		drawSprite1 = spriteSheet.getSubimage(32, 0, 31, 31);
		drawSprite2 = spriteSheet.getSubimage(0, 32, 31, 31);
		deadSprite = spriteSheet.getSubimage(32, 32, 31, 31);
	}
	
	private void renderKillCount(Graphics g) {
		g.setFont(Duel.headerFont);
		
		g.setColor(Color.white);
		if(killCount < 0) g.setColor(Color.red);
		g.drawString(String.valueOf(killCount), x+60, y-50);
		
	}

}
