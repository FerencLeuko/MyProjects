package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Main;

public class SaveAs extends AbsMenu {

	{
		menuName = "Mentés másként";
	}

	public void menuAction(Materials materials, Services services) {
		Main.file.saveAs(materials, services);
	}

}
