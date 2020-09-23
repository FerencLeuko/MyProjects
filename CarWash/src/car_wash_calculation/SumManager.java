package car_wash;

public class SumManager {

	private Materials materials = new Materials();
	private Services services = new Services();
	private UserInput input = new UserInput();
	private FileManager file = new FileManager();
	private Printer printer = new Printer();

	public static void sum() {
		SumManager sum = new SumManager();
		sum.calculateSum();
	}

	private void calculateSum() {
		try {
			int num = askForNumFiles();
			addFiles(num);
			printer.printAllConsumption(materials, services);
			if (askSave()) {
				file.saveAs(materials, services);
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
		return input.askInputIntWithLimits("Hány mentett adatfile-t szeretne összesíteni: ", 25);
	}

	private void addFiles(int num) {
		String lastSavedNum = "_default";
		try {
			lastSavedNum = file.loadLastSavedNumber();
		} catch (Exception e) {
		}
		file.loadMaterialPrices(materials, lastSavedNum);
		file.loadMaterialUnits(materials, lastSavedNum);
		file.loadServiceRatios(services, lastSavedNum);
		for (int i = 0; i < num; i++) {
			int fileNum = input.askInputInt("Melyik sorszámú munkamenetet szeretné betölteni: ");
			String fileNumber = "" + fileNum;
			double[] addMQ = materials.getQuant();
			int[] addSerS = services.getQSmall();
			int[] addSerL = services.getQLarge();
			file.loadMaterialQuants(materials, fileNumber);
			file.loadServiceQSmall(services, fileNumber);
			file.loadServiceQLarge(services, fileNumber);
			for (int j = 0; j < Materials.getMaterialNames().length; j++) {
				materials.setQuant(materials.getQuant(j) + addMQ[j], j);
				services.setQSmall(services.getQSmall(j) + addSerS[j], j);
				services.setQLarge(services.getQLarge(j) + addSerL[j], j);
			}
		}
	}

}
