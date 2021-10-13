import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MessageGroup {
	
	String name;
	Random r = new Random();
	List<String> lines;
	int lineIndex;
	
	public MessageGroup(String name){
		this.name = name;
		lines = new LinkedList<String>();
	}
	
	public String getLine(){
		if(lines.size()==0) return "";
		String line;
		if(lineIndex < lines.size()){
			line = lines.get(lineIndex);
			lineIndex ++;
		}
		else {
			lineIndex = 0;
			Collections.shuffle(lines);
			line = getLine();
			
		}
		return line;
	}
	
	public String toString(){
		String str = name+"\n";
		for(String l : lines)
			str += "\t"+l+"\n";
		return str;
	}
}
