package defaultpackage;

public class First {
	
	static final double PI = 3.142;
	 
	static String greeting = "Hello every1";
	
	public static void main(String [] arg) {
	
		
		int a = 5;
		int b = 6;
		
		String name = "Sunday";
		String surname = "Sunday";
		
		boolean isTrue = false;
		String equalCheck = a == b? "Equal" : "Not Equal";
		
		
		if (a == b) {
			System.out.println("This is equal to A");
		}
		
		else if (a != b) {
			System.out.println("A is not equal to B");
		}
		
		else {
			System.out.println("A and B are not eqal and equivalent");
		}
		
		
		int ans = 9;
		
		while (ans >= 0) {
			System.out.println("ans is less than 9");
			
			ans--;
		}
		
		double amount;
		amount = 2000.00;
		
		if (amount >= 2000)
			System.out.println("Amount is greater than 2000");
		
		else if (amount == 1500 )	
		
		System.out.println("Thank you");
		System.out.println(Math.PI	);
		System.out.println(greeting);
		
		System.out.println(name == surname? "Correct" : "Not Correct");
	}
	

}
