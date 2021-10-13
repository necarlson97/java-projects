
public class JournalUtils {
	
	public static String unEscapeString(){
		String s = Journal.entryString;
		return unEscapeString(s);
	}
	
	public static String unEscapeString(String s){
	    StringBuilder sb = new StringBuilder();
	    for (int i=0; i<s.length(); i++)
	        switch (s.charAt(i)){
	            case '\n': sb.append("\\n"); break;
	            case '\t': sb.append("\\t"); break;
	            case ' ': sb.append("\\s"); break;
	            // ... rest of escape characters
	            default: sb.append(s.charAt(i));
	        }
	    String ret = sb.toString();
	    System.out.println(ret + " : " + ret.length());
	    return ret;
	}
	

	private static String shortenFloat(float f){
		if((f+"").length() > 4)
			return (f+"").substring(0, 4);
		else 
			return f+"";
	}

	public static double oscillateNumber(int numb, int period, double scale) {
	    return Math.sin(numb*2*Math.PI/period)*(scale/2) + (scale/2);
	}

}
