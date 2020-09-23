package car_wash_menu;

import car_wash_calculation.SumManager;
import car_wash_data.Materials;
import car_wash_data.Services;

public class Sum extends AbsMenu {
	
	{
		menuName = "Adatok összegzése mentett file-ok alapján";
	}

	public void menuAction(Materials materials, Services services) {
		SumManager sum = new SumManager();
		sum.sum();
	}

}
