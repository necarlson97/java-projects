
public class MessageArt {

	String name;
	BotMessage[] messages;
	String[] lines;
	
	public MessageArt(String name, String[] lines){
		this.name = name;
		this.lines = lines;
		this.messages = stringArrayToMessage(lines);
	}

	private BotMessage[] stringArrayToMessage(String[] lines) {
		messages = new BotMessage[lines.length];
		for(int i=0; i<lines.length; i++)
			messages[i] = new BotMessage(lines[i]);
		return messages;
	}
	
	public String toString(){
		String str = "";
		for(String l : lines){
			str += l+"\n";
		}
		return str;
	}
	
}
