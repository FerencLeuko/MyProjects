package car_wash_calculation;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Main;
import car_wash_main.Printer;
import car_wash_main.UserInput;

public class SumManager {

	private Materials materials = new Materials();
	private Services services = new Services();
	private UserInput input = new UserInput();
	private Printer printer = new Printer();
	private static final int SUM_LIMIT = 50;

	public void sum() {
		calculateSum();
	}

	private void calculateSum() {
		try {
			int num = askForNumFiles();
			addFiles(num);
			printer.printAllConsumption(materials, services);
			if (askSave()) {
				Main.file.saveAs(materials, services);
			} else {
				printer.print(String.format("Az összesítést nem mentette a program. Folytatás.%n"));
			}
		} catch (Exception e) {
			System.out.println("Nem sikerült az összegzés.");
		}
	}

	private boolean askSave() {
		return input.askInputIntWithLimits("Szeretné menteni? 1. Igen 2. Nem ", 2) == 1;
	}

	private int askForNumFiles() {
		return input.askInputIntWithLimits("Hány mentett adatfile-t szeretne összesíteni: ", SUM_LIMIT);
	}

	private void addFiles(int num) {
		String lastSavedNum = "_default";
		try {
			lastSavedNum = Main.file.loadLastSavedNumber();
		} catch (Exception e) {
		}
		Main.file.loadMaterialPrices(materials, lastSavedNum);
		Main.file.loadMaterialUnits(materials, lastSavedNum);
		Main.file.loadServiceRatios(services, lastSavedNum);
		for (int i = 0; i < num; i++) {
			int fileNum = input.askInputInt("Melyik sorszámú munkamenetet szeretné betölteni: ");
			String fileNumber = "" + fileNum;
			double[] addMQ = materials.getQuants();
			int[] addSerS = services.getQSmall();
			int[] addSerL = services.getQLarge();
			Main.file.loadMaterialQuants(materials, fileNumber);
			Main.file.loadServiceQSmall(services, fileNumber);
			Main.file.loadServiceQLarge(services, fileNumber);
			for (int j = 0; j < Materials.getMaterialNames().length; j++) {
				materials.setQuant(materials.getQuant(j) + addMQ[j], j);
				services.setQSmall(services.getQSmall(j) + addSerS[j], j);
				services.setQLarge(services.getQLarge(j) + addSerL[j], j);
			}
		}
	}

}
