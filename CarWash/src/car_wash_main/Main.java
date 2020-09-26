package car_wash_main;

import car_wash_menu.MenuFactory;
import car_wash_register.CurrentDate;
import car_wash_register.Materials;
import car_wash_register.Services;

public class Main {

	public static final int EXIT_VALUE = Integer.MIN_VALUE;
	public static final char EXIT_CHAR = '*';
	private final FileManager file = FileManager.getFileManager();
	private Materials materials = new Materials();
	private Services services = new Services();
	private UserInput input = new UserInput();
	private Printer printer = new Printer();
	private CurrentDate date = new CurrentDate();
	private MenuFactory menu = new MenuFactory();
	private final Menu[] menuSelection = menu.getMenuSelection();
	private final Menu[] extraSelection = menu.getExtraSelection();

	public static void main(String[] args) {
		try {
			Main main = new Main();
			main.run();
		} catch (Exception e) {
			System.out.print("Hiba!");
			System.out.println(e.getLocalizedMessage());
		}
	}

	private void run() {
		initNewRun();
		while (true) {
			try {
				printer.printMenu(true, menuSelection);
				int choice = input.askInputIntWithLimitsAndExitOption("Az ön által kért menüpont: ", menuSelection.length);
				if (choice == EXIT_VALUE) {
					break;
				}
				selectMenuAction(choice);
				file.autoSave(materials, services);
			} catch (Exception e) {
				System.out.print("Hiba történt!");
				System.out.println(e.getMessage());
			}
		}
		printer.printExit();
		UserInput.SCANNER.close();
	}

	private void initNewRun() {
		printer.print(date.provideDateString());
		printer.printGreetings();
		file.loadAutoSave(materials, services);
		file.initWorkSessionList();
	}

	private void selectMenuAction(int choice) {
		if (menuSelection[--choice].getName().equals(Menu.EXTRAS_NAME)) {
			selectExtras();
		} else {
			menuSelection[choice].menuAction(materials, services);
		}
	}

	private void selectExtras() {
		printer.printMenu(false, extraSelection);
		int choice = input.askInputIntWithLimitsAndExitOption("Az ön által kért menüpont: ", extraSelection.length);
		if (choice != EXIT_VALUE) {
			extraSelection[--choice].menuAction(materials, services);
		}
	}

}