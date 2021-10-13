import java.awt.Graphics2D;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;

public class Blaster implements Runnable {
	
	private String[] base;
	private String[] hashes;
	
	private MessageDigest md5;
	private MessageDigest sha1;
	private MessageDigest sha256;
	
	private LinkedList<String> heads = new LinkedList<String>(Arrays.asList(""));
	private LinkedList<String> tails = new LinkedList<String>(Arrays.asList(""));
	
	private static final int DEFAULT_PRINTBOX_SAMPLE = 10; 
	
	Thread thread;
	boolean running = true;
	
	int unit;
	Bar bar;
			
	public Blaster() {
		unit = DisplayEngine1.windowWidth/100;
		
		int barWidth = unit * 10;
		int barHeight = DisplayEngine1.windowHeight - unit * 10;
		
		int barX = DisplayEngine1.windowWidth - unit - barWidth;
		int barY = unit * 2;
		
		bar = new Bar(barX, barY, barWidth, barHeight);
		
		try {
			md5 = MessageDigest.getInstance("MD5");
			sha1 = MessageDigest.getInstance("SHA-1");
			sha256 = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
	}
	
	public void load(String name) {
		base = StringLoader.load(name);
		PrintBox.addString(baseString());
	}
	
	public void addBonusDigits(int headDigits, int tailDigits) {
		for(int i=0; i<Math.pow(10, headDigits); i++) 
			heads.add(i+"");
		for(int i=0; i<Math.pow(10, tailDigits); i++) 
			tails.add(i+"");
		PrintBox.addString("Heads: "+heads);
		PrintBox.addString("Tails: "+tails);
	}
	
	public void modifyBase() {
		LinkedList<String> newBase = new LinkedList<String>();
		for(int i=0; i<base.length; i++) {
			for(String h : heads) {
				for(String t : tails) {
					newBase.add(h+base[i]+t);
				}
			}
		}
		base = newBase.toArray(new String[newBase.size()]);
		PrintBox.addString(baseString(20));
	}
	
	public void check(String s) {
		switch(s.toLowerCase()) {
			case "md5": check(md5); break;
			case "sha1": check(sha1); break;
			case "sha256": check(sha256); break;
			default: System.out.println("No algoryth "+s+" for hash check.");
		}
		
	}
	
	private void check(MessageDigest d) {
		if(base == null) return;
		hashes = new String[base.length];
		for(int i=0; i<base.length; i++) {
			String s = base[i];
			hashes[i] = getHash(d, s);	
			bar.progress = (float)i/base.length;
		}
		PrintBox.addString(hashesString(DEFAULT_PRINTBOX_SAMPLE));
	}
	
	private String getHash(MessageDigest d, String s) {
		try {
			byte[] bytes = s.getBytes("UTF-8");
			byte[] digest = d.digest(bytes);
			return bytesToString(digest);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "ERROR UNSUPPORTED ENCODING";
		}
		
	}

	private String bytesToString(byte[] hash) {
		StringBuilder sb = new StringBuilder(2*hash.length);
        for(byte b : hash){
            sb.append(String.format("%02x", b&0xff));
        }
		return sb.toString();
	}
	
	public String toString() {
		return toString(DEFAULT_PRINTBOX_SAMPLE);
	}

	private String toString(int sample) {
		String s = "";
		
		s+=baseString(sample);
		s+=hashesString(sample);
		
		return s;
	}
	
	String baseString() {
		return baseString(DEFAULT_PRINTBOX_SAMPLE);
	}
	
	private String baseString(int sample) {
		String s = "";
		if(base != null) {
			s+= "Base Size: "+base.length+"\n";
			for(int i=0; i<sample; i++) {
				s += "\ti:"+i+" "+base[i]+"\n";
			}
		}
		return s;
	}
	
	String hashesString() {
		return hashesString(DEFAULT_PRINTBOX_SAMPLE);
	}
	
	private String hashesString(int sample) {
		String s = "";
		if(hashes != null) {
			s+= "Hashes Size: "+hashes.length+"\n";
			for(int i=0; i<sample; i++) {
				s += "\ti:"+i+" "+hashes[i]+"\n";
			}
		}
		return s;
	}

	@Override
	public void run() {
		
	}

	public void render(Graphics2D g) {
		if(bar != null) bar.render(g);
	}

}
