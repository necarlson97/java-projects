import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Blaster implements Runnable {
	
	private int batchSize = 0;
	private LinkedList<String> batch = new LinkedList<String>();
	private LinkedList<String> cracked = new LinkedList<String>();
	private LinkedList<String> hashes = new LinkedList<String>();
	
	private static String[] algNames = {"MD5", "SHA-1", "SHA-256"};
	private MessageDigest[] algs = new MessageDigest[algNames.length];
	private int algI = 0;
	
	private static final int SAMPLE = 10; 
	
	Thread thread;
	boolean running = true;
	volatile boolean hashing = false;
	
	FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
	JFileChooser fc;
	
	Bar bar = new Bar();
	Display display;
			
	public Blaster(Display d) {
		display = d;
		
		String home = System.getProperty("user.home");
		fc = new JFileChooser(new File(home+"/Downloads/"));
		fc.setFileFilter(filter);
		
		try {
			for(int i=0; i<algs.length; i++)
				algs[i] = MessageDigest.getInstance(algNames[i]);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
	}
	
	public void loadBatch() {
		fc.setDialogTitle("Open Password Batch");
		fc.showOpenDialog(display);
		File file = fc.getSelectedFile();
		int added = 0;
		if(file != null) {
			added = addToList(file, batch);
			PrintBox.addString("Added "+added+" batch from: "+file.getName());
			batchSize = batch.size();
		}
	}

	public void loadHashes() {
		fc.setDialogTitle("Open Hash");
		fc.showOpenDialog(display);
		File file = fc.getSelectedFile();
		int added = 0;
		if(file != null) {
			added = addToList(file, hashes);
			PrintBox.addString("Added "+added+" hashes from: "+file.getName());
		}
		
	}
	
	public void tryHash() {
		fc.setDialogTitle("Open Passwords To Hash");
		fc.showOpenDialog(display);
		File file = fc.getSelectedFile();
		if(file != null) {
			toHash(file);
			PrintBox.addString("Done");
		}
		
	}

	public int addToList(File toAdd, LinkedList<String> list) {
		Scanner scan = null;
		int size = 0;
		try {
			scan = new Scanner(new FileReader(toAdd));
			LineNumberReader  lnr = new LineNumberReader(new FileReader(toAdd));
			lnr.skip(Long.MAX_VALUE);
			size += lnr.getLineNumber();
			lnr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int i=0;
		while(scan.hasNext()) {
			list.add(scan.nextLine());
			i++;
			bar.progress = (float)i/size;
		}
		
		return size;
	}
	
	public void changeAlg() {
		algI = (algI+1)%algs.length;
	}
	
	private void next() {
		String pass = batch.removeFirst();
		String hash = getHash(algs[algI], pass);
		PrintBox.addString(hash);
		if(hashes.remove(hash)) cracked.add(pass);
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
	
	private void toHash(File file) {
		PrintWriter writer = null;
		Scanner scan = null;
		try{
		    writer = new PrintWriter(file.getName()+"_hash.txt", "UTF-8");
		    scan = new Scanner(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(scan.hasNext()) {
			writer.println(getHash(algs[algI], scan.nextLine()));
		}
		
	    writer.close();
	}

	@Override
	public void run() {
		while(running) {
			if(hashing && hasNext()) next(); 
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
		g.drawString("Algorythm: "+algNames[algI], x, y);
		y+=20;
		g.drawString("Hashing: "+hashing, x, y);
		
	}

}
