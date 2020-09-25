package car_wash_main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import car_wash_register.CurrentDate;
import car_wash_register.Materials;
import car_wash_register.Services;

public class FileManager {

	private UserInput input = new UserInput();
	private Printer printer = new Printer();
	private CurrentDate date = new CurrentDate();
	private static List<Integer> workSessionList = new LinkedList<Integer>();

	private FileManager() {
	}

	private static final FileManager FILE = new FileManager();

	public static FileManager getFileManager() {
		return FILE;
	}

	public void initWorkSessionList() {
		try {
			for (int fileNumber : readDataFromFileInt("workSessionList")) {
				workSessionList.add(fileNumber);
			}
		} catch (Exception e) {
		}
		return;
	}

	private void updateWorkSessionList(int fileNumber) {
		if (!workSessionList.contains(fileNumber)) {
			workSessionList.add(fileNumber);
			int[] fileNumbersArray = workSessionList.stream().mapToInt(i -> i).toArray();
			saveDataToFileInt("workSessionList", fileNumbersArray);
		}
	}

	public void loadAutoSave(Materials materials, Services services) {
		String lastSavedNum = "";
		try {
			lastSavedNum = readTextFromFile("lastSavedNum").replaceAll("[^0-9. ]", "");
			loadAll(materials, services, lastSavedNum);
			printer.print(
					String.format("A legutóbbi munkamenet betöltése megtörtént, sorszáma: %s.%n", lastSavedNum == "" ? "nem volt" : lastSavedNum));
		} catch (Exception e) {
			startNewWorkSession(materials, services);
			printer.print(String.format("Nem volt korábbi mentett munkamenet.%n"));
		}
	}

	public void loadAs(Materials materials, Services services) {
		int lastSavedNum = Integer.MIN_VALUE;
		try {
			lastSavedNum = readDataFromFileInt("lastSavedNum")[0];
		} catch (Exception e) {
			System.out.println("Nem volt még mentett sorzsám.");
		}
		if (lastSavedNum != Integer.MIN_VALUE) {
			System.out.printf("A legutóbbi aktív munkamenet sorszáma: %d.%n", lastSavedNum);
		}
		int fileNumber = input.askInputInt("Melyik sorszámú munkamenetet szeretné betölteni (lista:0-val): ");
		try {
			if (fileNumber == 0) {
				provideFileList();
				fileNumber = input.askInputInt("Melyik sorszámú munkamenetet szeretné betölteni: ");
			}
			loadAll(materials, services, "" + fileNumber);
			printer.print(String.format("Az adatok betöltése megtörtént. %n"));
			saveDataToFileInt("lastSavedNum", new int[] { fileNumber });
			writeTextToFile("lastSavedDate", date.provideDateString());
			updateWorkSessionList(fileNumber);
		} catch (Exception e) {
			printer.print("Nem sikerült a betöltés. ");
		}
	}

	private void provideFileList() {
		try {
			int[] files = readDataFromFileInt("workSessionList");
			printer.print(String.format("A korábbi munkamenetek listája létrehozási sorrendben: %n"));
			for (int file : files) {
				printer.print(String.format("%s%n", "" + file));
			}
		} catch (Exception e) {
			printer.print("Nem sikerült a lista betöltése.");
		}
	}

	public void startNewWorkSession(Materials materials, Services services) {
		try {
			loadDefaultQuants(materials);
			loadDefaultServicesQ(services);
			printer.print(String.format("Új munkamenet indult.%n"));
			int fileNumber = input.askInputInt("Milyen sorszámmal mentsem a munkamenetet: ");
			saveAll(materials, services, "" + fileNumber);
			printer.print(String.format("A mentett file sorszáma: %d%n", fileNumber));
			saveDataToFileInt("lastSavedNum", new int[] { fileNumber });
			writeTextToFile("lastSavedDate", date.provideDateString());
			updateWorkSessionList(fileNumber);
		} catch (Exception e) {
			System.out.println("Nem sikerült a betöltés. A munkamenet a beépített adatokkal indul.");
		}
	}

	public void loadDefaultRatios(Services services) {
		try {
			services.setsLRatio(readDataFromFileDouble("ServiceRatios_default"));
		} catch (Exception e) {
			printer.print("Nem sikerült a betöltés.");
		}
	}

	public void loadDefaultPrices(Materials materials) {
		try {
			materials.setPrices(readDataFromFileInt("MaterialP_default"));
		} catch (Exception e) {
			printer.print("Nem sikerült a betöltés.");
		}
	}

	public void loadDefaultUnits(Materials materials) {
		try {
			materials.setUnits(readDataFromFileInt("MaterialUnits_default"));
		} catch (Exception e) {
			printer.print("Nem sikerült a betöltés.");
		}
	}

	public void loadDefaultQuants(Materials materials) {
		try {
			materials.setQuants(readDataFromFileDouble("MaterialQ_default"));
		} catch (Exception e) {
			printer.print("Nem sikerült a betöltés.");
		}
	}

	public void loadDefaultServicesQ(Services services) {
		try {
			services.setQSmall(readDataFromFileInt("ServiceQSmall_default"));
			services.setQLarge(readDataFromFileInt("ServiceQLarge_default"));
		} catch (Exception e) {
			printer.print("Nem sikerült a betöltés.");
		}
	}

