package car_wash_menu;

import car_wash_main.Printer;
import car_wash_register.Materials;
import car_wash_register.Services;

public class DefaultRatio extends AbsMenu {
	
	private Printer printer = new Printer();
	
	{
		menuName = "Beépített arányok visszatöltése";
	}

	public void menuAction(Materials materials, Services services) {
		file.loadDefaultRatios(services);
		printer.print(String.format("A beépített arányok (%.2f) visszaállítva.%n", services.getsLRatio()[0]));
	}

}
