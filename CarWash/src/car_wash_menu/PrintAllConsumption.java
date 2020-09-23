package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Printer;

public class PrintAllConsumption extends AbsMenu {
	
	private Printer printer = new Printer();

	{
		menuName = "Anyagfelhasználás kiszámítása az összes anyagra";
	}

	public void menuAction(Materials materials, Services services) {
		printer.printAllConsumption(materials, services);
	}
}
