package car_wash_calculation;

import car_wash_main.FileManager;
import car_wash_main.Main;
import car_wash_main.Printer;
import car_wash_main.UserInput;
import car_wash_register.Materials;
import car_wash_register.Services;

public class RatiosManager {

	private UserInput input = new UserInput();
	private Printer printer = new Printer();
	private Materials materials = new Materials();
	private Services services = new Services();
	private final FileManager file = FileManager.getFileManager();

	private int small1;
	private int small2;
	private int large1;
	private int large2;
	private double material1;
	private double material2;
	private int choice;

	public void calculateRatio(Services services) {
		try {
			printer.print(String.format("Kis és nagy autó anyagfelhasználás pontosítása számításokkal.%n"));
			printer.printNames(Materials.getMaterialNames());
			printer.print(String.format("* Mégsem%n"));
			choice = input.askInputWithExitAndLimits("A választott anyag: ", Materials.getMaterialNames().length);
			if (choice != Main.EXIT_VALUE) {
				choice--;
				askInfo();
				updateRatio(calculate(), services);
			}
		} catch (Exception e) {
			System.out.println("Nem sikerült a pontosítás.");
		}
	}

	private void updateRatio(double ratio, Services services) {
		if (ratio != 0) {
			printer.print(String.format("%nA nagy autók anyagfelhasználási aránya %s esetén: %.2f", Materials.getMaterialNames()[choice], ratio));
			printer.print(String.format("%n(A régi arány: %.2f volt.)%n", services.getsLRatio()[choice]));
			int approve = input.askInputIntWithLimits(String.format("%n1. Változás rögzítése %n2. Régi arány megtartása%nA választás: "), 2);
			if (approve == 1) {
				services.setsLRatio(ratio, choice);
				printer.print(
						String.format("Az új arány %s esetén: %.2f lett.%n", Materials.getMaterialNames()[choice], services.getsLRatio()[choice]));
			} else {
				printer.print(String.format("A régi arány maradt: %.2f.%n", services.getsLRatio()[choice]));
			}
		} else {
			printer.print(String.format("%nAz arányok nem változtak.%n"));
		}
	}

	private void askInfo() {
		printer.print(String.format("Az arány pontosításához két mentett adat file szükséges.%n"));
		printer.print(String.format("%nKérem adja meg az első mentett file-t!%n"));
		askFirstData();
		printer.print(String.format("A szolgáltatások kis autóra: %d, nagy autóra: %d, az anyagfelhasználás: %.2f.%n", small1, large1, material1));
		printer.print(String.format("%nKérem adja meg a második mentett file-t!%n"));
		askSecondData();
		printer.print(String.format("A szolgáltatások kis autóra: %d, nagy autóra: %d, az anyagfelhasználás: %.2f.%n", small2, large2, material2));
	}

	private void askFirstData() {
		int fileNum = input.askInputInt("Melyik sorszámú munkamenetet szeretné betölteni: ");
		String fileNumber = "" + fileNum;
		file.loadMaterialQuants(materials, fileNumber);
		file.loadServiceQSmall(services, fileNumber);
		file.loadServiceQLarge(services, fileNumber);
		small1 = services.getQSmall(choice);
		large1 = services.getQLarge(choice);
		material1 = materials.getQuant(choice);
	}

	private void askSecondData() {
		int fileNum = input.askInputInt("Melyik sorszámú munkamenetet szeretné betölteni: ");
		String fileNumber = "" + fileNum;
		file.loadMaterialQuants(materials, fileNumber);
		file.loadServiceQSmall(services, fileNumber);
		file.loadServiceQLarge(services, fileNumber);
		small2 = services.getQSmall(choice);
		large2 = services.getQLarge(choice);
		material2 = materials.getQuant(choice);
	}

	private double calculate() {
//		I.    a*x+b*y=p
//		II.   c*x+d*y=q
		int a = small1;
		int b = large1;
		int c = small2;
		int d = large2;
		double p = material1;
		double q = material2;
		double x, y;
		if (a * d - b * c == 0 || material1 <= 0 || material2 <= 0 || small1 < 0 || small2 < 0 || large1 < 0 || large2 < 0) {
			printer.print(String.format("Nem számítható a megadott adatok alapján.%n"));
		} else {
			y = ((p * c - q * a) / (c * b - d * a));
			x = (p - b * y) / a;
			return (y / x) > 0 ? y / x : 0;
		}
		return 0;
	}

}
