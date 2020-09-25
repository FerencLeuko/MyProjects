package car_wash_menu;

import car_wash_register.Materials;
import car_wash_register.Services;

public class SaveAs extends AbsMenu {

	{
		menuName = "Mentés másként";
	}

	public void menuAction(Materials materials, Services services) {
		file.saveAs(materials, services);
	}

}
