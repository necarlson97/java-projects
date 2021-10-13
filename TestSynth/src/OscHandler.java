import java.util.Random;

public class OscHandler {

	public static byte[] getSine(float _hz, int period) {

		byte[] sine = new byte[period];
		for (int b = 0; b < period; b++) {
			float fPeriodPosition = (float) b / (float) period;
			float fValue;
			fValue = (float) Math.sin(fPeriodPosition * 2.0 * Math.PI);
			int nValue = Math.round(fValue * 100);
			sine[b] = (byte) (nValue & 0xFF);
		}
		return sine;
	}

	public static byte[] getRandom(int period) {
		Random rand = new Random();
		byte noise[] = new byte[period];
		for (int b = 0; b < period; b++) {
			noise[b] = (byte) (rand.nextInt(255) - 127);
		}
		return noise;
	}

	public static byte[] getSaw(int period) {
		byte sawtooth[] = new byte[period];

		for (int b = 0; b < period; b++) {
			float fPeriodPosition = (float) b / (float) period;
			float fValue;
			if (fPeriodPosition < 0.5F) {
				fValue = 2.0F * fPeriodPosition;
			} else {
				fValue = 2.0F * (fPeriodPosition - 1.0F);
			}
			int nValue = Math.round(fValue * 100);
			sawtooth[b] = (byte) (nValue & 0xFF);
		}
		return sawtooth;
	}

	public static byte[] getSquare(int period) {
		byte square[] = new byte[period];
		int counter = 127;
		for (int b = 0; b < period; b++) {
			if (b < period / 2)
				square[b] = (byte) -127;
			else
				square[b] = (byte) 127;
		}
		return square;
	}

	public static byte[] getTriangle(int period) {
		byte triangle[] = new byte[period];
		for (int b = 0; b < period; b++) {
			float fPeriodPosition = (float) b / (float) period;
			float fValue;
			if (fPeriodPosition < 0.25F) {
				fValue = 4.0F * fPeriodPosition;
			} else if (fPeriodPosition < 0.75F) {
				fValue = -4.0F * (fPeriodPosition - 0.5F);
			} else {
				fValue = 4.0F * (fPeriodPosition - 1.0F);
			}
			int nValue = Math.round(fValue * 100);
			triangle[b] = (byte) (nValue & 0xFF);
		}
		return triangle;
	}

}
