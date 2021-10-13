
public class Sonnet {
	
	int index;
	String roman;
	String poem;

	public Sonnet(int _index, String _roman, String _poem) {
		index = _index;
		roman = _roman;
		poem = _poem;
	}
	
	public String toString() {
		String s = "Index: "+index;
		s += "\nRoman: "+roman;
		s += "\nPoem: "+poem+"\n";
		return s;
	}

}
