import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Plane {
	
	Point loc = new Point();
	
	AffineTransform identity = new AffineTransform();
	AffineTransform trans = new AffineTransform();
	
	// 0 is gear down
	// 1 is gear up
	BufferedImage[] sprites = new BufferedImage[2];

	float kmphX;
	float kmphY;
	
	float lift = 20;
	float drag = 0;
	float gravity = 9.8f * 60;
	
	float diminishFactor = .9f;
	
	float length;
	float height;
	int planeKilos;
	int fuelKilos;
	int maxSpeed;
	
	float throttle;
	float throttleIncrease = .001f;
	float throttleThrust = 2;
	
	float angle;
	float angleIncrease = .01f;
	
	int terminalVelocity = 2160;
	
	boolean gearDown = true;
	
	Plane(float length, float height, int planeKilos, int fuelKilos, int maxSpeed) {
		this.length = length;
		this.height = height;
		this.planeKilos = planeKilos;
		this.fuelKilos = fuelKilos;
		this.maxSpeed = maxSpeed;
		
		loc.y = height;
	}
	
	void update() {
		applyForces();
		diminishForces();
	}

	private void applyForces() {
		
		float attackAngle = -(float) (Math.toDegrees(angle)); 
		if(attackAngle < -180) attackAngle += 360;
		
		if(kmphX < maxSpeed) kmphX += throttle * throttleThrust;
		if(kmphX > 0) kmphY += attackAngle * lift * kmphX;
		
		if(kmphY > 0) kmphY -= drag;
		else kmphY += drag;
			
		if(loc.y > height/2) kmphY -= gravity;
		if(loc.y < height/2) kmphY = (float) (Math.abs(kmphY)*.5);
		
		if(Math.abs(kmphY) > terminalVelocity) kmphY = (kmphY/Math.abs(kmphY)) * terminalVelocity;
		
		loc.x += kmphX / (60*Engine2D.FPS);
		loc.y += kmphY / (60*Engine2D.FPS);
	}
	
	private void diminishForces() {
		kmphY *= diminishFactor;
		kmphX *= diminishFactor;
	}

	void render(Graphics g) {
		Point pos = relativePosition(loc);
		int renderWidth = (int) (length * Display.pixelPerMeter);
		int renderHeight = (int) (height * Display.pixelPerMeter);
		
		Graphics2D g2d = (Graphics2D)g.create();
		trans.setTransform(identity);
		
		g2d.translate((int)pos.x-renderWidth/2, (int)pos.y-renderHeight/2);
		trans.rotate(angle, renderWidth/2, renderHeight/2);
		
		if(gearDown) g2d.drawImage(sprites[0], trans, null);
		else g2d.drawImage(sprites[1], trans, null);
		
		g.setColor(Color.green);
		g.drawRect((int)(pos.x-renderWidth/2), (int)(pos.y-renderHeight/2), renderWidth, renderHeight);
		
		g.setColor(new Color(throttle, 0f, 0f));
		g.fillRect(0, 0, 20, 50);
	}
	
	Point relativePosition(Point location) {
		Point position = new Point(Display.windowWidth/2, Display.windowHeight/2);
		Point camera = Display.cameraLocation();
		position.x -= (loc.x * Display.pixelPerMeter) - camera.x;
		position.y -= (loc.y * Display.pixelPerMeter) - camera.y;
		return position;
	}
	
	void throttleUp() {
		if(throttle < 1-throttleIncrease) throttle += throttleIncrease;
		else throttle = 1;
	}
	
	void throttleDown() {
		if(throttle > 0+throttleIncrease) throttle -= throttleIncrease;
		else throttle = 0;
	}
	
	void angleUp() {
		if(angle+angleIncrease > 2*Math.PI) angle = 0;
		else angle+=angleIncrease;
	}
	
	void angleDown() {
		if(angle-angleIncrease < 0) angle = (float) (2*Math.PI);
		else angle-=angleIncrease;
	}

}
