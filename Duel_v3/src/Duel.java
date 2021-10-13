import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Duel extends Canvas implements Runnable{
	public static final int WIDTH = 1920, HEIGHT = 1080;
	private static Random r = new Random();
	
	private static List<Particle> foregroundParticles = new ArrayList<Particle>();
	private static List<Particle> backgroundParticles = new ArrayList<Particle>();
	private static List<Particle> midgroundParticles = new ArrayList<Particle>();
	private List<Cloud> clouds = new ArrayList<Cloud>();
	static List<TrainCar> trainCars = new ArrayList<TrainCar>();
	static List<BackgroundObject> backgroundObjects = new ArrayList<BackgroundObject>();
	static List<BackgroundObject> midgroundObjects = new ArrayList<BackgroundObject>();
	
	static int horizonY = 400;
	
	static Player player;
	static Enemy enemy;
	
	public static Duelist shooter = null;
	public static Duelist victim = null;
	
	public static int helpCount;
	static Font headerFont = new Font("press start 2p", 1, 30);
	private Font bodyFont = new Font("press start 2p", 1, 20);
	
	Thread duelThread;
	
	public static void main(String[] args){
		new Duel();
	}
	
	public Duel(){
		this.addKeyListener(new KeyInput());
		new Window(WIDTH, HEIGHT, "Duel", this);
		
		player = new Player(100, 300);
		Color randEnemyColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
		enemy = new RedWoman(1000, 300);
		createBackgroundObjects();
		
		duelThread = new Thread(this);
		duelThread.start();
	}

	@Override
	public void run() {
		while(true){
			runParticles();
			runBackground();
			
			player.run();
			enemy.run();
			
			shoot();
			
			render();
			
			try {
				duelThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			helpCount++;
		}
	}
	
	public void runParticles(){
		if(foregroundParticles.size() > 200) foregroundParticles.remove(0);
		for(int i=0; i<foregroundParticles.size(); i++){
			foregroundParticles.get(i).run();
		}
		if(midgroundParticles.size() > 200) midgroundParticles.remove(0);
		for(int i=0; i<midgroundParticles.size(); i++){
			midgroundParticles.get(i).run();
		}
		if(backgroundParticles.size() > 400) backgroundParticles.remove(0);
		for(int i=0; i<backgroundParticles.size(); i++){
			backgroundParticles.get(i).run();
		}
	}
	
	public void runBackground(){
		for(int i=0; i<midgroundObjects.size(); i++){
			BackgroundObject mgo = midgroundObjects.get(i);
			if(mgo.x <-100) {
				midgroundObjects.remove(i);
				midgroundObjects.add(new Cactus());
			}
			mgo.run();
		}
		for(int i=0; i<backgroundObjects.size(); i++){
			BackgroundObject bgo = backgroundObjects.get(i);
			if(bgo.x <-100) {
				backgroundObjects.remove(i);
				backgroundObjects.add(new Mesa());
			}
			bgo.run();
		}
		for(int i=0; i<clouds.size(); i++){
			Cloud cloud = clouds.get(i);
			if(cloud.x>Duel.WIDTH+500){
				clouds.remove(i);
				clouds.add(new Cloud());
			}
			cloud.run();
		}
		for(int i=0; i<trainCars.size(); i++){
			trainCars.get(i).run();
		}
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		renderBackground(g);
		for(BackgroundObject bgo : backgroundObjects){
			bgo.render(g);
		}
		for(BackgroundObject mgo : midgroundObjects){
			mgo.render(g);
		}
		for(int i=0; i<backgroundParticles.size(); i++){
			backgroundParticles.get(i).render(g);
		}
		for(TrainCar trainCar : trainCars){
			trainCar.render(g);
		}
		for(int i=0; i<midgroundParticles.size(); i++){
			midgroundParticles.get(i).render(g);
		}
		player.render(g);
		enemy.render(g);
		renderHelpText(g);
		for(int i=0; i<foregroundParticles.size(); i++){
			foregroundParticles.get(i).render(g);
		}
		
		
		g.dispose();
		bs.show();
	}
	
	private void renderBackground(Graphics g){
		
		g.setColor(new Color(180, 200, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(Cloud cloud : clouds){
			cloud.render(g);
		}
		
		int intX = 0;
		int intY = horizonY+300;
		int width;
		int height;		
		g.setColor(new Color(255, 230, 200));
		g.fillRect(0, horizonY, WIDTH, HEIGHT-horizonY);
		g.setColor(new Color(250, 210, 210));
		while(intY < HEIGHT){
			width = (r.nextInt(10)+1)*10;
			height = (r.nextInt(5)+1)*10;
			g.fillRect(intX, intY, width, height);
			if(intX < WIDTH) intX += width + r.nextInt(5) * 10;
			else {
				intX = 0;
				intY += r.nextInt(5) * 10;
			}
		}
		
	}

	private void renderHelpText(Graphics g) {	
		
		g.setColor(Color.white);
		
		if(helpCount < 500) {
			g.setFont(headerFont);
			g.drawString("Help", 50, 50);
			
			g.setFont(bodyFont);
			g.drawString("", 50, 100);
			g.drawString("D to draw. Anything else to shoot.", 50, 150);
			
		}
		else {
			g.setFont(bodyFont);
			g.drawString("Press f1 for help.", 50, 50);
		}
		
	}

	public void shoot(){
		
		if(shooter !=null) createShootParticles(shooter);
		if(victim != null) {
			victim.alive = false;
			createDeathParticles(victim);
			if(victim.gunDrawn) shooter.killCount ++;
			else if(victim.drawingGun) shooter.killCount += 0;
			else shooter.killCount --;
		}
	
		shooter = null;
		victim = null;
	}
	
	public void createShootParticles(Duelist shooter) {
		for(int i=50; i<60; i++){ // Gunsmoke
			float rXVel = r.nextFloat() - (float) .5;
			float rYVel = r.nextFloat()*3 - (float) 4.6;
			
			Color smokeColor = Color.white;
			if(rYVel <= -4) smokeColor = Color.gray;
			else if(rYVel <= -2) smokeColor = Color.darkGray;
			
			foregroundParticles.add( new GunSmoke(rXVel, rYVel, shooter, smokeColor) );
		}
		for(int i=50; i<60; i++){ // Muzzleflash
			float rXVel = r.nextFloat()+2;
			float rYVel = r.nextFloat() - (float) .5;
			
			Color flashColor = Color.yellow;
			if(rXVel < 2.6) flashColor = Color.orange;
			else if(rXVel < 2.8) flashColor = Color.red;
		
			foregroundParticles.add( new MuzzleFlash(rXVel, rYVel, shooter, flashColor) );
		}
		foregroundParticles.add( new BulletTail(shooter) );
		
	}
	
	public static void createSteam(int x, int y) {
		float smokeColorFloat = (float) ((.2) * r.nextFloat() + .8);
		float rXVel = r.nextFloat()-4;
		float rYVel = (smokeColorFloat-2) *5;
		
		Color smokeColor = (new Color(smokeColorFloat, smokeColorFloat, smokeColorFloat));
		backgroundParticles.add( new GunSmoke(rXVel, rYVel, x, y, smokeColor) );
	}
	
	public void createDeathParticles(Duelist victim){
		
		for(int i=0; i<40; i++){ // Blood
			float rXVel = r.nextFloat()*5 - 2;
			float rYVel = r.nextFloat()*5 - 5;
			midgroundParticles.add( new BloodDrop(rXVel, rYVel, victim) );
		}
		for(int i=40; i<50; i++){ // Duelist bits
			float rXVel = r.nextFloat()*5 - 2;
			float rYVel = r.nextFloat()*5 - 5;
			midgroundParticles.add( new DuelistBit(rXVel, rYVel, victim) );
		}
		if(victim.gunDrawn) midgroundParticles.add( new DroppedGun(victim) );

	}

	private void createBackgroundObjects() {
		int intX = 0;
		while(intX < WIDTH){
			backgroundObjects.add(new Mesa(intX));
			midgroundObjects.add(new Cactus(intX));
			intX += WIDTH/3;
		}
		while(intX > 0){
			intX -= WIDTH/6;
			clouds.add(new Cloud(intX));
		}
		trainCars.add(new TrainCar(-5, 224, 20));
		trainCars.add(new TrainCar(950, 224, 0));
		
	}
	
	
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
