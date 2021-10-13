import java.io.Console;

public class Driver {
	
	static Terminal t;
	static Network n;
	
	Thread thread;
	
	Driver() {
		t = new Terminal(this);
		n = FileWriter.loadNetwork(this);
	}
	
	void respond(String s) {
		Result r = n.tryCard(new Card(s));
		t.printlnSlow(r.type, t.botColor);
	}

	public static void main(String[] args) {
		new Driver();
	}

}
