package car_wash_main;

import car_wash_register.Materials;
import car_wash_register.Services;

public interface Menu {
	
	public static final String EXTRAS_NAME = "Extrák";
	public final FileManager file = FileManager.getFileManager();
	
	public String getName();
	
	public void menuAction(Materials materials, Services services);

}
