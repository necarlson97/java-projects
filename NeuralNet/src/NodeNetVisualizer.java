import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class NodeNetVisualizer extends Canvas implements Runnable{
	public static final int WIDTH = 800, HEIGHT = 800;
	private Random r = new Random();
	private NodeNet nodeNet;
	
	public float fitness = -1;
	
	public NodeNetVisualizer(NodeNet nodeNet) {
		this.nodeNet = nodeNet;
		
		new Window(WIDTH, HEIGHT, "Node Net Visualizer", this);
	}
	
	@Override
	public void run() {
		render();
	}
	
	public void update(NodeNet newNodeNet){
		this.nodeNet = newNodeNet;
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			this.run();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		bs.show();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		// find the largest layer to base the node size off of
		int largestLayerSize=0;
		for(int i=0; i<nodeNet.depth; i++){
			if(nodeNet.layers[i].size > largestLayerSize) largestLayerSize = nodeNet.layers[i].size;
		}
		
		int nodeDiameter = (nodeNet.depth > largestLayerSize) ? ((WIDTH-20)/nodeNet.depth) / 2 : ((WIDTH-20)/largestLayerSize) / 2;
		drawLayer(g, 0, nodeDiameter, nodeNet.layers[0], nodeNet.layers[1]);
		for(int i=1; i<nodeNet.depth-1; i++){
			drawLayer(g, i, nodeDiameter, nodeNet.layers[i], nodeNet.layers[i+1]);
		}
		drawLayer(g, nodeNet.depth-1, nodeDiameter, nodeNet.layers[nodeNet.depth-1], null);
		
		drawOutput(g, nodeNet.getOutput());
		
		if(fitness != 0) drawFitness(g, fitness);
		
		g.dispose();
		bs.show();
		
	}
	
	public void drawFitness(Graphics g, float fitness){
		g.setColor(Color.white);
		String fitnessString = "Fitness: "+String.valueOf(fitness);
		g.drawString(fitnessString, WIDTH-100, 30);
	}
	
	private void drawOutput(Graphics g, float[] output) {
		g.setColor(Color.white);
		String outputString = "Output: [";
		for(float o : output){
			if(String.valueOf(o).length() > 4) outputString += String.valueOf(o).substring(0, 4);
			else outputString +=String.valueOf(o);
			outputString += ", ";
		}
		outputString = outputString.substring(0, outputString.length()-2) + "]";
		g.drawString(outputString, WIDTH-(10*outputString.length()), HEIGHT-30);
		
	}

	private void drawLayer(Graphics g, int layerNumber, int nodeDiameter, NodeLayer nodeLayer, NodeLayer nextNodeLayer) {
		// Gets a good size for the nodes dependent if the net is deeper then it is tall, or vice versa
				
		int xPosition = ((nodeDiameter*2) * layerNumber) + 10;
		int yPositionIncrement = HEIGHT/nodeLayer.size;
		int yPosition = 10;
		
		
		
		// draw the nodes
		Node currentNode;
		for(int i=0; i<nodeLayer.size; i++){
			currentNode = nodeLayer.nodes[i];
			
			// Draws it at the current layer's x, increments the y, and with the calculated diameter
			
			//g.setColor(new Color(1-currentNode.value, (float) .1, currentNode.value));
			g.setColor(new Color((float) .5, (float) xPosition/WIDTH, (float) .1));
			
			g.fillOval(xPosition, yPosition, nodeDiameter, nodeDiameter);
			
			if(nodeDiameter > 30){
				g.setColor(Color.white);
				String apxValue = String.valueOf(currentNode.value).substring(1, 3);
				g.setFont(new Font(g.getFont().getFontName(), 0, nodeDiameter/2));
				g.drawString(apxValue, xPosition + nodeDiameter/4, yPosition + nodeDiameter/2);
			}
			
			
			// draw the connectors to the next layer of nodes (unless this is the last layer of nodes, and there is no next layer)
			if(nodeDiameter > 20 && nextNodeLayer != null){
				
				int lineXPosition = xPosition + (nodeDiameter);
				int lineYPosition = nodeDiameter/2 + 10;
				
				for(int j=0; j<nodeLayer.size; j++){
					
					int nextXPosition = xPosition + (nodeDiameter * 2);
					int nextYPositionIncrement = HEIGHT/nextNodeLayer.size;
					int nextYPosition = nodeDiameter/2 + 10;
					
					for(int n=0; n<nextNodeLayer.size; n++){
	
						g.setColor(new Color(1-currentNode.weights[n], (float) .5, currentNode.weights[n]));
						
						// draws a line from the right side of one circle to the left side of the other
						g.drawLine(lineXPosition, lineYPosition, nextXPosition, nextYPosition);
					
						nextYPosition += nextYPositionIncrement;
						
					}
					
					lineYPosition += yPositionIncrement;
				}
			}
			yPosition += yPositionIncrement;
		}
	}
}


