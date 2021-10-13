import java.awt.Color;
import java.awt.Graphics;

public class Extinguisher extends GameObject {
	
	float spent = 0;
	float spentSpeed = (float) .1;
	
	Color color = Color.red;
	Color spentColor = new Color(128, 0 ,0);

	int index;
	
	int maxFoam = 5;
	Foam[] foam;
	
	boolean left = false;
	
	Module module;

	public Extinguisher(Module startingModule, int i) {
		super(startingModule.x+startingModule.width/2, startingModule.y, 5, 10);
		index = i;
		foam = new Foam[maxFoam];
	}

	@Override
	public void run() {
		if(held) {
			left = BurningHouse.player.pointLeft;
			module = BurningHouse.player.module;
		}
		for(Foam f : foam)
			if(f != null) f.run();
		cleanParticles();
	}
	
	private void cleanParticles() {
		for(int i=0; i<foam.length; i++) {
			if(foam[i] != null && foam[i].life < 0) foam[i] = null;
		}
	}
	
	@Override
	public void use() {
		if(spent < 1) spent += spentSpeed;
		else {
			spent = 1;
			return;
		}
		
		for(int i=0; i<foam.length; i++) {
			foam[i] = new Foam((int)x, (int)y+5, left);
		}
		
		if(module != null) {
			module.extinguishFire();
		}
	}

	@Override
	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		if(held) {
			intY += height+5;
		}
		
		g.setColor(color);
		g.fillRect(intX+width/2, intY-height, width, height);
		
		int spentHeight = (int) (height*spent);
		g.setColor(spentColor);
		g.fillRect(intX+width/2, intY-spentHeight, width, spentHeight);
		
		g.setColor(Color.black);
		if(left) g.fillRect(intX, intY-height, width, 3);
		else g.fillRect(intX+width, intY-height, width, 3);
		
		for(Foam f : foam)
			if(f != null) f.render(g);
		
	}

}
