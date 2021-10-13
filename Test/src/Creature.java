public class Creature{
    private String name;
    private int lifeLevel;

  public Creature(String name, int level){ 
    this.name=name;
    lifeLevel=level;
  }
  public String getName(){
    return name;
  }
  public int getLifeLevel(){
    return lifeLevel;
  }
  public void changeLifeLevel(int amt){
     lifeLevel+=amt;
  }
  public String toString(){
    return("Name: " + name + ", life level: " + lifeLevel);
  }
}