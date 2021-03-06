package car_wash.calculation;

import car_wash.register.AllData;
import car_wash.register.Materials;
import car_wash.register.Services;

public class Calculator {

	public double calculateMaterialPerServiceNormal(int serviceNumber, AllData data) {
		Materials materials = data.getMaterials();
		Services services = data.getServices();
		return materials.getQuant(serviceNumber) / //
				(services.getQSmall(serviceNumber) + services.getQLarge(serviceNumber) * services.getsLRatio()[serviceNumber]);
	}

	public double calculateMaterialPerServiceLarge(int serviceNumber, AllData data) {
		Services services = data.getServices();
		return calculateMaterialPerServiceNormal (serviceNumber, data)* services.getsLRatio()[serviceNumber];
	}

	public double calculateCostPerServiceNormal(int serviceNumber, AllData data) {
		Materials materials = data.getMaterials();
		return calculateMaterialPerServiceNormal(serviceNumber, data) * materials.getPrices(serviceNumber);
	}

	public double calculateCostPerServiceLarge(int serviceNumber, AllData data) {
		Services services = data.getServices();
		return calculateCostPerServiceNormal(serviceNumber, data) * services.getsLRatio()[serviceNumber];
	}

}
