import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.mifmif.common.regex.Generex;

public class Blaster implements Runnable{
	
	String[] ammo;
	String[] dict;

	Display display;
	
	int stage = 0;
	Thread thread;
	boolean running = true;
	
	static Generex g;
	
	static int DEFAULT_MODE = 2;
	static int DEFAULT_LENGTH = 8;
	
	LinkedList<String> hashes;
	LinkedList<String> cracked = new LinkedList<String>();
	
	public Blaster(Display d) {
		display = d;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while(running) {
			
		}
	}
	
	/* Modes:
	 * 	1 - Upper and lower case
	 *  2 - Upper, lower, digit
	 *  3 - Upper, lower, digit, special
	 */
	void tryPasswords() {
		tryPasswords(DEFAULT_MODE, DEFAULT_LENGTH, "");
	}
	void tryPasswords(int mode) {
		tryPasswords(mode, DEFAULT_LENGTH, "");
	}
	void tryPasswords(int mode, String base) {
		tryPasswords(mode, DEFAULT_LENGTH, base);
	}
	void tryPasswords(String base) {
		tryPasswords(DEFAULT_MODE, DEFAULT_LENGTH, base);
	}
	void tryPasswords(int mode, int length, String base) {
		if(base.length() > length) base = base.substring(0, length);
		
		System.out.println("Getting passwords: "+mode+", "+length+", "+base);
		
		String alpha = "A-Za-z";
		String bonus = "";
		if(mode >= 2) bonus += "0-9";
		if(mode >= 3) bonus += "#?!@$%^&*-";
		
		
		for(int baseIndex=0; baseIndex<=length-base.length(); baseIndex++) {
			for(int bonusIndex=0; bonusIndex<base.length(); bonusIndex++) {
				for(int capitalMode=0; capitalMode<3; capitalMode++) {
					String mask = "";	
					for(int letter=0; letter<=length; letter++) {
						if(letter >= baseIndex && letter < baseIndex + base.length()) {
							for(char c : base.toCharArray()) {
								String l = (c+"");
								if(capitalMode == 0) l.toLowerCase();
								if(capitalMode == 1) l.toUpperCase();
								if(capitalMode == 2) {
									if(letter == baseIndex) l.toUpperCase();
									else l.toLowerCase();
								}
								mask+="["+l;
								if(letter == baseIndex+bonusIndex) mask+=bonus;
								mask+="]";
								letter++;
							}
						}
						else mask += "["+alpha+bonus+"]";
					}
					tryMaskCrack(mask);
				}	
			}
		}
	}
	
	public void tryMaskCrack(String mask) {
		System.out.println("Mask: "+mask);
		g = new Generex(mask);
		int i=0;
		while(running) {
			System.out.print("i: "+i+" ");
			try {
				String p = g.getMatchedString(i);
				tryCrack(p);
			} catch(StringIndexOutOfBoundsException e) {
				System.out.println("Done");
				return;
			}
			i++;
		}
	}
	
	public String hash(String s) {
		return s;
	}
	
	public void tryCrack(Collection<String> c) {
		for(String s : c)
			tryCrack(s);
	}
	
	boolean tryCrack(String s) {
		System.out.println("Trying: "+s);
		if(hashes == null || !hashes.contains(hash(s))) return false;
		System.out.println("Cracked: "+s);
		cracked.add(s);
		return true;
	}
	
	void brute() {
		tryPasswords();
	}
	
	public void startStage(int s) {
		stage = s;
		startStage();
	}
	
	public void startStage() {
		switch(stage) {
		case 0:
			if(ammo == null) return;
			for(String s : ammo)
				tryPasswords(s);
			stage++;
			break;
			
		case 1:
			if(dict == null) return;
			tryCrack(Arrays.asList(dict));
			stage++;
			break;
			
		case 2:
			stage++;
			brute();
			break;
			
		default:
			break;
		}
	}
	
	public void setAmmo(String input) {
		String[] spl = input.split(",");
		for(int i=0; i<spl.length; i++)
			spl[i] = spl[i].trim();
			
		ammo = spl;
	}
	
	

}
