import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

public class Recognizer {
	
	SpeechClient speech;
	String fileName = "audio.wav";
	
	String clientID = "";
	String clientSecret = "";

	Recognizer() {

		try {
			speech = SpeechClient.create();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public String recognize() {

		// Reads the audio file into memory
		Path path = Paths.get(fileName);
		byte[] data;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		ByteString audioBytes = ByteString.copyFrom(data);

		// Builds the sync recognize request
		RecognitionConfig config = RecognitionConfig.newBuilder()
				.setEncoding(AudioEncoding.LINEAR16)
				.setSampleRateHertz(16000)
				.setLanguageCode("en-US")
				.build();
		RecognitionAudio audio = RecognitionAudio.newBuilder()
				.setContent(audioBytes)
				.build();

		// Performs speech recognition on the audio file
		RecognizeResponse response = speech.recognize(config, audio);
		List<SpeechRecognitionResult> results = response.getResultsList();
		
		String ret = "";
		for (SpeechRecognitionResult result: results) {
			// There can be several alternative transcripts for a given chunk of speech. Just use the
			// first (most likely) one here.
			SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
			ret += alternative.getTranscript();
		}
		try {
			speech.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

}
