public class Dragon extends Creature{
    private String name;
    private int lifeLevel;
    
    private double flightRange;
    private int flameStrength;

  public Dragon(String name, int level, double fr, int fs){
    super(name, level);
    this.flightRange = fr;
    this.flameStrength = fs;
  }
    
public double getFlightRange(){
  return flameStrength;
}

public int getFlameStrength(){
  return flameStrength;
}

public String toString(){
  return("Name: "+name+", life level: "+lifeLevel+", range: "+flightRange+", flame: "+flameStrength);
}
}