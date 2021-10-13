import java.awt.Color;
import java.awt.Graphics;

public class Ext extends GameObject { // Extinguisher, but I hated writing that word over and over

	Color fullColor = Color.red;
	Color spentColor = new Color(128, 0, 0);
	
	float spent = 0;
	
	int headWidth = margin/2;
	int headHeight = margin /2;
	
	public Ext(Module m, int i) {
		super(m.x+m.width/2, m.y, margin, margin*2, i);
	}
	
	public void run() {
		super.run();
		if(module != null && holder == null){
			if(module.onFire > .9 && r.nextInt(10) * module.onFire > 8) 
				explode();
		}
	}

	@Override
	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y-height;
		
		if(holder != null) {
			if(!holder.down) {
				intY -= holder.height/4;
				if (pointLeft) intX -= holder.width/2;
				else intX += holder.width/2;
			}
			else intY -= holder.width/4;
		}
			
		renderExt(intX, intY, g);
	} 
	
	private void renderExt(int drawX, int drawY, Graphics g) {
		g.setColor(fullColor);
		g.fillRect(drawX-width/2, drawY, width, height);
		g.setColor(spentColor);
		g.fillRect(drawX-width/2, drawY, width, (int) (height*spent));
		g.setColor(Color.BLACK);
		g.drawRect(drawX-width/2, drawY, width, height);
		if(pointLeft) g.fillRect(drawX-width, drawY, headWidth, headHeight);
		else g.fillRect(drawX+width/2, drawY, headWidth, headHeight);
	}
	
	public void use() {
		if(spent >= 1) return;
		spent += .1;
		holder.moduleIn().extinguish();
	}
	
	public void explode() {
		for(Module m : module.room.modules) {
			if(spent < 1) m.extinguish();
			spent += .1;
		}
		spent = 1;
	}

}
