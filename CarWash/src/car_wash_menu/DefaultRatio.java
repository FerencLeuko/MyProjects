package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Main;
import car_wash_main.Printer;

public class DefaultRatio extends AbsMenu {
	
	private Printer printer = new Printer();
	
	{
		menuName = "Beépített arányok visszatöltése";
	}

	public void menuAction(Materials materials, Services services) {
		Main.file.loadDefaultRatios(services);
		printer.print(String.format("A beépített arányok (%.2f) visszaállítva.%n", services.getsLRatio()[0]));
	}

}
