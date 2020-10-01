package car_wash.main;

import car_wash.register.Materials;
import car_wash.register.Services;

public interface Menu {
	
	public static final String EXTRAS_NAME = "Extrák";
	public final FileManager file = FileManager.getFileManager();
	
	public String getName();
	
	public void menuAction(Materials materials, Services services);

}
