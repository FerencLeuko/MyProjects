package car_wash.main;

import car_wash.main.menu.MenuFactory;
import car_wash.register.CurrentDate;
import car_wash.register.Materials;
import car_wash.register.Services;

public class Main {

	public static final int EXIT_VALUE = Integer.MIN_VALUE;
	public static final char EXIT_CHAR = '*';
	private static final FileManager FILE = FileManager.getFileManager();
	private Materials materials = new Materials();
	private Services services = new Services();
	private UserInput input = new UserInput();
	private Printer printer = new Printer();
	private CurrentDate date = new CurrentDate();
	private MenuFactory menu = MenuFactory.getInstance();
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
				FILE.autoSave(materials, services);
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
		FILE.loadAutoSave(materials, services);
		FILE.initWorkSessionList();
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