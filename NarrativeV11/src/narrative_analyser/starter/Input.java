package narrative_analyser.starter;

import java.util.Scanner;

public class Input {
	
	private static final Scanner SCANNER = new Scanner(System.in);
	
	public String askInput() {
		return SCANNER.nextLine();
	}

}
