import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.naming.Context;

public class Entry extends JournalTextWindow {
	
	String fileName;
	String content;
	
	static boolean textUp = false; 
	static boolean textDown = false; 
	
	float entryY = Journal.margin*8;;
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
	}
	
	@Override
	public void run(){
		super.run();
		if(count%800==0 || charsSinceSave %11 == 10) save();
		
		upAndDown();
		depreciateVelocity();
	}
	
	private void upAndDown(){
		if(textUp && entryYVel > -1.5) entryYVel-= entrySpeed;
		else if(textDown && entryYVel < 1.5) entryYVel+= entrySpeed;
		entryY += entryYVel;
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
		for(String w : content.split("((?<=[ \\n])|(?=[ \\n]))")){
			g.setColor(offWhite);
			if ( needNewLine(w, intX) ) {
				intX = partitionX+margin*2;
				intY += bodyPointSize+margin;
				if(w.contains("\n")) {
					if(debug) g.drawOval(intX, intY, margin, margin);
					renderedIndex += w.length();
					continue;
				}
			}
			
			if(debug) g.drawLine(intX+margin/2, intY, intX+w.length()*bodyPointSize-margin/2, intY);
			g.drawString(w, intX, intY);
			
			if(renderedIndex + w.length() > contentIndex) {
				int indexOfIndex = contentIndex - renderedIndex;
				renderedIndex = renderTextPointer(g, intX, intY, indexOfIndex);
			}
			else if (renderedIndex >= 0) renderedIndex += w.length();
			
			intX += w.length()*bodyPointSize;
		}
		if(renderedIndex >= 0) {
			int indexOfIndex = contentIndex - renderedIndex;
			renderedIndex = renderTextPointer(g, intX, intY, indexOfIndex);
		}
		
		g.setColor(Color.black);
		g.fillRect(partitionX, 0, windowWidth-partitionX, margin*5);
		
		g.setFont(headerFont);
		
		g.setColor(Color.white);
		
		g.drawLine(partitionX, 0, partitionX, windowHeight);
		
		g.drawString(fileName, partitionX+margin, margin*5);
		
	}
	
	private int renderTextPointer(Graphics g, int intX, int intY, int index) {
		g.setColor(new Color(pointerColor, pointerColor, pointerColor));
		g.drawRect(intX+(index*bodyPointSize), intY-bodyPointSize, 0, bodyPointSize);
		
		if(intY > windowHeight) entryYVel -= 1;
		
		return -10;
	}
	
	private boolean needNewLine(String s, int intX){
		return s.contains("\n") || 
				(intX + s.length()*bodyPointSize > entryLineCutoff &&
				intX != partitionX+margin*2);
	}
	
	public void load(){
		content = Journal.fileLoader.readFile(fileName);
	}
	
	public void save(){
		charsSinceSave = 0;
		Journal.fileLoader.writeFile(fileName, content);
	}

	public void deleteChar() {
		if(content == "") return;
		String before = content.substring(0, contentIndex-1);
		String after = content.substring(contentIndex);
		content = before + after;
		contentIndex -= 1;
		charsSinceSave += 1;
	}

	public void addChar(char c) {
		content = content.substring(0, contentIndex) + c + content.substring(contentIndex) ;
		contentIndex += 1;
		charsSinceSave += 1;
	}

	public void changeContentIndex(int change) {
		if(contentIndex+change < content.length() && contentIndex+change > 0) contentIndex += change;
		else if (change > 0) contentIndex = content.length();
		else if (change < 0) contentIndex = 0;
	}
	

}
