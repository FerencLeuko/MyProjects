package car_wash.main.menu;

import car_wash.register.Materials;
import car_wash.register.Services;

public class MaterialOrder extends AbsMenu {
	
	{
		menuName = "Anyagrendelés bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		materials.addMaterialOrdered();
	}

}
