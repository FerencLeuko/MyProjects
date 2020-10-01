package car_wash_menu;

import car_wash_register.Materials;
import car_wash_register.Services;

public class NewSession extends AbsMenu {

	{
		menuName = "Új munkamenet indítása";
	}

	public void menuAction(Materials materials, Services services) {
		file.startNewWorkSession(materials, services);
	}

}
