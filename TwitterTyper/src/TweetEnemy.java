import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;

public class TweetEnemy {
	
	float x;
	float y;
	
	float yVel;
	float boost;
	
	int number;
	
	String text;
	char[] textChars;
	
	int nextIndex = -1;
	char lastChar;
	
	int inc = 0;
	float red = (float).2;
	float grn = (float).5;
	float blu = (float).7;
	
	String typeFace = TwitterTyper.typeFace;
	int pointSize;
	
	boolean finished;
	
	Confetti[] confetti = new Confetti[10];
	
	public TweetEnemy(String rawText, int number, int batch){
		x = (float) (TwitterTyper.windowWidth*.1);
		y = (float) -(TwitterTyper.windowWidth*number*.2);
		this.number = number;
		text = rawText.replaceAll("[^\\x20-\\x7F]", "").toLowerCase();
		text = text.replaceAll("(http).*(\\s|$)", "");
		textChars = split(text);
		pointSize = (int) (TwitterTyper.windowWidth*.02);
		yVel = (float) batch/7;
	}
	
	public void run(){
		y+=yVel+boost;
		boost = 0;
		
		for(int i=0; i<confetti.length ;i++) {
			Confetti conf = confetti[i];
			if(conf!=null) {
				conf.run();
				if(conf.count>=conf.LIFETIME) confetti[i] = null;
			}
		}
		
		if(finished || nextIndex < 0) return;
		
		if(nextIndex >= text.length()) {
			finished = true;
		}
		else if(lastChar == text.charAt(nextIndex) || text.charAt(nextIndex) == ' ') {
			nextIndex++;
			TwitterTyper.score++;
		}
		lastChar = '\u0000';
	}
	
	public char[] split(String input){
		char[] split = input.toCharArray();
		
		return split;
	}
	

	public void render(Graphics g){
		Font font = new Font(typeFace, Font.PLAIN, pointSize);
		g.setFont(font);
		
		red = (float) ((red+.013)%2);
		blu = (float) ((blu+.017)%2);
		grn = (float) ((grn+.023)%2);
		
		float dRed = (float) ((Math.abs(red-1)+.9)/2);
		float dGrn = (float) ((Math.abs(grn-1)+.9)/2);
		float dBlu = (float) ((Math.abs(blu-1)+.9)/2);
		
		int i=0;
		int charX = (int)x;
		int charY = (int)y;
		for(char c : textChars ) {
			if(finished) g.setColor(new Color(0, dGrn, 0));
			else if(i<nextIndex) g.setColor(new Color(dRed, dGrn, dBlu));
			else if(i==nextIndex) g.setColor(new Color(dRed, 0, 0));
			else if(i>nextIndex) g.setColor(Color.white);
			
			g.drawString(""+c, charX, charY);
			
			if(i == nextIndex-1 && c==' ' && lastChar==' ') {
				textChars[i] = '·';
				confettiBurst(charX+pointSize/2, charY-pointSize/2);
				TwitterTyper.score++;
			}
			
			charX+=pointSize;
			if(charX>TwitterTyper.windowWidth*.6 && (c==' ' || c=='·')) {
				charY+=pointSize;
				charX = (int)x;
			}
			i++;
		}
		
		for(Confetti conf : confetti)
			if(conf!=null) conf.render(g);
		
		if(TwitterTyper.debug && lastChar != '\u0000'){
			g.setFont(new Font(typeFace, Font.PLAIN, pointSize*3));
			g.setColor(Color.WHITE);
			int dx = (int) (TwitterTyper.windowWidth*.9);
			int dy = (int) (TwitterTyper.windowHeight*.9);
			g.drawString(lastChar+"", dx, dy);
		}
		
	}
	
	private void confettiBurst(int x, int y){
		for(int i=0; i<confetti.length; i++){
			confetti[i] = new Confetti(i, x, y);
		}
	}
	
	
	public String toString(){
		return text+"\nnextIndex: "+nextIndex;
	}

}
