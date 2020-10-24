package car_wash.main.menu;

import car_wash.main.Printer;
import car_wash.register.Materials;
import car_wash.register.Services;

public class PrintAllConsumption extends AbsMenu {
	
	private Printer printer = new Printer();

	{
		menuName = "Anyagfelhasználás kiszámítása az összes anyagra";
	}

	public void menuAction(Materials materials, Services services) {
		printer.printAllConsumption(materials, services);
	}
}
