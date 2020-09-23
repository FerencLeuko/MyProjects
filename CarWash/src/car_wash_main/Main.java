package car_wash_main;

import car_wash_data.CurrentDate;
import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_menu.MenuFactory;

public class Main {

	public static final int EXIT_VALUE = Integer.MIN_VALUE;
	public static final char EXIT_CHAR = '*';
	public static FileManager file = FileManager.getFileManager();
	private Materials materials = new Materials();
	private Services services = new Services();
	private UserInput input = new UserInput();
	private Printer printer = new Printer();
	private CurrentDate date = new CurrentDate();
	private MenuFactory menu = new MenuFactory();
	private final Menu[] menuSelection = menu.getMenuSelection();
	private final Menu[] extraSelection = menu.getExtraSelection();
	private final int numMenu = menuSelection.length;
	private final int numExtras = extraSelection.length;

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
				printer.printMenu(menuSelection);
				int choice = input.askInputWithExitAndLimits("Az ön által kért menüpont: ", numMenu);
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
		if (menuSelection[--choice].getName().equals("Extrák")) {
			selectExtras();
		} else {
			menuSelection[choice].menuAction(materials, services);
		}
	}

	private void selectExtras() {
		printer.printExtras(extraSelection);
		int choice = input.askInputWithExitAndLimits("Az ön által kért menüpont: ", numExtras);
		if (choice != EXIT_VALUE) {
			extraSelection[--choice].menuAction(materials, services);
		}
	}

}