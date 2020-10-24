package car_wash.main.menu;

import car_wash.register.Materials;
import car_wash.register.Services;

public class NewSession extends AbsMenu {

	{
		menuName = "Új munkamenet indítása";
	}

	public void menuAction(Materials materials, Services services) {
		FILE.startNewWorkSession(materials, services);
	}

}
