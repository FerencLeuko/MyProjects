package manager;

import manager.menu.MenuFactory;

public class Main {

	public static final String EXIT = "*";
	public static final String PROMPT = "> ";
	private final UserInput user = new UserInput();
	private MenuFactory menuFactory = new MenuFactory();
	private boolean userExit;

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		Menu[] mainMenu = menuFactory.getMainMenu();
		printGreetings();
		do {
			selectMenu(mainMenu);
		} while (!userExit);
		printExit();
		UserInput.SCANNER.close();
	}

	private void selectMenu(Menu[] menuArray) {
		printMenu(menuArray);
		do {
			try {
				System.out.print(PROMPT);
				String choice = user.askInput();
				if (choice.equals(EXIT)) {
					userExit = true;
				} else {
					menuArray[Integer.parseInt(choice) - 1].menuOperation();
					break;
				}
			} catch (RuntimeException e) {
			}
		} while (!userExit);
	}

	private void printMenu(Menu[] menuArray) {
		int counter = 1;
		System.out.println();
		for (Menu menu : menuArray) {
			System.out.printf("%d%s%s%n", counter++, ". ", menu.getMenuName());
		}
		System.out.printf("%s Kilépés%n", EXIT);
	}

	private void printGreetings() {
		System.out.printf("Üdvözlöm az amőba játékban!%n");
		System.out.printf("Kérem válasszon a menü alapján!%n");
	}

	private void printExit() {
		System.out.println("Viszlát!");
	}

}
