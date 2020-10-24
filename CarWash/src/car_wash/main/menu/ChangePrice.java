package car_wash.main.menu;

import car_wash.register.Materials;
import car_wash.register.Services;

class ChangePrice extends AbsMenu {
	
	{
		menuName = "Anyagárak módosítása";
	}

	public void menuAction(Materials materials, Services services) {
		materials.changePrice();
	}

}
