package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;

public class ChangeUnit extends AbsMenu {
	
	{
		menuName = "Kiszerelések módosítása";
	}

	public void menuAction(Materials materials, Services services) {
		materials.changeUnits();
	}

}
