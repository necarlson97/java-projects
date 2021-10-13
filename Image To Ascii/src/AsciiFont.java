import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class AsciiFont {
	
	int charWidth;
	int charHeight;
	Font font;
	
	public AsciiFont(Font font, double charWidth){
		this.font = font;
		this.charWidth = (int) Math.round(charWidth);
		this.charHeight = font.getSize();
	}
	
	public AsciiFont(Font font) {
		FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, 
				RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
		this.font = font;
		this.charWidth = (int) Math.round(font.getStringBounds("m", frc).getWidth());
		this.charHeight = font.getSize();
	}

	
}
