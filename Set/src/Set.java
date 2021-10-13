import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Set extends Game{
	
	int xMargin;
	int yMargin;
	
	int cardWidth;
	int cardHeight;

	
	Card[] fullDeck = new Card[81]; 
	Card[][] table = new Card[4][4];
	LinkedList<Card> deck;
	
	int i=-1;
	
	Font font;
	
	LinkedList<Card> selected = new LinkedList<Card>();
	
	FullTableButton button;
	boolean fullTable = false;
	
	int playerSets = 0;
	
	public static void main(String[] args) {
		new Set();
	}

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		
		font = new Font("Press Start 2p", Font.PLAIN, (int) (windowWidth *.03));
		
		xMargin = (int) (windowWidth *.2);
		yMargin = (int) (windowWidth *.2);
		
		
		cardWidth = (int) (windowWidth *.15);
		cardHeight = (int) (windowHeight *.15);
		
		button = new FullTableButton(this);
		
		setupDeck();
	}

	private void setupDeck() {
		
		int i=0;
		for(int s=0; s<3; s++) {
			for(int c=0; c<3; c++) {
				for(int f=0; f<3; f++) {
					for(int n=1; n<4; n++) {
						fullDeck[i] = 
								new Card(s, c, f, n, this);
						i++;
					}
				}
			}
		}
		
		deck = new LinkedList<Card>(Arrays.asList(fullDeck));
		Collections.shuffle(deck);
	}

	private Card nextCard() {
		if(deck.isEmpty()) {
			System.out.println("out of cards");
			return null;
		}
		return deck.removeFirst();
	}
	
	@Override
	void click(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int row = -1;
		int col = -1;
		
		if(x > xMargin && x < windowWidth - xMargin &&
				y > yMargin && y < yMargin + cardHeight * 4) {
			row = (y - yMargin) / cardHeight;
			col = (x - xMargin) / cardWidth;
			
			onCard(row, col);
		}
		
		else if(x > button.x && x < button.x+button.width && 
				y > button.y && y < button.y+button.height) {
			fullTable = true;
		}
		
	}
	
	private void onCard(int row, int col) {
		if(table[row][col] != null) {
			Card c = table[row][col];
			if(!c.selected) select(c);
			else deselect(c);
			
			if(selected.size() > 3) deselect(selected.getLast());
			else if(selected.size() == 3 && isSet(selected))
				takeSet(selected);
				
		}
	}
	
	private void select(Card c) {
		selected.add(c);
		c.selected = true;
	}
	
	private void deselect(Card c) {
		selected.remove(c);
		c.selected = false;
	}
	
	private void takeSet(LinkedList<Card> set) {
		for(Card c : set) {
			c.selected = false;
		}
		for(int row=0; row<4; row++) {
			for(int col=0; col<4; col++) {
				if(set.contains(table[row][col]))
					table[row][col] = null;
			}
		}
		set.clear();
		playerSets++;
	}
	
	@Override
	void keyPressed(KeyEvent e) {
		
	}

	@Override
	void keyReleased(KeyEvent e) {
		
	}
	
	public boolean isSet(LinkedList<Card> list) {

		Card[] cards = list.toArray(new Card[3]);
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(i != j) {
					for(int t=0; t<4; t++) {
						if(cards[i].traits[t] == cards[j].traits[t]) {
							if(!isShared(cards, t)) return false;
						}
					}
				}
			}
		}
			
		return true;
	}
	
	public boolean isShared(Card[] cards, int t) {
		int trait = cards[0].traits[t];
		for(int i=0; i<cards.length; i++) {
			if(cards[i].traits[t] != trait) return false;
		}
		
		return true;
	}

	@Override
	void runGame() {
		
		int rows = 3;
		if(fullTable) rows = 4;
		for(int row=0; row<rows; row++) {
			for(int col=0; col<4; col++) {
				if(table[row][col] == null)
					table[row][col] = nextCard();
			}
		}
		
	}

	@Override
	void renderGame(Graphics2D g) {
		g.setStroke(new BasicStroke(3));
		
		int intX = xMargin;
		int intY = yMargin;
		
		for(Card[] row : table) {
			for(Card c : row) {
				if(c != null) c.render(g, intX, intY);
				intX += cardWidth;
			}
			intX = xMargin;
			intY += cardHeight;
		}
		
		button.render(g);
		
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("Cards left: "+deck.size(), xMargin, 100);
		g.drawString("Sets taken: "+playerSets, xMargin, windowHeight-50);
		
	}

}
