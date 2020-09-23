package car_wash_main;

import car_wash_data.Materials;
import car_wash_data.Services;

public interface Menu {
	
	public String getName();
	
	public void menuAction(Materials materials, Services services);

}
