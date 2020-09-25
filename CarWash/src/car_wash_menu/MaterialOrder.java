package car_wash_menu;

import car_wash_register.Materials;
import car_wash_register.Services;

public class MaterialOrder extends AbsMenu {
	
	{
		menuName = "Anyagrendelés bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		materials.addMaterialOrdered();
	}

}
