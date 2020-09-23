package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Main;
import car_wash_main.Printer;

public class DefaultPrice extends AbsMenu {
	
	private Printer printer = new Printer();
	
	{
		menuName = "Beépített árak visszatöltése";
	}

	public void menuAction(Materials materials, Services services) {
		Main.file.loadDefaultPrices(materials);
		printer.printPrices(materials.getPrices());
	}

}
