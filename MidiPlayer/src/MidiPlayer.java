import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

public class MidiPlayer implements Receiver{
	
	Sequencer seq;
	InputStream stream;
	Transmitter trans;
	
	KeyArray[] keys = new KeyArray[128];
	Particle[] dots = new Particle[128];
	
	String fileName;
	
	public MidiPlayer(String fn) {
		fileName = fn;
		sequencerSetup();
	}
	
	private void sequencerSetup() {
		try {
			seq = MidiSystem.getSequencer();
			seq.open();
			stream = new BufferedInputStream(new FileInputStream(new File(fileName)));
			seq.setSequence(stream);
			trans = seq.getTransmitter();
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			System.out.println("Failed to initiate sequencer.");
		} catch (IOException e) {
			System.out.println("Failed to open file '"+fileName+"'.");
		}
		trans.setReceiver(this);
		seq.start();	
	    
	}
	
	@Override
	public void send(MidiMessage message, long timeStamp) {
	    if(message instanceof ShortMessage) {
	        ShortMessage sm = (ShortMessage) message;
	        int channel = sm.getChannel();
	        

	        if (sm.getCommand() == sm.NOTE_ON) {

	            int key = sm.getData1();
	            int velocity = sm.getData2();
	            
	            if(keys[key] == null) keys[key] = new KeyArray(key);
	            
	            Note n = keys[key].getRecent();
	            
	            if(n == null) addNote(new Note(key, velocity, channel));
	            else {
	            		n.velocity = 0;
	            		if(velocity > 0) addNote(new Note(key, velocity, channel));
	            }
	            

	        } else {
	        	
	        }
	    }
	}
	
	void addNote(Note n) {
		keys[n.key].add(n);
		dots[n.key] = new Particle(n);
	}


	void render(Graphics g) {
		
		for(int k=0; k<128; k++) {
			if(keys[k] != null) {
				for(Note n : keys[k].notes) {
					if(n != null) n.render(g);
				}
			}
			if(dots[k] != null) dots[k].render(g);
		}
			
	}

	@Override
	public void close() {
		seq.stop();
		trans.close();
	}
	
	public void screenResize() {
		for(KeyArray k : keys) {
			for(Note n : k.notes) {
				n.width = Game.windowWidth/128;
			}
		}
		for(Particle p : dots) {
			p.size = Game.windowWidth/128;
		}
	}

}
