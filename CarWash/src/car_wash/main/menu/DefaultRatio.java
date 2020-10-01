package car_wash.main.menu;

import car_wash.main.Printer;
import car_wash.register.Materials;
import car_wash.register.Services;

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
