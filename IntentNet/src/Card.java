import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Card {
	
	String input;
	String output;
	
	float[] inputs;
	float[] outputs;
	
	Card(String _input, String _output) {
		input = _input;
		output = _output; 
		
		inputs = processInput(input);
		outputs = processOutput(output);
	}

	Card(String _input) {
		input = _input.trim();
		
		inputs = processInput(input);
		
		System.out.println(this);
	}
	
	public static String[] split(String s) {
		s = s.toLowerCase().replaceAll("you", "ENTITY");
		System.out.println(s);
		String[] ret = s.replaceAll("[^a-zA-Z0-9 ?!.]", "").split("\\b |\\b(?=[?!.])");
		return ret;
	}
	
	private float[] processInput(String in) {
		String[] arr = split(in);
		
		float[] ret = new float[Stripper.inBag.length];
		
		for(int i=0; i<arr.length; i++) {
			int ind = bagIndex(Stripper.inBag, arr[i]);
			if(ind>=0) ret[ind]++;
		}
		
		return ret;
	}

	private float[] processOutput(String out) {
		
		float[] ret = new float[Stripper.outBag.length];
		
		int i = bagIndex(Stripper.outBag, out);
		if(i >= 0) ret[i]++;
		
		return ret;
	}
	
	private int bagIndex(String[] arr, String str) {
		
		for(int i=0; i<arr.length; i++) {
			if(str.equals(arr[i])) return i;
		}
		return -1;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(toString(), 20, 40);
	}
	
	public String toString() {
		String str = "";
		if(output != null) str += output+" ";
		
		if(inputs.length > 0) {
			str +="[";
			for(int i=0; i<inputs.length; i++) {
				if(inputs[i] > 0) str+= i+",";
			}
			str = str.substring(0, str.length()-1)+"] ";
		}
		
		
		str += input;
		return str;
	}
	
}
