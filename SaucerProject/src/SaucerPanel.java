import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public  class SaucerPanel extends JPanel implements ActionListener{
  //environmental features
  private final static int panelWidth=500;
  private final static int panelHeight=400;
  private final static int groundHeight = 50;
  private final static int groundYpos = panelHeight-groundHeight;
  private final static Color groundColor=new Color(0, 128, 0);
  private final static Color skyColor = new Color(182, 227, 252);
  private final static Color buildingColor = Color.lightGray;
  //building one
  private final static int buildingOneXpos=200;
  private final static int buildingOneYpos=200;
  private final static int buildingOneHeight=150;
  private final static int buildingOneWidth=30;
  //building two
  private final static int buildingTwoXpos=350;
  private final static int buildingTwoYpos=250;
  private final static int buildingTwoHeight=100;
  private final static int buildingTwoWidth=15;
  //saucer
  private final static int saucerWidth = 90;
  private final static int saucerHeight = 40;
  private final static int saucerMoveIncrement = 5;//how far to move the saucer each tick
  private final static int initialSaucerXpos = 10;
  private final static int initialSaucerYpos = 50;
  private final static Color saucerColor = Color.black;
  private String saucerHoveringStatus = "hovering...";
  private String saucerLandedStatus = "landed!";
  private String saucerStatus = saucerHoveringStatus;
  private int saucerXpos = initialSaucerXpos; 
  private int saucerYpos = initialSaucerYpos;
  private boolean hovering = true;// state of saucer
  private String direction; //direction of saucer
  //GUI components 
  private JButton upBtn = new JButton("Up");
  private JButton downBtn = new JButton("Down");
  private JButton leftBtn = new JButton("Left");
  private JButton rightBtn = new JButton("Right");
  private JButton resetBtn = new JButton("Reset");
  
  // used to generate random dots as "debris" from a crash
  private Random r = new java.util.Random();
  // Timer object with a delay of 100ms
  private Timer timer = new Timer(100,this);

  public SaucerPanel(){
    setPreferredSize(new Dimension(panelWidth, panelHeight));
    setBackground(skyColor);
    setOpaque(true);
    this.add(upBtn);  
    this.add(downBtn);
    this.add(leftBtn); 
    this.add(rightBtn);
    this.add(resetBtn);
    upBtn.addActionListener(this);
    downBtn.addActionListener(this);
    leftBtn.addActionListener(this);
    rightBtn.addActionListener(this);
    resetBtn.addActionListener(this);
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    drawGround(g);
    drawBuildings(g);
    if (hovering) 
      drawSaucer(g, saucerXpos, saucerYpos);
    else
      drawCrash(g);
  }
  
  private void drawSaucer(Graphics g, int xPos, int yPos){
    g.setColor(saucerColor);
    g.fillOval(xPos, yPos, saucerWidth, saucerHeight);
    //15pixels added to yPos+saucerHeight to make status appear under saucer
    g.drawString(saucerStatus, xPos, (yPos+saucerHeight+15));
  }
  
  private void drawBuildings(Graphics g){
    g.setColor(buildingColor);
    g.fillRect(buildingOneXpos, buildingOneYpos, buildingOneWidth, buildingOneHeight);
    g.fillRect(buildingTwoXpos, buildingTwoYpos, buildingTwoWidth, buildingTwoHeight);
  }
  
  private void drawGround(Graphics g){
    g.setColor(groundColor);
    g.fillRect(0, groundYpos, panelWidth, groundHeight);
  }
  
  //draw a bunch of points to simulate debris
  private void drawCrash(Graphics g){
    g.setColor(Color.black);
    for(int i = 0; i < 400; i++)
      ranPoints(10,350,g);
  }   
  
  //generates the debris
  private void ranPoints(int x, int y, Graphics g){
    int xLoc = r.nextInt(500);
    int yLoc = y - r.nextInt(350);
    g.fillOval(xLoc,yLoc,6,6);
  }

  // true if the saucer landed between the buildings
  private boolean landedInBounds(int xPos){
      return(xPos>=(buildingOneXpos+buildingOneWidth) 
          && xPos<= (buildingTwoXpos-saucerWidth));
  }
  
  // test if the saucer touches either building
  private boolean saucerTouchingBuilding(int xPos, int yPos){
    boolean result=false;
    //calculate the saucer "boundary" points:
    int sauceRx=xPos+saucerWidth;
    int sauceLx=xPos;
    int sauceLy=yPos+saucerHeight;
    int sauceUy=yPos;
    // building 1 
    if((buildingOneXpos<sauceRx && buildingOneXpos>sauceLx) && (sauceLy>=buildingOneYpos) )
         result = true;
    if((buildingOneXpos<sauceRx && (buildingOneXpos+buildingOneWidth)>sauceLx) && (sauceLy>=buildingOneYpos) )
         result = true;
    // building 2
    if((buildingTwoXpos<sauceRx && buildingTwoXpos>sauceLx) && (sauceLy>=buildingTwoYpos) )
         result = true;
    if((buildingTwoXpos<sauceRx && (buildingTwoXpos+buildingTwoWidth)>sauceLx) && (sauceLy>=buildingTwoYpos) )
         result = true;
    return result;
  }
  
  public void actionPerformed(ActionEvent e){
	  
	  // Draw saucer (and background over former saucer position
	  paintComponent(getGraphics());
	  
	  // Begins timer upon first press, and sets direction based on button press
	  if(!timer.isRunning() && direction == null) timer.start();  
	  direction = e.getActionCommand();
	  timer.setActionCommand(direction);
	  
	  // Setting of saucer movement or activation of reset
	  if(direction == "Up") saucerYpos -= saucerMoveIncrement;
	  if(direction == "Down") saucerYpos += saucerMoveIncrement;
	  if(direction == "Left") saucerXpos -= saucerMoveIncrement;
	  if(direction == "Right") saucerXpos += saucerMoveIncrement;
	  if(direction == "Reset"){
		  timer.stop();
		  timer.restart();
		  direction = null;
		  saucerYpos = initialSaucerYpos;
		  saucerXpos = initialSaucerXpos;
		  hovering = true;
		  saucerStatus = saucerHoveringStatus;
	  }
	  
	  // Stops timer after a single frame of crash debris or landing success
	  if(hovering == false || saucerStatus == saucerLandedStatus) timer.stop();
	  
	  // Crashing or landing boundry checks
	  if(saucerTouchingBuilding(saucerXpos, saucerYpos)) hovering = false;
	  if(saucerYpos >= groundYpos-saucerHeight){
		  if (landedInBounds(saucerXpos)) saucerStatus = saucerLandedStatus;
		  else hovering = false;
	  }
	  
  }
}

