package defaultpackage;

public class Practice {


	public static void main(String[] args) {
		
		String [] name = {"Week", "Mon", "Tue", "Wed", "Thur"};
		label1:
		
		for (String temp: name) {
			if (temp == "Week")
				System.out.println(temp);
			
			for (int i=0; i<3; i++) {
				System.out.println("This is a nested loop");
				continue label1;
			}
		}
	

	}

}
