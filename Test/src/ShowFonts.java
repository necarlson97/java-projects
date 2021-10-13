import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShowFonts extends JPanel {
 
  public static void main(String[] a){
    JFrame f = new JFrame();
    f.setSize(900,50000);
    f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    f.setVisible(true);
    f.add(new ShowFonts());
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
  }
  
  public void paint(Graphics g) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    Font[] allFonts = ge.getAllFonts();
    
    int x = 0;
    int y = 0;

    for (int i = 0; i < allFonts.length; i++) {
      Font f = allFonts[i].deriveFont(10.0f);
      g.setFont(f);

      g.setColor(Color.black);
      g.drawString(f.getFontName(), 10+x, 20+y);
      
      y+=20;
      if(y >= 700){
    	  y=0;
    	  x += 200;
      }
      
      

    }
  }
}