	public void autoSave(Materials materials, Services services) {
		String lastSavedNum = "";
		try {
			lastSavedNum = readTextFromFile("lastSavedNum").replaceAll("[^0-9. ]", "");
		} catch (Exception e) {
		}
		try {
			saveAll(materials, services, lastSavedNum);
			printer.print(String.format("Automatikus mentés.%n"));
			writeTextToFile("lastAutoSavedDate", date.provideDateString());
		} catch (Exception e) {
			System.out.println("Nem sikerült az automatikus mentés.");
		}
	}

	public void saveAs(Materials materials, Services services) {
		int lastSavedNum = Integer.MIN_VALUE;
		String lastSavedDate = "";
		try {
			lastSavedNum = readDataFromFileInt("lastSavedNum")[0];
			lastSavedDate = readTextFromFile("lastSavedDate");
		} catch (Exception e) {
			System.out.println(String.format("Nem volt még mentett sorzsám.%n"));
		}
		try {
			if (lastSavedNum != -Integer.MIN_VALUE) {
				System.out.printf("A legutóbbi munkamenet sorszáma: %d.%n", lastSavedNum);
				System.out.printf("A legutóbbi mentés dátuma: %s%n", lastSavedDate);
			}
			int fileNumber = input.askInputInt("Milyen sorszámmal mentsem a munkamenetet: ");
			saveAll(materials, services, "" + fileNumber);
			printer.print(String.format("A mentett file sorszáma: %d%n", fileNumber));
			saveDataToFileInt("lastSavedNum", new int[] { fileNumber });
			writeTextToFile("lastSavedDate", date.provideDateString());
			updateWorkSessionList(fileNumber);
		} catch (Exception e) {
			System.out.println("Nem sikerült a mentés.");
		}
	}

	public void loadServiceRatios(Services services, String fileNumber) {
		services.setsLRatio(readDataFromFileDouble("ServiceRatios" + fileNumber));
	}

	public void loadServiceQLarge(Services services, String fileNumber) {
		services.setQLarge(readDataFromFileInt("ServiceQLarge" + fileNumber));
	}

	public void loadServiceQSmall(Services services, String fileNumber) {
		services.setQSmall(readDataFromFileInt("ServiceQSmall" + fileNumber));
	}

	public void loadMaterialQuants(Materials materials, String fileNumber) {
		materials.setQuants(readDataFromFileDouble("MaterialQ" + fileNumber));
	}

	public void loadMaterialUnits(Materials materials, String fileNumber) {
		materials.setUnits(readDataFromFileInt("MaterialUnits" + fileNumber));
	}

	public void loadMaterialPrices(Materials materials, String fileNumber) {
		materials.setPrices(readDataFromFileInt("MaterialP" + fileNumber));
	}

	public String loadLastSavedNumber() {
		return readTextFromFile("lastSavedNum").replaceAll("[^0-9. ]", "");
	}

	private void loadAll(Materials materials, Services services, String fileNumber) {
		loadMaterialPrices(materials, fileNumber);
		loadMaterialUnits(materials, fileNumber);
		loadMaterialQuants(materials, fileNumber);
		loadServiceQSmall(services, fileNumber);
		loadServiceQLarge(services, fileNumber);
		loadServiceRatios(services, fileNumber);
	}

	private void saveAll(Materials materials, Services services, String fileNumber) {
		saveDataToFileInt("MaterialP" + fileNumber, materials.getPrices());
		saveDataToFileInt("MaterialUnits" + fileNumber, materials.getUnits());
		saveDataToFileDouble("MaterialQ" + fileNumber, materials.getQuants());
		saveDataToFileInt("ServiceQSmall" + fileNumber, services.getQSmall());
		saveDataToFileInt("ServiceQLarge" + fileNumber, services.getQLarge());
		saveDataToFileDouble("ServiceRatios" + fileNumber, services.getsLRatio());
	}

	private double[] readDataFromFileDouble(String filename) {
		String[] Q = readTextFromFile(filename).replaceAll("[^0-9. ]", "").split(" ");
		return Arrays.stream(Q).mapToDouble(Double::parseDouble).toArray();
	}

	private int[] readDataFromFileInt(String filename) {
		String[] Q = readTextFromFile(filename).replaceAll("[^0-9 ]", "").split(" ");
		return Arrays.stream(Q).mapToInt(Integer::parseInt).toArray();
	}

	private void saveDataToFileDouble(String filename, double[] data) {
		writeTextToFile(filename, Arrays.toString(data));
	}

	private void saveDataToFileInt(String filename, int[] data) {
		writeTextToFile(filename, Arrays.toString(data));
	}

	private String readTextFromFile(String source) {
		try {
			return Files.readString(Paths.get(source), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Hiba történt fájl olvasáskor: " + e.getMessage());
			return null;
		}
	}

	private void writeTextToFile(String fileName, String text) {
		try {
			Files.writeString(Paths.get(fileName), text, StandardCharsets.UTF_8);
		} catch (Exception ex) {
			System.err.println("Hiba történt fájl írásakor: " + ex.getMessage());
		}
	}

}
