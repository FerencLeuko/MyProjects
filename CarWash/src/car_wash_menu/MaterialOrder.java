package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;

public class MaterialOrder extends AbsMenu {
	
	{
		menuName = "Anyagrendelés bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		materials.addMaterialOrdered();
	}

}
