// Nils Carlson, Student ID: 229312429
// Project: Height Converter

public class HeightConverter {

	public static void main(String[] args){
		
		double conversionFactor = 2.54;
		
		String myName = "Nils Carlson";
		int myAge = 18;
		int myHeightInches = 72;
		
		double myHeightCM = myHeightInches * conversionFactor;
		
		System.out.println(myName+"\nage: "+myAge+"\nheight: "+myHeightInches+" inches\nheight: "+
																myHeightCM+" cm");
		
	}
	
}
