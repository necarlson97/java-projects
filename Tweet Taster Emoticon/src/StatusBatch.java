import java.awt.Font;
import java.awt.Graphics;

import twitter4j.Status;

public class StatusBatch {
	
	static int shortSize = 10;
	static int medSize = 10;
	static int longSize = 10;
	
	static Emotion[] emotions = TwitterState.emotions;
	
	float[][] shortMem = new float[shortSize][emotions.length];
	float[][] medMem = new float[medSize][emotions.length];
	float[][] longMem = new float[longSize][emotions.length];
	
	int shortIndex;
	int medIndex;
	int longIndex;
	
	int x = TweetTaster.windowHeight;
	int y = TweetTaster.windowHeight;
	int height = TweetTaster.windowHeight;
	int width = (TweetTaster.windowWidth-TweetTaster.windowHeight)/(emotions.length*3);
	
	float[] shortHeight = new float[emotions.length];
	float[] medHeight = new float[emotions.length];
	float[] longHeight = new float[emotions.length];
	float barRatio = .01f;
	
	Font barFont = new Font("Consolas", 0, 24);
	
	StatusBatch() {
		
	}
	
	void add(int emotionIndex) {
		shortIndex++;
		
		shortMem[shortIndex] = new float[emotions.length];
		shortMem[shortIndex][emotionIndex] =  1;
		
		
		if(shortIndex >= shortSize-1) {
			shortIndex = 0;
			nextMed();
		}
		
		
	}
	
	void nextMed() {
		medIndex ++;
		
		medMem[medIndex] = average(shortMem);	
		
		if(medIndex >= medSize-1) {
			medIndex = 0;
			nextLong();
		}
	}
	
	void nextLong() {
		longIndex = (longIndex+1) % longSize;
		
		longMem[longIndex] = average(medMem);
		
		setEmotion();
	}
	
	
	private void setEmotion() {
		float max = 0;
		int index = 0;
		
		Emotion e = emotions[2];
		
		float[] medAvg = average(medMem);
		float[] longAvg = average(longMem);
		
		for(int i=0; i<emotions.length; i++) {
			float temp = medAvg[i] - longAvg[i];
			if(temp > max) {
				max = temp;
				index = i;
			}
		}
		
		e = emotions[index];
		e.setStage(max);
		
		TweetTaster.setEmotion(e);
	}

	float[] average(float[][] toAverage) {
		float[] avg = new float[emotions.length];
	
		for(int i=0; i<avg.length; i++) {
			for(int j=0; j<toAverage.length; j++) {
				avg[i] += toAverage[j][i];
			}
			avg[i] /= toAverage.length;
		}
		
		return avg;
	}

	void render(Graphics g) {
		renderAvg(g);
	}
	
	private void renderAvg(Graphics g) {
		int actualHeight;
		x = height;
		
		float[] avg = average(shortMem);
		
		for(int i=0; i<emotions.length; i++) {
			g.setColor(emotions[i].color);
			actualHeight = (int) (height*avg[i]);
			shortHeight[i] += (actualHeight-shortHeight[i]) * barRatio;
			g.drawRect(x, y-actualHeight, width, actualHeight);
			g.fillRect(x, y-(int)shortHeight[i], width, (int)shortHeight[i]);
	
			x+=width;
		}
		
		avg = average(medMem);
		
		for(int i=0; i<emotions.length; i++) {
			g.setColor(emotions[i].color.darker());
			actualHeight = (int) (height*avg[i]);
			medHeight[i] += (actualHeight-medHeight[i]) * barRatio;
			g.drawRect(x, y-actualHeight, width, actualHeight);
			g.fillRect(x, y-(int)medHeight[i], width, (int)medHeight[i]);
			
			x+=width;
		}
		
		avg = average(longMem);
		
		for(int i=0; i<emotions.length; i++) {
			g.setColor(emotions[i].color.darker().darker());
			actualHeight = (int) (height*avg[i]);
			longHeight[i] += (actualHeight-longHeight[i]) * barRatio;
			g.drawRect(x, y-actualHeight, width, actualHeight);
			g.fillRect(x, y-(int)longHeight[i], width, (int)longHeight[i]);
			
			x+=width;
		}
	}
	
	private String shortString() {
		String str = "Short\n";
		
		for(int j=0; j<shortSize; j++) {
			str += j+"\t";
			for(int i=0; i<emotions.length; i++) {
				str += "\t"+shortMem[j][i];
			}
			str += "\n";
		}
		
		return str;
	}
	
	private String medString() {
		String str = "Med\n";
		
		for(int j=0; j<medSize; j++) {
			str += j+"\t";
			for(int i=0; i<emotions.length; i++) {
				str += "\t"+medMem[j][i];
			}
			str += "\n";
		}
		
		return str;
	}
	
	private String longString() {
		String str = "Long\n";
		
		for(int j=0; j<longSize; j++) {
			str += j+"\t";
			for(int i=0; i<emotions.length; i++) {
				str += "\t"+longMem[j][i];
			}
			str += "\n";
		}
		
		return str;
	}
	
	public String toString() {
		String str = "";
		
		str += shortString();
		str += medString();
		str += longString();
		
		return str;
	}

}
