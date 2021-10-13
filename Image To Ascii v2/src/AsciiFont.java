import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class AsciiFont {
	
	static String[] fontNames = ImageToAscii.fontNames;
	static int fontIndex = 0;
	
	int charWidth;
	int charHeight;
	Font font;
	
	public AsciiFont(int size) {
		this.font = new Font(fontNames[fontIndex], Font.PLAIN, size);
		setSizes();
	}
	
	public void setSizes(){
		FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, 
				RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
		
		this.charWidth = (int) Math.round(font.getStringBounds("m", frc).getWidth());
		this.charHeight = font.getSize();
	}
	
	public void changeFont(int change){
		fontIndex = (fontIndex+change)%fontNames.length;
		if(fontIndex < 0 ) fontIndex = fontNames.length+change;
		font = new Font(fontNames[fontIndex], Font.PLAIN, font.getSize());
	}
	
	public AsciiFont changeSize(int change) {
		font = font.deriveFont((float) font.getSize() + change);
		setSizes();
		return this;
	}
	
	public String toString(){
		return font+" width: "+charWidth+" height: "+charHeight;
	}

	
}
