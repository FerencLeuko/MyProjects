package car_wash_menu;

import car_wash_main.Printer;
import car_wash_register.Materials;
import car_wash_register.Services;

public class PrintAllConsumption extends AbsMenu {
	
	private Printer printer = new Printer();

	{
		menuName = "Anyagfelhasználás kiszámítása az összes anyagra";
	}

	public void menuAction(Materials materials, Services services) {
		printer.printAllConsumption(materials, services);
	}
}
