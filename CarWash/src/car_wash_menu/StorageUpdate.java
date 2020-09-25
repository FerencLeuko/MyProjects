package car_wash_menu;

import car_wash_register.Materials;
import car_wash_register.Services;

public class StorageUpdate extends AbsMenu {
	
	{
		menuName = "Készletváltozás bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		materials.updateStorage();
	}

}
