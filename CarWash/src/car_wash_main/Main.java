package car_wash;

public class Main {

	static final int MENU = 9;
	static final int EXTRAS = 6;
	static final int EXIT_VALUE = Integer.MIN_VALUE;
	static final char EXIT_CHAR = '*';
	private Materials materials = new Materials();
	private Services services = new Services();
	private UserInput input = new UserInput();
	private Printer printer = new Printer();
	private FileManager file = new FileManager();
	private CurrentDate date = new CurrentDate();

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
		printer.print(date.provideDateString());
		printer.printGreetings();
		file.loadAutoSave(materials, services);
		file.initWorkSessionList();
		menu: while (true) {
			try {
				printer.printMenu();
				int choice = input.askInputWithExitAndLimits("Az ön által kért menüpont: ", MENU);
				switch (choice) {
				case 1:
					services.addServiceSold();
					break;
				case 2:
					materials.addMaterialOrdered();
					break;
				case 3:
					materials.updateStorage();
					break;
				case 4:
					printer.printConsumption(materials, services);
					break;
				case 5:
					printer.printAllConsumption(materials, services);
					break;
				case 6:
					file.saveAs(materials, services);
					break;
				case 7:
					file.loadAs(materials, services);
					break;
				case 8:
					file.startNewWorkSession(materials, services);
					break;
				case 9:
					selectExtras(materials, services);
					break;
				case EXIT_VALUE:
					break menu;
				}
				file.autoSave(materials, services);
			} catch (Exception e) {
				System.out.print("Hiba történt!");
				System.out.println(e.getMessage());
			}
		}
		printer.printExit();
		UserInput.SCANNER.close();
	}

	private void selectExtras(Materials materials, Services services) {
		printer.printExtras();
		int choice = input.askInputWithExitAndLimits("Az ön által kért menüpont: ", EXTRAS);
		if (choice != EXIT_VALUE) {
			switch (choice) {
			case 1:
				RatiosManager ratios = new RatiosManager();
				ratios.calculateRatio(services);
				break;
			case 2:
				SumManager.sum();
				break;
			case 3:
				materials.changePrice();
				break;
			case 4:
				materials.changeUnits();
				break;
			case 5:
				file.loadDefaultPrices(materials);
				break;
			case 6:
				file.loadDefaultRatios(services);
				break;

			}
		}
	}

}