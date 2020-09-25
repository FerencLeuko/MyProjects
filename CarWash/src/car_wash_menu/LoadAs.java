package car_wash_menu;

import car_wash_register.Materials;
import car_wash_register.Services;

public class LoadAs extends AbsMenu {

	{
		menuName = "Munkamenet betöltése";
	}

	public void menuAction(Materials materials, Services services) {
		file.loadAs(materials, services);
	}

}
