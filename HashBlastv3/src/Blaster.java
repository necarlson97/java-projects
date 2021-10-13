import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Blaster implements Runnable {
	
	private int batchSize = 0;
	private LinkedList<String> batch = new LinkedList<String>();
	private LinkedList<String> cracked = new LinkedList<String>();
	private LinkedList<String> hashes = new LinkedList<String>();
	
	private MessageDigest md5;
	private MessageDigest sha1;
	private MessageDigest sha256;
	
	private MessageDigest algorythm;
	
	private LinkedList<String> heads = new LinkedList<String>(Arrays.asList(""));
	private LinkedList<String> tails = new LinkedList<String>(Arrays.asList(""));
	
	private static final int SAMPLE = 10; 
	
	Thread thread;
	boolean running = true;
	volatile boolean hashing = false;
	
	Bar bar = new Bar();
			
	public Blaster() {
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
	
	public void addToBatch(String name) {
		Scanner scan = null;
		int size = 1;
		try {
			scan = new Scanner(new FileReader(name+".txt"));
			LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(name+".txt")));
			lnr.skip(Long.MAX_VALUE);
			size = lnr.getLineNumber() + 1;
			lnr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		size *= heads.size() * tails.size();
		
		int i=0;
		while(scan.hasNext()) {
			String s = scan.nextLine();
			for(String h : heads) {
				for(String t : tails) {
					batch.add(h+s+t);
					i++;
				}
			}
			
			bar.progress = (float)i/size;
		}
		batchSize = batch.size();
		PrintBox.addString("Batch Size: "+batchSize);
	}

	public void addToHashes(String name) {
		Scanner scan = null;
		int size = 1;
		try {
			scan = new Scanner(new FileReader(name+".txt"));
			LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(name+".txt")));
			lnr.skip(Long.MAX_VALUE);
			size = lnr.getLineNumber() + 1;
			lnr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int i=0;
		while(scan.hasNext()) {
			hashes.add(scan.nextLine());
			bar.progress = (float)i/size;
		}
		PrintBox.addString("Hashes Size: "+hashes.size());
	}
	
	public void bonusDigits(int headDigits, int tailDigits) {
		for(int i=0; i<Math.pow(10, headDigits); i++) 
			heads.add(i+"");
		for(int i=0; i<Math.pow(10, tailDigits); i++) 
			tails.add(i+"");
		PrintBox.addString("Heads: "+heads);
		PrintBox.addString("Tails: "+tails);
	}
	
	public void setAlgorythm(String s) {
		switch(s.toLowerCase()) {
			case "md5": algorythm = md5; break;
			case "sha1": algorythm = sha1; break;
			case "sha256": algorythm = sha256; break;
			default: System.out.println("No algoryth "+s+" for hash check.");
		}
		PrintBox.addString("Algorythm: "+algorythm);
	}
	
	private void next() {
		String pass = batch.removeFirst();
		String hash = getHash(algorythm, pass);
		//if(hashes.remove(hash)) cracked.add(pass);
		if(hashes.contains(hash)) cracked.add(pass);
		PrintBox.addString(hash);
		bar.progress = (float)(batchSize-batch.size())/batchSize;
	}
	
	private boolean hasNext() {
		return !batch.isEmpty();
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
		String str = batchSample(SAMPLE);
		return str + crackedSample(SAMPLE);
	}
	
	private String batchSample(int sample) {
		String s = "";
		s+= "Batch Size: "+batch.size()+"\n";
		for(int i=0; i<sample; i++) {
			if(i >= batch.size()) return s;
			s += "\ti:"+i+" "+batch.get(i)+"\n";
		}
		return s;
	}
	
	
	private String crackedSample(int sample) {
		String s = "";
		s+= "Cracked Size: "+cracked.size()+"\n";
		for(int i=0; i<sample; i++) {
			if(i >= cracked.size()) return s;
			s += "\ti:"+i+" "+cracked.get(i)+"\n";
		}
		return s;
	}

	@Override
	public void run() {
		while(running) {
			if(hashing && hasNext() && algorythm != null) next(); 
		}
	}

	public void render(Graphics2D g) {
		if(bar != null) bar.render(g);
		
		int x = 750;
		int y = 30;
		g.setColor(Color.black);
		g.fillRect(x, y, 100, 500);
		
		g.setColor(Color.white);
		
		y+=20;
		g.drawString("Hashes: "+hashes.size(), x, y);
		y+=20;
		g.drawString("Batch: "+batch.size()+"/"+batchSize, x, y);
		y+=20;
		g.drawString("Cracked: "+cracked.size()+"/"+batchSize, x, y);
		y+=20;
		g.drawString("Algorythm: "+algorythm, x, y);
		y+=20;
		g.drawString("Hashing: "+hashing, x, y);
		
	}

}
