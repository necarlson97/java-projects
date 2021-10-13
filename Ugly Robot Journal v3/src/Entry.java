import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.naming.Context;

public class Entry extends JournalTextWindow {
	
	String fileName;
	String content;
	
	static boolean textUp = false; 
	static boolean textDown = false; 
	
	float entryY = Journal.margin*8;
	float entryYVel = 0;
	float entrySpeed = (float) .2;
	
	int entryLineCutoff = (int) (Journal.windowWidth * .95);
	
	int contentIndex = 0;
	
	int charsSinceSave = 0;
	
	int pointerColor;
	
	public Entry(String fileName) {
		this.fileName = fileName;
		load();
		contentIndex = content.length();
		pointerOffScreen();
	}
	
	@Override
	public void run(){
		super.run();
		if(count%800==0 || charsSinceSave %11 == 10) save();
		
		upAndDown();
		depreciateVelocity();
	}
	
	private void upAndDown(){	
		if(textUp && entryYVel > -3) entryYVel+= entrySpeed;
		else if(textDown && entryYVel < 3) entryYVel-= entrySpeed;
		entryY += entryYVel;
		
		if(entryY > margin*8) entryY = margin*8; 
	}
	
	private void depreciateVelocity() {
		double resistance = 1.1;
		if(entryYVel < 0) entryYVel/=resistance;
		else if(entryYVel > 0) entryYVel/=resistance;
		if(Math.abs(entryYVel) < .01 ) entryYVel = 0;
	}
	
	@Override
	public void render(Graphics g){
		
		if(debug) {
			g.setColor(Color.red);
			g.drawLine(entryLineCutoff, 0, entryLineCutoff, windowHeight);
			g.drawString(""+contentIndex, windowWidth - 2*bodyPointSize, 5*bodyPointSize);
		}
		
		g.setFont(bodyFont);
		int intY = (int) (entryY);
		int intX = partitionX+margin*2;
		
		pointerColor = (int) Journal.oscillateNumber(count, 80, 225);
		
		int renderedIndex = 0;
		boolean rendered = false;
		for(String w : content.split("((?<=[ \\n])|(?=[\\n]))")){
			
			if(!rendered && renderedIndex + w.length() > contentIndex) {
				int indexOfIndex = contentIndex - renderedIndex;
				if(indexOfIndex < 0) indexOfIndex = 0;
				renderTextPointer(g, intX, intY, indexOfIndex);
				rendered = true;
			}
			else renderedIndex += w.length();
			
			if ( needNewLine(w, intX) ) {
				intX = partitionX+margin*2;
				intY += bodyPointSize+margin;
				if(w.contains("\n")) {
					if(debug) g.drawOval(intX, intY, margin, margin);
					continue;
				}
			}
			
			if(debug) {
				g.setColor(offWhite.darker().darker());
				for(int i=0; i<w.length(); i++)
					g.drawRect(intX+(i*bodyPointSize), intY-bodyPointSize, bodyPointSize, bodyPointSize);
			}
			g.setColor(offWhite);
			g.drawString(w, intX, intY);
			
			intX += w.length()*bodyPointSize;
		}
		if(!rendered) {
			int indexOfIndex = contentIndex - renderedIndex;
			renderTextPointer(g, intX, intY, indexOfIndex);
		}
		
		g.setColor(Color.black);
		g.fillRect(partitionX, 0, windowWidth-partitionX, margin*5);
		
		g.setFont(headerFont);
		
		Journal.sidebar.resetDate();
		g.setColor(Journal.sidebar.getDayColor());
		
		g.drawLine(partitionX, 0, partitionX, windowHeight);
		
		g.drawString(fileName, partitionX+margin, margin*5);
		
	}
	
	private void renderTextPointer(Graphics g, int intX, int intY, int index) {
		g.setColor(new Color(pointerColor, pointerColor, pointerColor));
		g.drawRect(intX+(index*bodyPointSize), intY-bodyPointSize, 0, bodyPointSize);
	}
	
	private void pointerOffScreen(){
		int intX = partitionX+margin*2;
		int intY = (int) (entryY);
		int renderedIndex = 0;
		for(String w : content.split("((?<=[ \\n])|(?=[\\n]))")){
			if ( needNewLine(w, intX) ) {
				intX = partitionX+margin*2;
				intY += bodyPointSize+margin;
			}
			if(renderedIndex + w.length() > contentIndex) break;
			renderedIndex += w.length();
			intX += w.length()*bodyPointSize;
		}
		if(intY < margin*8 + bodyPointSize) centerScreenTo((margin*8 + bodyPointSize) - intY);
		if(intY >= windowHeight - margin) centerScreenTo(((windowHeight-margin) / 2) - intY);
	}
	
	private void centerScreenTo(int newY){
		entryYVel = (float) (newY * .09);
	}
	
	private boolean needNewLine(String s, int intX){
		return s.contains("\n") || 
				(intX + s.trim().length()*bodyPointSize > entryLineCutoff &&
				intX != partitionX+margin*2);
	}
	
	public void load(){
		content = FileLoader.readFile(fileName);
	}
	
	public void save(){
		charsSinceSave = 0;
		FileLoader.writeFile(fileName, content);
	}

	public void deleteChar() {
		if(content.length() == 0) return;
		String before = "";
		if(contentIndex > 0) before = content.substring(0, contentIndex-1);
		String after = content.substring(contentIndex);
		content = before + after;
		changeContentIndex(-1);
		charsSinceSave += 1;
		pointerOffScreen();
	}

	public void addChar(char c) {
		content = content.substring(0, contentIndex) + c + content.substring(contentIndex) ;
		contentIndex += 1;
		charsSinceSave += 1;
		pointerOffScreen();
	}

	public void changeContentIndex(int change) {
		if(contentIndex+change > content.length()) contentIndex = content.length();
		else if(contentIndex+change < 0) contentIndex = 0; 
		else contentIndex += change;
	}
	

}
