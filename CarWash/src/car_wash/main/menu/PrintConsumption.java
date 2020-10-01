package car_wash.main.menu;

import car_wash.main.Printer;
import car_wash.register.Materials;
import car_wash.register.Services;

public class PrintConsumption extends AbsMenu {
	
	private Printer printer = new Printer();

	{
		menuName = "Anyagfelhasználás kiszámítása (egy anyagra)";
	}

	public void menuAction(Materials materials, Services services) {
		printer.printConsumption(materials, services);
	}
}
