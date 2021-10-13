public class RentalApt extends Apartment{
  private String tenant;
  private boolean rented;

  public RentalApt(String owner, int size, boolean rented, String who){
    super(owner,size);
    tenant = who;
    this.rented = rented;
  }  public boolean getRented(){
    return rented;
  }
  public String getTenant(){
    return tenant;
  }
  public void setRented(boolean isRented){
    rented = isRented;
  }
  public void setTenant(String who){
    tenant= who;
  }
  public String toString(){
    String s = super.toString();
    return (s + " occupied? " +  rented + " tenant: " + tenant);
  }}