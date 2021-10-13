public class HourlyEmployee extends Employee{
  
	private double wage;
	private double hrs;
   public HourlyEmployee(int id, String n, double hrs, double wage){
     super(id, n, hrs);
     this.hrs = hrs;
     this.wage = wage;
     
   }

  public double getWeeklyPay(){
    return wage*hrs;
  }

}