package car_wash.main.menu;

import car_wash.register.Materials;
import car_wash.register.Services;

public class ChangeUnit extends AbsMenu {
	
	{
		menuName = "Kiszerelések módosítása";
	}

	public void menuAction(Materials materials, Services services) {
		materials.changeUnits();
	}

}
