/**   The driver class that instantiates the DisplayWindow
 *    and SaucerPanel, adds the panel to the window, then
 *    makes the call to showFrame to display the panel.
*/
public class SaucerDriver{

  public static void main(String[] args){
    DisplayWindow d = new DisplayWindow("Saucer");
    SaucerPanel p = new SaucerPanel();
    d.addPanel(p);
    d.showFrame();
  }
}