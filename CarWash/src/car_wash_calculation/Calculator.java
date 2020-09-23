package car_wash;

class Calculator {

	double calculateMaterialPerServiceNormal(int serviceNumber, Data data) {
		Materials materials = data.getMaterials();
		Services services = data.getServices();
		return materials.getQuant(serviceNumber) / //
				(services.getQSmall(serviceNumber) + services.getQLarge(serviceNumber) * services.getsLRatio()[serviceNumber]);
	}

	double calculateMaterialPerServiceLarge(int serviceNumber, Data data) {
		Services services = data.getServices();
		return calculateMaterialPerServiceNormal (serviceNumber, data)* services.getsLRatio()[serviceNumber];
	}

	double calculateCostPerServiceNormal(int serviceNumber, Data data) {
		Materials materials = data.getMaterials();
		return calculateMaterialPerServiceNormal(serviceNumber, data) * materials.getPrices(serviceNumber);
	}

	double calculateCostPerServiceLarge(int serviceNumber, Data data) {
		Services services = data.getServices();
		return calculateCostPerServiceNormal(serviceNumber, data) * services.getsLRatio()[serviceNumber];
	}

}
