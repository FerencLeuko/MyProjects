package manager;

import java.util.Scanner;

public class UserInput {
	
	public static final Scanner SCANNER = new Scanner(System.in);
	
	public String askInput() {
		
		return SCANNER.nextLine();
	}

}
