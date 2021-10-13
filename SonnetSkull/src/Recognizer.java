import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class Recognizer {

	Configuration configuration;
	StreamSpeechRecognizer recognizer;

	Recognizer() {
		configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		
		try {
			recognizer = new StreamSpeechRecognizer(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	public String read(File f) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String ret = "";
		recognizer.startRecognition(stream);
		SpeechResult result;
		while ((result = recognizer.getResult()) != null) {
			ret+=" "+result.getHypothesis();
		}
		recognizer.stopRecognition();
		
		ret = WordsToInt.translate(ret);
		
		return ret.toLowerCase().trim();
	}
}
