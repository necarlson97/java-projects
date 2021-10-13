import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Duel extends Canvas implements Runnable{
	public static final int WIDTH = 1440, HEIGHT = 900;
	private Random r = new Random();
	
	private List<Particle> particles;
	
	static Player player;
	static Enemy enemy;
	
	public static Duelist shooter = null;
	public static Duelist victim = null;
	
	public static int helpCount;
	private Font headerFont = new Font("press start 2p", 1, 30);
	private Font bodyFont = new Font("press start 2p", 1, 20);
	
	Thread duelThread;
	
	public static void main(String[] args){
		new Duel();
	}
	
	public Duel(){
		this.addKeyListener(new KeyInput());
		new Window(WIDTH, HEIGHT, "Duel", this);
		
		particles = new ArrayList<Particle>();
		player = new Player(100, 440);
		Color randEnemyColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
		enemy = new Enemy(1000, 440, randEnemyColor);
		
		duelThread = new Thread(this);
		duelThread.start();
	}

	@Override
	public void run() {
		while(true){
			if(particles.size() > 500) particles.remove(0);
			for(int i=0; i<particles.size(); i++){
				particles.get(i).run();
			}
			player.run();
			enemy.run();
			
			if(shooter != null && victim != null) shoot();
			
			render();
			
			try {
				duelThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			helpCount++;
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.darkGray);
		g.fillRect(0, HEIGHT-400, WIDTH, HEIGHT+400);
		
		renderHelpText(g);
		renderKillCount(g);
		for(int i=0; i<particles.size(); i++){
			particles.get(i).render(g);
		}
		player.render(g);
		enemy.render(g);
		
		g.dispose();
		bs.show();
	}

	private void renderKillCount(Graphics g) {
		g.setFont(headerFont);
		
		g.setColor(Color.white);
		if(player.killCount < 0) g.setColor(Color.red);
		g.drawString(String.valueOf(player.killCount), player.x, player.y-50);
		
		g.setColor(Color.white);
		if(enemy.killCount < 0) g.setColor(new Color(100, 0, 0));
		g.drawString(String.valueOf(enemy.killCount), enemy.x, enemy.y-50);
	}

	private void renderHelpText(Graphics g) {	
		
		g.setColor(Color.white);
		
		if(helpCount < 500) {
			g.setFont(headerFont);
			g.drawString("Help", 50, 50);
			
			g.setFont(bodyFont);
			g.drawString("Don't shoot unarmed men. And don't die.", 50, 100);
			g.drawString("D to draw. Anything else to shoot.", 50, 150);
			
		}
		else {
			g.setFont(bodyFont);
			g.drawString("Press f1 for help.", 50, 50);
		}
		
	}

	public void shoot(){
		victim.alive = false;
		
		createKillParticles(shooter, victim); // Also counts the kill within this method
	
		shooter = null;
		victim = null;
	}
	
	public void createKillParticles(Duelist shooter, Duelist victim){
		
		
		for(int i=0; i<40; i++){ // Blood
			float rXVel = r.nextFloat()*5 - 2;
			float rYVel = r.nextFloat()*5 - 5;
			particles.add( new BloodDrop(victim.x, victim.y, rXVel, rYVel) );
		}
		for(int i=40; i<50; i++){ // Duelist bits
			float rXVel = r.nextFloat()*5 - 2;
			float rYVel = r.nextFloat()*5 - 5;
			particles.add( new DuelistBit(victim.x, victim.y, rXVel, rYVel, victim.duelistColor) );
		}
		for(int i=50; i<60; i++){ // Gunsmoke
			float rXVel = r.nextFloat() - (float) .5;
			float rYVel = r.nextFloat()*3 - (float) 4.6;
			Color smokeColor = Color.white;
			if(rYVel <= -4) smokeColor = Color.gray;
			else if(rYVel <= -2) smokeColor = Color.darkGray;
			if(shooter == player) particles.add( new GunSmoke(shooter.x+55, shooter.y+30, rXVel, rYVel, smokeColor) );
			else particles.add( new GunSmoke(shooter.x-25, shooter.y+30, rXVel, rYVel, smokeColor) );
		}
		for(int i=50; i<60; i++){ // Muzzleflash
			float rXVel = r.nextFloat()+2;
			float rYVel = r.nextFloat() - (float) .5;
			Color flashColor = Color.yellow;
			if(rXVel < 2.6) flashColor = Color.orange;
			else if(rXVel < 2.8) flashColor = Color.red;
			if(shooter == player) particles.add( new MuzzleFlash(shooter.x+55, shooter.y+30, rXVel, rYVel, flashColor) );
			else particles.add( new MuzzleFlash(shooter.x-25, shooter.y+30, rXVel*-1, rYVel, flashColor) );
		}
		
		if(victim.gun) { // The severed arm
			if(victim == enemy) {
				float rXVel = r.nextFloat();
				float rYVel = r.nextFloat();
				particles.add( new DroppedEnemyGun(victim.x, victim.y, rXVel, rYVel, victim.duelistColor) );
			}
			if(victim == player) {
				float rXVel = r.nextFloat()*2;
				float rYVel = r.nextFloat()*2 - 2;
				particles.add( new DroppedPlayerGun(victim.x, victim.y, rXVel, rYVel) );
			}
			shooter.killCount ++;
		}
		else shooter.killCount --;
	}

	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
}
