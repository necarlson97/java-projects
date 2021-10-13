public class PricedApt extends RentalApt {

  private double price;

	public PricedApt(String owner, int size, boolean rented, String who, double price){
	  super(owner, size, rented, who);
	  this.price = price;
	  
	}
}