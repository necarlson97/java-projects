
public abstract class Action implements Comparable{
	
	int destX = -1;
	int destY = -1;
	String name;
	Ant ant;
	int priority;
	
	public Action(Ant a, String n, int x, int y){
		destX = x;
		destY = y;
		name = n;
		ant = a;
	}
	
	public Action(Ant a, String n){
		name = n;
		ant = a;
	}
	
	public int compareTo(Object o) throws ClassCastException {
	    if (!(o instanceof Action))
	      throw new ClassCastException("An Action object expected.");
	    Action otherAction = (Action) o;  
	    return otherAction.priority - this.priority;    
	  }
	
	public String toString(){
		String str = name;
		if(destX != -1 && destY != -1) str+= " ("+destX+","+destY+")";
		str+=" P: "+priority;
		return str;
	}

}
