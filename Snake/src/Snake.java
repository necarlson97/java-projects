import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Snake {
	
	SnakeNode snakeHead;
	
	static boolean left = false;
	static boolean right = false;
	static boolean up = false;
	static boolean down = false;
	
	float prevXAdd;
	float prevYAdd;
	
	final int NORMALSLOW = 8;
	
	int size = 2;
	int slow = NORMALSLOW;
	
	int powerUpCounter;
	boolean grid;
	boolean disco;
	
	boolean dying;
	
	public Snake(int x, int y){
		snakeHead = new SnakeNode(x, y, size-1);
		snakeHead.next = new SnakeNode(x+SnakeGame.boxSize, y, size);
		snakeHead.color = Color.WHITE;
		snakeHead.next.color = Color.BLACK;
	}
	
	public Snake(){
		int x = (SnakeGame.boxCols/2)*SnakeGame.boxSize;
		int y = (SnakeGame.boxRows/2)*SnakeGame.boxSize;
		snakeHead = new SnakeNode(x, y, size-1);
		snakeHead.next = new SnakeNode(x+SnakeGame.boxSize, y, size);
		snakeHead.color = Color.WHITE;
		snakeHead.next.color = Color.BLACK;
	}
	
	private void getFood() {
		size++;
		SnakeNode curr = snakeHead;
		while(curr.next != null){
			curr = curr.next;
		}
		
		curr.next = new SnakeNode((int)curr.x, (int)curr.y, size);
		SnakeGame.addFood();
		
	}
	
	private void removeNode(){
		SnakeNode curr = snakeHead;
		if(curr.next == null || curr.next.next == null) {
			SnakeGame.player = new Snake();
			return;
		}
		size--;
		while(curr.next.next != null){
			curr = curr.next;
		}
		curr.next = null;
	}

	public void run(int count){
	
		if(dying) {
			if(count %10==0) removeNode();
			return;
		}
		if(powerUpCounter >=0) powerUpCounter--;
		if(powerUpCounter==0) resetStats();
		if(count%slow==0) moveHead();
		checkCollisions();
		if(size>(SnakeGame.boxCols/4)) SnakeGame.sizeChange(2);
		
	}
	
	private void resetStats() {
		slow = NORMALSLOW;
		if(grid) SnakeGame.debug = false;
		grid = false;
		disco = false;
	}

	private void checkCollisions() {
		int scale = SnakeGame.boxSize;
		int c = (int) (snakeHead.x/scale);
		int r = (int) (snakeHead.y/scale);
		
		int i=0; 
		while(i<SnakeGame.MAXFOOD) {
			Food f = SnakeGame.food[i];
			if(f!=null) {
				if(f.col == c && f.row == r) {
					if(f.effect.contains("speed")) {
						int speedUp = Integer.valueOf(f.effect.substring(f.effect.length()-2));
						slow = speedUp;
						powerUpCounter = 200;
					}
					else if(f.effect.equals("grid")) {
						SnakeGame.debug = true;
						grid = true;
						powerUpCounter = 200;
					}
					else if(f.effect.equals("disco")) {
						disco = true;
						powerUpCounter = 1000;
					}
					
					SnakeGame.removeFood(i);
					getFood();
				}
			}
			i++;
		}
		
		SnakeNode currNode = snakeHead.next;
		while(currNode!=null){
			int nc = (int) (currNode.x/scale);
			int nr = (int) (currNode.y/scale);
			if(c == nc && r == nr) dying = true;
			currNode = currNode.next;
		}
	}
	
	public void zeroDirections(){
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void moveHead(){
		int scale = SnakeGame.boxSize;
		int rows = SnakeGame.boxRows;
		int cols = SnakeGame.boxCols;
		float xAdd = 0;
		float yAdd = 0;
		
		if(left) xAdd-=1;
		if(right) xAdd+=1;
		
		if(up) yAdd-=1;
		if(down) yAdd+=1;
		
		zeroDirections();
		
		if(xAdd == 0 && yAdd == 0){
			xAdd = prevXAdd;
			yAdd = prevYAdd;
		}
		
		prevXAdd = xAdd;
		prevYAdd = yAdd;
		
		float newX = snakeHead.x+(xAdd*scale);
		float newY = snakeHead.y+(yAdd*scale);
		if(newY < scale || newY > (rows)*scale || newX < scale || newX > (cols)*scale) {
			dying = true;
			return;
		}
				
		snakeHead.move(newX, newY);
		
		
	}
	
	public void render(Graphics g){
		SnakeNode curr = snakeHead;
		while(curr != null){
			curr.render(g);
			curr = curr.next;
		}
	}
	
	public String toString(){
		String str = "Size: "+size+"Nodes: \n";
		SnakeNode currNode = snakeHead;
		while(currNode!=null) {
			str += currNode+"\n";
			currNode = currNode.next;
		}
		
		return str;
	}

}
