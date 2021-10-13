import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class MidiTest {
	
	public static void main(String[] args) throws MidiUnavailableException, InterruptedException{
		Synthesizer synth = MidiSystem.getSynthesizer();
	    synth.open();
	    
	    int volume = 50; // between 0 et 127
	    int duration = 100; // in milliseconds
	    int channel = 1;
		
	    Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
	    MidiChannel[] channels = synth.getChannels();
	    
	    Soundbank soundbank = synth.getDefaultSoundbank();
	    synth.loadAllInstruments(soundbank);
	    
		for(int i=0; i<128; i++){	
			channels[channel].programChange(i);
			System.out.println(instr[i]+", i: "+i);
			
		    channels[channel].noteOn( 60, volume ); // C note
	        Thread.sleep( duration );
	        channels[channel].noteOff( 60 );

		}	
	}
}
