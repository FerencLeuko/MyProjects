package car_wash.main.menu;

import car_wash.register.Materials;
import car_wash.register.Services;

public class LoadAs extends AbsMenu {

	{
		menuName = "Munkamenet betöltése";
	}

	public void menuAction(Materials materials, Services services) {
		FILE.loadAs(materials, services);
	}

}
