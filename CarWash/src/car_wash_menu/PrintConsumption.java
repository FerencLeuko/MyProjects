package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Printer;

public class PrintConsumption extends AbsMenu {
	
	private Printer printer = new Printer();

	{
		menuName = "Anyagfelhasználás kiszámítása (egy anyagra)";
	}

	public void menuAction(Materials materials, Services services) {
		printer.printConsumption(materials, services);
	}
}
