import java.awt.Color;
import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

public class DateSidebar extends JournalTextWindow{
	
	static Date date = new Date();
	static DateFormat formatter;
	static Calendar calendar;
	
	static int dateStart = -10;
	static String selectedDate;
	
	static LinkedList<String> existingEntries = FileLoader.getExistingEntries();
		
	public DateSidebar() {
		
		formatter = new SimpleDateFormat("dd MMM yyyy");
		calendar = Calendar.getInstance(); 
		
		calendar.setTime(date);
		
		formatter.setTimeZone(TimeZone.getTimeZone("EST")); 
		
	}

	@Override
	public void run() {
		super.run();
		
	}
	
	public void resetDate() {
		calendar.setTime(date); 
		calendar.add(Calendar.DATE, dateStart+10);
	}
	
	public void centerDate() {
		dateStart = -10;
		calendar.setTime(date); 
		calendar.add(Calendar.DATE, dateStart+10);
		Journal.entry.save();
		Journal.entry = new Entry(selectedDateName());
		existingEntries = FileLoader.getExistingEntries();
	}
	
	public void changeDate(int change) {
		dateStart += change;
		Journal.entry.save();
		Journal.entry = new Entry(selectedDateName());
		existingEntries = FileLoader.getExistingEntries();
	}
	
	public String selectedDateName(){
		calendar.setTime(date); 
		calendar.add(Calendar.DATE, dateStart+10);
		return formatter.format(calendar.getTime());
	}

	@Override
	public void render(Graphics g) {

		int dateNumb = (windowHeight - margin) / (headerPointSize + margin);
		
		g.setFont(headerFont);
		g.setColor(offWhite.darker());
		
		calendar.setTime(date); 
		calendar.add(Calendar.DATE, dateStart);
		
		int intY = margin*3;
		for(int i=0; i<dateNumb; i++) {
			if(i==0 || i==dateNumb-1)  g.setColor(offWhite.darker().darker().darker());
			else if(i==1 || i==dateNumb-2)  g.setColor(offWhite.darker().darker());
			else g.setColor(offWhite.darker());
			
			String dateName = formatter.format(calendar.getTime());
			
			g.drawString(dateName, margin, intY);
			
			if(existingEntries.contains(dateName)) {
				g.setColor(getDayColor().darker());
				g.drawString(dateName.substring(0, 2), margin, intY);
			}
			
			if(calendar.getTime().equals(date)) {
				g.setColor(getDayColor());
				g.drawString(dateName, margin, intY);
			}
			
			if(i == 10) {
				g.setColor(getDayColor());
				g.fillRect(0, intY-headerPointSize-margin/2, partitionX, headerPointSize + margin);
				g.setColor(Color.BLACK);
				g.drawString(dateName, margin, intY);
			}
				
			calendar.add(Calendar.DATE, 1);
			intY += headerPointSize+margin;
		}

	}
	
	public Color getDayColor() {
		float hue = (float) ((calendar.get(Calendar.DAY_OF_YEAR)+180)%360) / 360;
		return new Color(Color.HSBtoRGB(hue, (float) .4, 1));
	}

}
