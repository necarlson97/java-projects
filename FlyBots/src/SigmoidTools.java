
public class SigmoidTools {
	
	static int tableSize = 200;
	
	static boolean isSetup = false;
	static float[] sigmoids = new float[tableSize];
	
	static float sigmoid(float x) {
		if(!isSetup) setup();
		return (float) (1/( 1 + Math.pow(Math.E,(-1*x))));
	}

	private static void setup() {
		for (int i = 0; i < sigmoids.length; i++) {
			float x = ((float)i / (tableSize/10)) - 5;
		    sigmoids[i] = calculateSigmoid(x);
		}
		isSetup = true;
	}
	
	private static float calculateSigmoid(float x) {
		return (float) (1/( 1 + Math.pow(Math.E,(-1*x))));
	}
}
