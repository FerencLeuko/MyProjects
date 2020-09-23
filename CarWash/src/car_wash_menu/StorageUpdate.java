package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;

public class StorageUpdate extends AbsMenu {
	
	{
		menuName = "Készletváltozás bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		materials.updateStorage();
	}

}
