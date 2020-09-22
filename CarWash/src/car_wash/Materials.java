package car_wash;

class Materials {

	private static final String[] MATERIAL_NAMES = new String[8];
	private double[] quant = new double[8];
	private int[] prices = new int[8];
	private int[] units = new int[8];
	private UserInput input = new UserInput();
	private Printer printer = new Printer();

	static {

		MATERIAL_NAMES[0] = "3M polírpaszta";
		MATERIAL_NAMES[1] = "Nasiol Protect";
		MATERIAL_NAMES[2] = "VMD spray";
		MATERIAL_NAMES[3] = "Astonish";
		MATERIAL_NAMES[4] = "Universal";
		MATERIAL_NAMES[5] = "Hill Vital";
		MATERIAL_NAMES[6] = "Prevent";
		MATERIAL_NAMES[7] = "Motor cleaner";

	}

	{
		prices[0] = 13300;
		prices[1] = 24000 * 20;
		prices[2] = 1599 * 2;
		prices[3] = 949 / 3 * 4;
		prices[4] = 2030 / 5;
		prices[5] = 2290 * 5;
		prices[6] = 1999 / 375 * 1000;
		prices[7] = 1220;

		units[0] = 1000;
		units[1] = 50;
		units[2] = 500;
		units[3] = 750;
		units[4] = 5000;
		units[5] = 200;
		units[6] = 375;
		units[7] = 1000;

		for (int i = 0; i < MATERIAL_NAMES.length; i++) {
			quant[i] = 0;
		}
	}

	void addMaterialOrdered() {
		printer.print(String.format("Anyagrendelés hozzáadása:%n"));
		printer.printNames(MATERIAL_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("Az anyag: ", MATERIAL_NAMES.length);
		if (choice != Main.EXIT_VALUE) {
			choice--;
			double change = input.askInputDouble("A rendelt mennyiség literben: ");
			printer.printChangeDouble("felhasznált " + MATERIAL_NAMES[choice], new double[] { quant[choice], quant[choice] + change });
			quant[choice] += change;
		}
	}

	void updateStorage() {
		printer.print(String.format("Készletváltozás rögzítése:%n"));
		printer.printNames(MATERIAL_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("Az anyag: ", MATERIAL_NAMES.length);
		if (choice != Main.EXIT_VALUE) {
			double change = input.askInputDouble("A készletváltozás literben (készletcsökkenés esetén negatív): ");
			printer.printChangeDouble("felhasznált " + MATERIAL_NAMES[--choice], new double[] { quant[choice], quant[choice] - change });
			quant[choice] -= change;
		}
	}

	void changePrice() {
		printer.print(String.format("Anyagár módosítása (kiszerelés módosítása):%n"));
		printer.printNames(MATERIAL_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("Az anyag: ", MATERIAL_NAMES.length);
		if (choice != Main.EXIT_VALUE) {
			printer.print(String.format("%s, eddigi ára: %,d Ft/liter volt.%n", MATERIAL_NAMES[--choice], prices[choice]));
			prices[choice] = input.askInputInt("Az új ára per liter: ");
			printer.print(String.format("%s, módosított ára: %,d Ft/liter lett.%n", MATERIAL_NAMES[choice], prices[choice]));
		}
	}

	void changeUnits() {
		printer.print(String.format("Anyagok kiszerelésének módosítása: %n"));
		printer.printNames(MATERIAL_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("Az anyag: ", MATERIAL_NAMES.length);
		if (choice != Main.EXIT_VALUE) {
			printer.print(String.format(MATERIAL_NAMES[--choice] + " kiszerelése: " + addUnit(choice) + "%n"));
			units[choice] = input.askInputInt("Az új kiszerelés ml-ben megadva (1 liter = 1000 ml): ");
			printer.print(String.format("%s, módosított kiszerelése: %s%n", MATERIAL_NAMES[choice], addUnit(choice)));
		}
	}

	String addUnit(int i) {
		return units[i] >= 1000 ? "" + units[i] / 1000 + " liter." : "" + units[i] + " ml.";
	}

	static String[] getMaterialNames() {
		return MATERIAL_NAMES;
	}

	double[] getQuant() {
		return quant;
	}

	double getQuant(int index) {
		return quant[index];
	}

	void setQuant(double[] quant) {
		this.quant = quant;
	}

	void setQuant(double quant, int index) {
		this.quant[index] = quant;
	}

	int[] getPrices() {
		return prices;
	}

	int getPrices(int index) {
		return prices[index];
	}

	void setPrices(int[] prices) {
		this.prices = prices;
	}

	void setPrices(int price, int index) {
		this.prices[index] = price;
	}

	int[] getUnits() {
		return units;
	}

	void setUnits(int[] units) {
		this.units = units;
	}

}
