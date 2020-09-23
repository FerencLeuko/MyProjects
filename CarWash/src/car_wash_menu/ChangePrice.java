package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;

public class ChangePrice extends AbsMenu {
	
	{
		menuName = "Anyagárak módosítása";
	}

	public void menuAction(Materials materials, Services services) {
		materials.changePrice();
	}

}
