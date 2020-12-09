package narrative_analyser.starter.menu;

import java.util.Set;

import narrative_analyser.starter.ExitException;
import narrative_analyser.starter.Input;
import narrative_analyser.starter.Menu;
import narrative_analyser.starter.Storage;

abstract class AbstractMenu implements Menu {

	Menu[] allMenuPoints = MenuFactory.getAllMenuPoints();
	Input input = new Input();
	Storage storage = Storage.getInstance();
	final String exit = "*";
	final String back = "<-";

	String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void printMenuNames(String prompt, Set<String> menuNames) {
		printMenuNames(prompt, menuNames.toArray(new String[menuNames.size()]));
	}

	public void printMenuNames(String prompt, String... menuNames) {
		char letter = 'A';
		System.out.print(prompt);
		if (menuNames != null) {
			for (String menuName : menuNames) {
				if (!menuName.strip().isEmpty())
					System.out.printf("%n%s%s%s", letter++, ". ", menuName);
			}
		}
		System.out.println();
	}

	@Override
	public int select(int limit) throws ExitException {
		int choice = -1;
		do {
			System.out.printf("Please enter your choice!%n > ");
			try {
				String answer = input.askInput();
				if(answer.equals(exit)) {
					throw new ExitException();
				}
				choice = answer.toUpperCase().strip().charAt(0) - 'A';
			} catch (RuntimeException e) {
			}
		} while (choice < 0 || choice >= limit);
		return choice;
	}

}
