package car_wash_menu;

import car_wash_register.Materials;
import car_wash_register.Services;

public class ChangePrice extends AbsMenu {
	
	{
		menuName = "Anyagárak módosítása";
	}

	public void menuAction(Materials materials, Services services) {
		materials.changePrice();
	}

}
