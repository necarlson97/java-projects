import java.util.Arrays;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument.Iterator;

public class KeyArray{
	
	int size = 500;
	Note[] notes = new Note[size];
	int key;
	int i = 0;
	
	public KeyArray(int k) {
		key = k;
	}
	
	public void add(Note n) {
		i = (i+1) % size;
		notes[i] = n;
	}
	
	public Note getRecent() {
		return notes[i];
	}

}
