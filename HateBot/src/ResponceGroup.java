import java.util.LinkedList;
import java.util.List;

public class ResponceGroup extends MessageGroup{
	
	List<String> keys = new LinkedList<String>();
	MessageGroup negativeResponce; 

	public ResponceGroup(String name, MessageGroup negativeResponce) {
		super(name);
		this.negativeResponce = negativeResponce;
	}
	
	public String toString(){
		String str = "Keys: "+keys+"\n";
		str+= super.toString()+"\n";
		str+= negativeResponce+"\n";
		
		return str;
	}

}
