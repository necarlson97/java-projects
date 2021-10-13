import java.awt.Color;
import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateSidebar extends JournalTextWindow{
	
	static Date date = new Date();
	static DateFormat formatter;
	static Calendar calendar;
	
	static int dateStart = -10;
	static String selectedDate;
	
	public DateSidebar() {
		
		formatter = new SimpleDateFormat("dd MMM yyyy");
		calendar = Calendar.getInstance(); 
		
		formatter.setTimeZone(TimeZone.getTimeZone("EST")); 
		
	}

	@Override
	public void run() {
		super.run();
		
	}
	
	public void centerDate() {
		dateStart = -10;
		calendar.setTime(date); 
		calendar.add(Calendar.DATE, dateStart+10);
		Journal.entry.save();
		Journal.entry = new Entry(selectedDateName());
	}
	
	public void changeDate(int change) {
		dateStart += change;
		Journal.entry.save();
		Journal.entry = new Entry(selectedDateName());
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
			
			if(calendar.getTime().equals(date)) 
				g.setColor(Color.pink);
			
			if(i == 10) {
				g.setColor(Color.pink);
				g.fillRect(0, intY-headerPointSize-margin/2, partitionX, headerPointSize + margin);
				g.setColor(Color.BLACK);
				g.drawString(formatter.format(calendar.getTime()), margin, intY);
			}
			else 
				g.drawString(formatter.format(calendar.getTime()), margin, intY);
			
			calendar.add(Calendar.DATE, 1);
			intY += headerPointSize+margin;
		}

	}

}
