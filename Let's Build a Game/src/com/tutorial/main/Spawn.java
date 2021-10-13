package com.tutorial.main;

import java.util.Random;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int scoreKeep = 0;
	
	public Spawn(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
	}

	public void tick(){
		scoreKeep++;
		if(scoreKeep == 500){
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			
			if(hud.getLevel() == 2){
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
			}
			else if(hud.getLevel() == 3){
				for(int i = 0; i < 2; i++){
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
				}	
			}
			else if(hud.getLevel() == 4){
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler));
			}
			else if(hud.getLevel() == 5){
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler));
				handler.addObject(new SmartEnemy(Game.WIDTH/2,Game.HEIGHT/2, ID.SmartEnemy, handler));
			}
			else if(hud.getLevel() == 6){
				handler.clearEnemies();
				handler.addObject(new EnemyBoss((Game.WIDTH/2)-64, 0, ID.EnemyBoss, handler));
			}
			
		}
	}
}
