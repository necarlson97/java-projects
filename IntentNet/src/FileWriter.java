import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileWriter {

	private static String folder = ""; 

	static void saveNetwork(Network net) {
		String s = netToString(net);
		writeFile("Net: "+System.currentTimeMillis(), s);
	}

	// Formula for network is:
	// 	tried, success
	// 	Formula for each layer is:
	// 		Layer length
	// 		Previous Layer Length (weights length)
	// 		Formula for each node in layer
	//			output, error, x, y, weights
	private static String netToString(Network net) {
		String s = "";
		// Network info
		s += net.tried+",";
		s += net.success+",";

		// Input Layer
		s += net.inputLayer.length+",";
		s += 0+",";
		for(Neuron n : net.inputLayer) {
			s += n.output+",";
			s += n.error+",";
			s += n.x+",";
			s += n.y+",";
		}

		// First Hidden Layer
		Neuron[] l = net.hiddenLayers[0];
		s += l.length+",";
		s += net.inputLayer.length+",";
		for(Neuron n : l) {
			s += n.output+",";
			s += n.error+",";
			s += n.x+",";
			s += n.y+",";
			for(float f : n.weights)
				s += f+",";
		}
		// Other Hidden Layers
		for(int i=1; i<net.hiddenLayers.length; i++) {
			l = net.hiddenLayers[i];
			s += l.length+",";
			s += net.hiddenLayers[i-1].length+",";
			for(Neuron n : l) {
				s += n.output+",";
				s += n.error+",";
				s += n.x+",";
				s += n.y+",";
				for(float f : n.weights)
					s += f+",";
			}
		}
		// Output Layer
		s += net.outputLayer.length+",";
		s += l.length+",";
		for(Neuron n : net.outputLayer) {
			s += n.output+",";
			s += n.error+",";
			s += n.x+",";
			s += n.y+",";
			for(float f : n.weights)
				s += f+",";
		}
	
		return s.substring(0, s.length()-1);
	}

	public static void writeFile(String filename, String contents){

		try {
			PrintWriter writer = new PrintWriter(folder+filename+".txt", "UTF-8");
			writer.println(contents);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	static Network loadNetwork(Network n) {
		LinkedList<File> nets = new LinkedList<File>();

		File[] files = new File(".").listFiles();

		for (File file : files) {
			if (file.isFile()) {
				String name = file.getName();
				if(name.contains("Net")) 
					nets.add(file);
			}
		}

		String s = readFile(nets.getLast());
		Network net = stringToNet(s, n);

		return net;
	}

	private static Network stringToNet(String str, Network n) {
		String[] s = str.split(",");
		
		int tried = Integer.parseInt(s[0]);
		int success = Integer.parseInt(s[1]);

		List<Neuron[]> layers = stringToLayers(s);
		Neuron[] inputLayer = layers.remove(0);
		Neuron[] outputLayer = layers.remove(layers.size()-1);
		Neuron[][] hiddenLayers = layers.toArray(new Neuron[layers.size()][]);

		Network net = new Network(n.driver, tried, success, inputLayer, hiddenLayers, outputLayer);

		return net;
	}

	private static List<Neuron[]> stringToLayers(String[] s) {
		List<Neuron[]> layers = new LinkedList<Neuron[]>();
		int i=2;
		Neuron[] l;
		Neuron[] prevL = null;
		while(i < s.length) {
			l = new Neuron[Integer.parseInt(s[i])]; i++;
			int prevLength = Integer.parseInt(s[i]); i++;

			int j=0;
			while(j < l.length) {
				float output = Float.parseFloat(s[i]); i++;
				float error = Float.parseFloat(s[i]); i++;
				int x = Integer.parseInt(s[i]); i++;
				int y = Integer.parseInt(s[i]); i++;

				String[] weightStrs = Arrays.copyOfRange(s, i, i+prevLength);
				float[] weights = new float[weightStrs.length];
				for(int w=0; w<weightStrs.length; w++)
					weights[w] = Float.parseFloat(weightStrs[w]);
				i+=weights.length;

				l[j] = new Neuron(prevL, weights, output, error, x, y);
				j++;
			}
			prevL = l;
			layers.add(l);
		}

		return layers;
	}

	public static String readFile(File f){
		String s = "";
		try {
			byte[] b = Files.readAllBytes(f.toPath());
			s = new String(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

}
