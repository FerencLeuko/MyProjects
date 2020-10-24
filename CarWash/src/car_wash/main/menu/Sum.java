package car_wash.main.menu;

import car_wash.calculation.SumManager;
import car_wash.register.Materials;
import car_wash.register.Services;

public class Sum extends AbsMenu {
	
	{
		menuName = "Adatok összegzése mentett file-ok alapján";
	}

	public void menuAction(Materials materials, Services services) {
		SumManager sum = new SumManager();
		sum.sum();
	}

}
