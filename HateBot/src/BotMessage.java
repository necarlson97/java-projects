
public class BotMessage extends Message{
	
	
	int charIndex = 1;
	int delay = 5;

	public BotMessage(String inputString) {
		super("");
		fullString = inputString;
		if(inputString.length() > 100) delay = 1;
		else if(inputString.length() > 50) delay = 2;
		else if(inputString.length() > 30) delay = 3;
		else if(inputString.length() > 20) delay = 5;
		else if(inputString.length() > 10) delay = 10;
		else if(inputString.length() > 5) delay = 20;
		else delay = 40;
		
		grn = 1 - ((float)HateBot.hateLevel/10);
		blu = 1 - ((float)HateBot.hateLevel/10);
	}
	
	public void run(int count){
	
	}
	
	public void increment(int count){
		if(count % delay == 0 && charIndex <= fullString.length()) {
			charIndex ++;
			drawString = fullString.substring(0, charIndex);
		}
	}

}
