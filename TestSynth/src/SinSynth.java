import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SinSynth {

   protected static final int SAMPLE_RATE = 16 * 1024;   
  

public static void main(String[] args) throws LineUnavailableException {
       final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
       SourceDataLine line1 = AudioSystem.getSourceDataLine(af);
       SourceDataLine line2 = AudioSystem.getSourceDataLine(af);
       line1.open(af, SAMPLE_RATE);
       line1.start();
       
       line2.open(af, SAMPLE_RATE);
       line2.start();
       
       Random r = new Random();
       
       System.out.println("a");
       FrameHandler frame = new FrameHandler();
       System.out.println("b");
       
       for(int h = 0; h<0; h++){
    	   for(int i = 0; i<7; i++){
        	   for(int j = 0; j<500; j++){
        		   //if(r.nextInt(5) == 0) line1.write(OscHandler.getSine(200,10*i), 0, 10*i);
        		   //if(r.nextInt(5) == 1) line1.write(getRandom(10*i), 0, 10*i);
        		   if(r.nextInt(5) == 2) line2.write(OscHandler.getSaw(10*i), 0, 10*i);
        		   //if(r.nextInt(5) == 3) line1.write(getTriangle(10*i), 0, 10*i);
        		   //if(r.nextInt(5) == 4) line1.write(getSquare(10*i), 0, 10*i);
        	   }
           }
       }
     
       
       line1.drain();
       line1.close();

       line2.drain();
       line2.close();
    }

}