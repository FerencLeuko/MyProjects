package car_wash.main.menu;

import car_wash.register.Materials;
import car_wash.register.Services;

public class StorageUpdate extends AbsMenu {
	
	{
		menuName = "Készletváltozás bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		materials.updateStorage();
	}

}
