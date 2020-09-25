package car_wash_register;

import car_wash_main.Register;
import car_wash_main.FileManager;
import car_wash_main.Printer;
import car_wash_main.UserInput;

public class Materials implements Register {

	private static final int NUM_MATERIALS = 8;
	private static final String[] MATERIAL_NAMES = new String[NUM_MATERIALS];
	private double[] quants = new double[NUM_MATERIALS];
	private int[] prices = new int[NUM_MATERIALS];
	private int[] units = new int[NUM_MATERIALS];
	private UserInput input = new UserInput();
	private Printer printer = new Printer();
	private FileManager file = FileManager.getFileManager();

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
		file.loadDefaultPrices(this);
		file.loadDefaultUnits(this);
		file.loadDefaultQuants(this);
	}

	public void addMaterialOrdered() {
		printer.print(String.format("Anyagrendelés hozzáadása:%n"));
		printer.printNames(MATERIAL_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("Az anyag: ", MATERIAL_NAMES.length);
		if (choice != EXIT_VALUE) {
			choice--;
			double change = input.askInputDouble("A rendelt mennyiség literben: ");
			printer.printChange("felhasznált " + MATERIAL_NAMES[choice], new Number[] { quants[choice], quants[choice] + change });
			quants[choice] += change;
		}
	}

	public void updateStorage() {
		printer.print(String.format("Készletváltozás rögzítése:%n"));
		printer.printNames(MATERIAL_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("Az anyag: ", MATERIAL_NAMES.length);
		if (choice != EXIT_VALUE) {
			double change = input.askInputDouble("A készletváltozás literben (készletcsökkenés esetén negatív): ");
			printer.printChange("felhasznált " + MATERIAL_NAMES[--choice], new Number[] { quants[choice], quants[choice] - change });
			quants[choice] -= change;
		}
	}

	public void changePrice() {
		printer.print(String.format("Anyagár módosítása (kiszerelés módosítása):%n"));
		printer.printNames(MATERIAL_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("Az anyag: ", MATERIAL_NAMES.length);
		if (choice != EXIT_VALUE) {
			printer.print(String.format("%s, eddigi ára: %,d Ft/liter volt.%n", MATERIAL_NAMES[--choice], prices[choice]));
			prices[choice] = input.askInputInt("Az új ára per liter: ");
			printer.print(String.format("%s, módosított ára: %,d Ft/liter lett.%n", MATERIAL_NAMES[choice], prices[choice]));
		}
	}

	public void changeUnits() {
		printer.print(String.format("Anyagok kiszerelésének módosítása: %n"));
		printer.printNames(MATERIAL_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("Az anyag: ", MATERIAL_NAMES.length);
		if (choice != EXIT_VALUE) {
			printer.print(String.format(MATERIAL_NAMES[--choice] + " kiszerelése: " + addUnitName(choice) + "%n"));
			units[choice] = input.askInputInt("Az új kiszerelés ml-ben megadva (1 liter = 1000 ml): ");
			printer.print(String.format("%s, módosított kiszerelése: %s%n", MATERIAL_NAMES[choice], addUnitName(choice)));
		}
	}

	public String addUnitName(int i) {
		return units[i] >= 1000 ? "" + units[i] / 1000 + " liter." : "" + units[i] + " ml.";
	}

	public static String[] getMaterialNames() {
		return MATERIAL_NAMES;
	}

	public double[] getQuants() {
		return quants;
	}

	public double getQuant(int index) {
		return quants[index];
	}

	public void setQuants(double[] quant) {
		this.quants = quant;
	}

	public void setQuant(double quant, int index) {
		this.quants[index] = quant;
	}

	public int[] getPrices() {
		return prices;
	}

	public int getPrices(int index) {
		return prices[index];
	}

	public void setPrices(int[] prices) {
		this.prices = prices;
	}

	public void setPrices(int price, int index) {
		this.prices[index] = price;
	}

	public int[] getUnits() {
		return units;
	}

	public void setUnits(int[] units) {
		this.units = units;
	}

}
