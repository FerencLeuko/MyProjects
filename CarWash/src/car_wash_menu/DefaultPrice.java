package car_wash_menu;

import car_wash_main.Printer;
import car_wash_register.Materials;
import car_wash_register.Services;

public class DefaultPrice extends AbsMenu {
	
	private Printer printer = new Printer();
	
	{
		menuName = "Beépített árak visszatöltése";
	}

	public void menuAction(Materials materials, Services services) {
		file.loadDefaultPrices(materials);
		printer.printPrices(materials.getPrices());
	}

}
