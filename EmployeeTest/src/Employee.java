public abstract class Employee{
  private int employeeID;
  private String name;
  private double hoursWorked;

  public Employee(int id, String n, double hrs){
    employeeID=id;
    name=n;
    hoursWorked=hrs;
  }
  public int getID(){ return employeeID;}
  public String getName() { return name; }
  public double getHoursWorked() { return hoursWorked;}
  public abstract double getWeeklyPay();
}