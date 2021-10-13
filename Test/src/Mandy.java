
public class Mandy{
	
	public static void main(String[] args){
		
		int pikachuDmg = 10;
		int pikachuHealth = 50;
		String pikachuName = "Nickle";
		
		int bulbDmg = 12;
		int bulbHealth = 40;
		String bulbName = "Chip";
		
		
		System.out.println("Pika Full health: "+pikachuHealth);
		pikachuHealth = pikachuHealth - bulbDmg;
		System.out.println("After first hit: "+pikachuHealth);
		pikachuHealth = pikachuHealth - bulbDmg;
		System.out.println("After second hit: "+pikachuHealth);
		
		System.out.println("Bulb Full health: "+bulbHealth);
		bulbHealth = bulbHealth- pikachuDmg;
		System.out.println("After first hit: "+ bulbHealth);
		bulbHealth = bulbHealth- pikachuDmg;
		System.out.println("After secound hit: "+bulbHealth);
		
		
		
		
	}
	
	
}

