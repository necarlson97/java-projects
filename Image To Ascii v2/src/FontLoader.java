import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class FontLoader {
	
	String testString = "$$$$$$$$$$@@@@@@BBBB%%%8&&WM#*oahkbdpqwmZO0QLCJUY"
			+ "Xzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"\"^^^^``````"
			+ "''''''''...........                    "
			+ "the quick brown fox jumped over the lazy dog";

	public FontLoader(){
		
	}
	
	public String[] getAvailableFontNames(){
		
		LinkedList<String> availableFontNames = new LinkedList<String>();
		
		FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, 
				RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] allFonts = e.getAllFonts();
	    Rectangle2D monoBounds = new Font("AndaleMono", Font.PLAIN, 20).getStringBounds(testString, frc);
	    double monoWidth = monoBounds.getWidth();
	    
	    for (Font f : allFonts) {
	    	Font resized = f.deriveFont(20f);
	    	Rectangle2D iBounds = resized.getStringBounds("i", frc);
	        Rectangle2D mBounds = resized.getStringBounds("m", frc);
	        Rectangle2D fullBounds = resized.getStringBounds(testString, frc);
	        if (iBounds.getWidth() == mBounds.getWidth() && fullBounds.getWidth() == monoWidth) {
	        	availableFontNames.add(f.getName());
	        }
	    }
	   
	    String[] toArray = (String[]) availableFontNames.toArray(new String[availableFontNames.size()]);
	    return toArray;
	}

}
