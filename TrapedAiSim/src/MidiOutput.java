// Putting using midi on hold, I like the versatility, 
// but it might be too much to tackle if wav files proves simple



import java.util.Random;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class MidiOutput implements Runnable{
	
	private Thread midiThread;
	private Random r = new Random();
	private Synthesizer synth;
    
    private Thought thought;
    
    MidiChannel[] channels ;
    private Instrument[] instr;
    private Sequence[] idleSounds;
    private int dur = 10;
	
	public MidiOutput(){
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
		instr = synth.getDefaultSoundbank().getInstruments();
		channels = synth.getChannels();
		
		Soundbank soundbank = synth.getDefaultSoundbank();
	    synth.loadAllInstruments(soundbank);
	    
	
		channels[1].programChange(4); // Electric Piano
		channels[2].programChange(14); // Xlylophone
		channels[3].programChange(34); // Electric Bass
		channels[4].programChange(123); // Bird Tweet
		
		midiThread=new Thread(this);
        midiThread.start();
		
	}
	
	public void speak(Thought t){
		thought = t;
	}


	public void run() {
		
		int rand = r.nextInt(idleSounds.length);
		if(thought!= null){
			if(thought.getName().equals("idle")){
				channels[1].noteOn( 60, 50 );
			}
			else if(thought.getName().equals("watched")){
				channels[1].noteOn( 68, 70 );
			}
			else if(thought.getName().equals("touched")){
				channels[3].noteOn( 43, 100 );
			}
			else if(thought.getName().equals("confused")){
				channels[4].noteOn( 53, 50 );
			}
			thought = null;
			
//			try {
//				midiThread.sleep(dur);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}	
			//channels[4].allNotesOff();
		}
		else {
			channels[4].allNotesOff();
			try {
				midiThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
		run();
	}
}
