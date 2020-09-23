package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Main;

public class LoadAs extends AbsMenu {

	{
		menuName = "Munkamenet betöltése";
	}

	public void menuAction(Materials materials, Services services) {
		Main.file.loadAs(materials, services);
	}

}
