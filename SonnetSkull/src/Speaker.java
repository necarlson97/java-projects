import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Speaker {
	
	String speaking = "kevin16";
	Voice voice;
	
	Speaker() {
		VoiceManager voiceManager = VoiceManager.getInstance();
		voice = voiceManager.getVoice(speaking);
		voice.allocate();
		voice.setRate(80);
	}
	
	public void speak(String s) {
		voice.speak(s);
	}

}
