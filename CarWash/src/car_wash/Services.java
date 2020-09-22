package car_wash;

class Services {

	private static final String[] SERVICE_NAMES = new String[8];
	double[] sLRatio = new double[8];
	private int[] QSmall = new int[8];
	private int[] QLarge = new int[8];
	private UserInput input = new UserInput();
	private Printer printer = new Printer();

	static {
		SERVICE_NAMES[0] = "Polírozás";
		SERVICE_NAMES[1] = "Nanokerámiás kezelés";
		SERVICE_NAMES[2] = "Kárpittisztítás";
		SERVICE_NAMES[3] = "Padlótisztítás";
		SERVICE_NAMES[4] = "Mosás";
		SERVICE_NAMES[5] = "Légtérfrissítés";
		SERVICE_NAMES[6] = "Waxolás";
		SERVICE_NAMES[7] = "Motormosás";

	}

	{
		sLRatio[0] = 2.0;
		sLRatio[1] = 2.0;
		sLRatio[2] = 2.0;
		sLRatio[3] = 2.0;
		sLRatio[4] = 2.0;
		sLRatio[5] = 2.0;
		sLRatio[6] = 2.0;
		sLRatio[7] = 2.0;

		for (int i = 0; i < SERVICE_NAMES.length; i++) {
			QSmall[i] = 0;
			QLarge[i] = 0;
		}
	}

	void addServiceSold() {
		printer.print(String.format("Eladott szolgáltatások hozzáadása:%n"));
		printer.printNames(SERVICE_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputWithExitAndLimits("A szolgáltatás: ", SERVICE_NAMES.length);
		if (choice != Main.EXIT_VALUE) {
			choice--;
			int change = input.askInputInt("Az eladott szolgáltatás mennyisége: ");
			int car = input.askInputIntWithLimits("1. kis autó vagy 2. nagy autó : ", 2);
			if (car == 2) {
				printer.printChangeInt(SERVICE_NAMES[choice] + " nagy autóra", new int[] { QLarge[choice], QLarge[choice] + change });
				QLarge[choice] += change;
			} else if (car == 1) {
				printer.printChangeInt(SERVICE_NAMES[choice] + " kis autóra", new int[] { QSmall[choice], QSmall[choice] + change });
				QSmall[choice] += change;
			} else {
				printer.printUnsuccessful();
			}
		}
	}

	static String[] getServiceNames() {
		return SERVICE_NAMES;
	}

	int[] getQSmall() {
		return QSmall;
	}

	int getQSmall(int index) {
		return QSmall[index];
	}

	void setQSmall(int[] serviceQSmall) {
		this.QSmall = serviceQSmall;
	}

	void setQSmall(int serviceQSmall, int index) {
		this.QSmall[index] = serviceQSmall;
	}

	int[] getQLarge() {
		return QLarge;
	}

	int getQLarge(int index) {
		return QLarge[index];
	}

	void setQLarge(int[] serviceQLarge) {
		this.QLarge = serviceQLarge;
	}

	void setQLarge(int serviceQLarge, int index) {
		this.QLarge[index] = serviceQLarge;
	}

	double[] getsLRatio() {
		return sLRatio;
	}

	void setsLRatio(double[] sLRatio) {
		this.sLRatio = sLRatio;
	}

	void setsLRatio(double sLRatio, int index) {
		this.sLRatio[index] = sLRatio;
	}

}
