package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Main;

public class NewSession extends AbsMenu {

	{
		menuName = "Új munkamenet indítása";
	}

	public void menuAction(Materials materials, Services services) {
		Main.file.startNewWorkSession(materials, services);
	}

}
