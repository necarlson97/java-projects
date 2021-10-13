
public class ListDemonstration {
	
	public class Message {
		String msg;
		
		Message(String m) {
			msg = m;
		}
	}
	
	ListDemonstration() {
		Message m1 = new Message("I am message one!");
		Message m2 = new Message("I am the second message!");
		Message m3 = new Message("Hello, it's me, the third message!");
		Message m4 = new Message("Hey, I am message #4");
		Message m5 = new Message("and I am message #5");
		Message m6 = new Message("Look at me! I'm message #6");
		
		System.out.println("m1.msg = "+m1.msg);
		System.out.println("m2.msg = "+m2.msg);
		System.out.println("m3.msg = "+m3.msg);
		System.out.println("m4.msg = "+m4.msg);
		System.out.println("m5.msg = "+m5.msg);
		System.out.println("m6.msg = "+m6.msg);
	}

	public static void main(String[ ]  args) {
		new ListDemonstration();
	} 
	
} 
