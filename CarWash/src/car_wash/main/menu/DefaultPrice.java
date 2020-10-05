package car_wash.main.menu;

import car_wash.main.Printer;
import car_wash.register.Materials;
import car_wash.register.Services;

public class DefaultPrice extends AbsMenu {
	
	private Printer printer = new Printer();
	
	{
		menuName = "Beépített árak visszatöltése";
	}

	public void menuAction(Materials materials, Services services) {
		FILE.loadDefaultPrices(materials);
		printer.printPrices(materials.getPrices());
	}

}
