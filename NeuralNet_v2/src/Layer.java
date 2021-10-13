import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;

public class Layer {
	Random r = new Random();
	int size;
	float[] weights;
	float[] values;
	int depth = 0;
	int drawWidth = 10;
	int drawHeight = 15;
	int x = 10;
	int bias = 1;
	float biasWeight = (r.nextFloat()*2)-1;
	
	boolean flash = false;
	
	public Layer(int size, int depth, int drawWidth, int drawHeight){
		weights = randomWeights(new float[size]);
		values = new float[size];
		x+=depth*(drawWidth*2);
		this.size = size;
		this.depth = depth;
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
	}
	
	public float[] randomWeights(float[] array){
		for(int i=0 ;i<array.length; i++){
			array[i] = (r.nextFloat()*2)-1; // Random float between -1 and 1
		}
		
		return array;
	}
	
	public float[] feedForward(float[] inputs){
		for(int i=0; i<size; i++){
			for(int j=0; j<inputs.length; j++){
				values[i] += inputs[j] * weights[i];
			}	
			values[i] += bias * biasWeight;
		}
		return values;
	}
	
	public float sum(float[] inputs){
		float sum = 0;
		for(float f : inputs)
			sum += f;
		return sum;
	}
	
	public float sum(){
		float[] inputs = values;
		float sum = 0;
		for(float f : inputs)
			sum += f;
		return sum;
	}
	
	public void render(Graphics g){
		int y = 10;
		
		for(int i=0; i<size; i++){
			float w = weights[i];
			float red, grn, blu;
			
			red = grn = blu = 0;
			if(w<0) red = w*-1;
			else if(w==0) blu = 1;
			else grn = w;
			
			g.setColor(new Color(red, grn, blu).brighter().brighter());
			if(flash) g.fillOval(x-10, y-10, drawWidth+20, drawHeight+20);
			g.fillOval(x-5, y-5, drawWidth+10, drawHeight+10);
			
			red = grn = blu = 0;
			if(values[i]<0) red = values[i]*-1;
			else if(values[i]==0) blu = 1;
			else grn = values[i];
			
			//g.setColor(new Color(1-red, 1-red, 1-red));
			g.setColor(Color.white);
			g.fillOval(x, y, drawWidth, drawHeight);
			
			//g.setColor(new Color(red, grn, blu));
			g.setColor(Color.black);
			String drawValue = "0";
			if(String.valueOf(values[i]).length() > 4) drawValue = String.valueOf(values[i]).substring(0, 4);
			g.drawString(drawValue, x+drawWidth/4, y+drawHeight/2);
			
			y+=drawHeight*2;
		}
		flash = false;
	}	
	
	public String toString(){
		return "Depth: "+depth+"\nWeights: "+Arrays.toString(weights)
			+"\nValues: "+Arrays.toString(values);
	}

}
