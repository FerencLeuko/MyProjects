package car_wash.main.menu;

import car_wash.register.Materials;
import car_wash.register.Services;

public class SaveAs extends AbsMenu {

	{
		menuName = "Mentés másként";
	}

	public void menuAction(Materials materials, Services services) {
		file.saveAs(materials, services);
	}

}
