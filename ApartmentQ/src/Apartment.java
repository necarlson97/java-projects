public class Apartment{

  private String owner;
  private int size; // square feet
  public Apartment(String owner,int size){
    this.owner = owner;
    this.size = size;
  }
  public int getSize(){
    return size;
  }
  public String getOwner(){
    return owner;
  }
  public void setOwner(String newOwner){
    owner = newOwner;
  }
  public String toString(){
    return(owner + " size: " + size);
  }}