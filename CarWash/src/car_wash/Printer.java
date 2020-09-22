package car_wash;

class Printer {

	void printNames(String[] names) {
		System.out.println();
		for (int i = 0; i < names.length; i++) {
			System.out.println(1 + i + ". " + names[i]);
		}
	}

	void printChangeInt(String name, int[] changeInfo) {
		System.out.printf("Az eddigi %s %,d volt.%n", name, changeInfo[0]);
		System.out.printf("A módosított %s %,d lett.%n", name, changeInfo[1]);
	}

	void printChangeDouble(String name, double[] changeInfo) {
		System.out.printf("Az eddigi %s %,.2f volt.%n", name, changeInfo[0]);
		System.out.printf("A módosított %s %,.2f lett.%n", name, changeInfo[1]);
	}

	void printPrices(int[] prices) {
		System.out.printf("%nAz áradatok a következők: %n");
		for (int i = 0; i < Materials.getMaterialNames().length; i++) {
			System.out.printf("%s, amely jelenlegi ára: %,d Ft/liter.%n", Materials.getMaterialNames()[i], prices[i]);
		}
	}

	void printData(Materials materials, Services services) {
		System.out.printf("%nA betöltött adatok a következők: %n");
		System.out.printf("%nAz eddig mentett anyagfogyás: %n");
		for (int i = 0; i < Materials.getMaterialNames().length; i++) {
			System.out.printf("%s: %,.2f liter, amely jelenlegi ára: %,d Ft/liter.%n", Materials.getMaterialNames()[i], materials.getQuant()[i],
					materials.getPrices()[i]);
		}
		System.out.printf("%nAz eddig mentett szolgáltatások: %n");
		for (int i = 0; i < Services.getServiceNames().length; i++) {
			System.out.println(
					Services.getServiceNames()[i] + ": " + services.getQSmall()[i] + " kis autó, és " + services.getQLarge()[i] + " nagy autó.");
		}
	}

	void printConsumption(Materials materials, Services services) {
		var input = new UserInput();
		printNames(Materials.getMaterialNames());
		int choice = input.askInputIntWithLimits("Az anyag: ", Materials.getMaterialNames().length) - 1;
		printCalculations(choice, new Data(materials, services));
	}

	void printAllConsumption(Materials materials, Services services) {
		System.out.println();
		for (int i = 0; i < Materials.getMaterialNames().length; i++) {
			printCalculations(i, new Data(materials, services));
		}
		printHistogram(materials);
	}
	
	private void printCalculations(int choice, Data data) {
		var cal = new Calculator();
		Materials materials = data.getMaterials();
		Services services = data.getServices();
		System.out.printf("%n%s%s.", "Az anyag: ", Materials.getMaterialNames()[choice]);
		System.out.printf("%n%s%s", "Az anyag kiszerelése: ", materials.addUnit(choice));
		System.out.printf("%n%s%s.", "Az anyaghoz kapcsolódó szolgáltatás: ", Services.getServiceNames()[choice]);
		System.out.printf("%n%s%.2f.", "A szolgáltatás kis/nagy autó anyagráfordítás aránya: ", services.getsLRatio() [choice]);
		System.out.printf("%n%s%s.", "Az összes szolgáltatás kis autóra: ", services.getQSmall()[choice]);
		System.out.printf("%n%s%s.", "Az összes szolgáltatás nagy autóra: ", services.getQLarge()[choice]);
		System.out.printf("%n%s%41.2f%s", "Az összes anyagfogyás: ", materials.getQuant()[choice], " liter.");
		System.out.printf("%n%s%13.2f%s", "Az anyagfogyás egy szolgáltatás esetén kis autóra: ", cal.calculateMaterialPerServiceNormal(choice, data),
				" liter.");
		System.out.printf("%n%s%12.2f%s%n", "Az anyagfogyás egy szolgáltatás esetén nagy autóra: ",
				cal.calculateMaterialPerServiceLarge(choice, data), " liter.");
		System.out.printf("%s%,47d%s%n", "Az anyag ára: ", materials.getPrices(choice), " Ft/liter.");
		System.out.printf("%s%,9.0f Ft.%n", "Az anyagköltség egy szolgáltatás esetén kis autóra: ", cal.calculateCostPerServiceNormal(choice, data));
		System.out.printf("%s%,8.0f Ft.%n", "Az anyagköltség egy szolgáltatás esetén nagy autóra: ", cal.calculateCostPerServiceLarge(choice, data));
	}


	void printHistogram(Materials materials) {
		System.out.printf("%nAnyagfelhasználás ebben az időszakban:%n");
		for (int i = 0; i < Materials.getMaterialNames().length; i++) {
			int lineLength = materials.getQuant(i) > 0 ? (int) materials.getQuant(i) : 0;
			System.out.printf("%20s: %s %.2f liter (%.0f kiszerelés)%n", Materials.getMaterialNames()[i], "\u2592".repeat(lineLength / getDivisor(materials)),
					materials.getQuant(i), Math.ceil(materials.getQuant(i)/materials.getUnits()[i]*1000));
		}
		System.out.println();
	}
	
	private int getDivisor(Materials material) {
		double quantMax = 0;
		for (int i = 0; i < Materials.getMaterialNames().length; i++) {
			quantMax = Math.max(quantMax, material.getQuant(i));
		}
		return (quantMax<=100) ? 1 : (int) quantMax/50;
	}

	void printMenu() {
		System.out.printf("%nKérem, válasszon az alábbi lehetőségek közül:%n");
		System.out.println("1. Szolgáltatás bevitele");
		System.out.println("2. Anyagrendelés bevitele");
		System.out.println("3. Készletváltozás bevitele");
		System.out.println("4. Anyagfelhasználás kiszámítása (egy anyagra)");
		System.out.println("5. Anyagfelhasználás kiszámítása az összes anyagra");
		System.out.println("6. Mentés másként");
		System.out.println("7. Munkamenet betöltése");
		System.out.println("8. Új munkamenet indítása");
		System.out.println("9. Extrák");
		System.out.println("* Kilépés");
	}
	
	void printExtras() {
		System.out.printf("%nKérem, válasszon az alábbi lehetőségek közül:%n");	
		System.out.println("1. Anyagfelhasználási arány pontosítása");
		System.out.println("2. Adatok összegzése mentett file-ok alapján");
		System.out.println("3. Anyagárak módosítása");
		System.out.println("4. Kiszerelések módosítása");
		System.out.println("5. Beépített árak visszatöltése");
		System.out.println("6. Beépített arányok visszatöltése");
		System.out.println("* Mégsem");
	}

	void printExit() {
		System.out.printf("%nViszlát!%n");
	}

	void printGreetings() {
		System.out.printf("%n%nÜdvözlöm!%n");
	}

	void printUnsuccessful() {
		System.out.println("Sikertelen művelet!");
	}

	void printSuccessfulSave(String fileName) {
		System.out.printf("A program mentette az adatokat! %s%n", fileName);
	}

	void print(String text) {
		System.out.printf("%s", text);
	}

}
