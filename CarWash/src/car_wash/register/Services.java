package car_wash.register;

import car_wash.main.Printer;
import car_wash.main.Register;
import car_wash.main.UserInput;

public class Services implements Register {

	private static final String[] SERVICE_NAMES = FILE.loadNames("ServiceNames");
	private static final int NUM_SERVICES = SERVICE_NAMES.length;
	private static final double DEFAULT_RATIO;
	private double[] sLRatio = new double[NUM_SERVICES];
	private int[] QSmall = new int[NUM_SERVICES];
	private int[] QLarge = new int[NUM_SERVICES];
	private UserInput input = new UserInput();
	private Printer printer = new Printer();

	static {
		DEFAULT_RATIO = FILE.loadDoubleArray("Ratio_default")[0];
	}

	{
		for (int i = 0; i < NUM_SERVICES; i++) {
			sLRatio[i] = DEFAULT_RATIO;
			QSmall[i] = 0;
			QLarge[i] = 0;
		}

	}

	public void addServiceSold() {
		printer.print(String.format("Eladott szolgáltatások hozzáadása:%n"));
		printer.printNames(SERVICE_NAMES);
		printer.print(String.format("* Mégsem%n"));
		int choice = input.askInputIntWithLimitsAndExitOption("A szolgáltatás: ", SERVICE_NAMES.length);
		if (choice != EXIT_VALUE) {
			choice--;
			int change = input.askInputInt("Az eladott szolgáltatás mennyisége: ");
			int car = input.askInputIntWithLimits("1. kis autó vagy 2. nagy autó : ", 2);
			if (car == 2) {
				printer.printChange(SERVICE_NAMES[choice] + " nagy autóra", new Number[] { QLarge[choice], QLarge[choice] + change });
				QLarge[choice] += change;
			} else if (car == 1) {
				printer.printChange(SERVICE_NAMES[choice] + " kis autóra", new Number[] { QSmall[choice], QSmall[choice] + change });
				QSmall[choice] += change;
			} else {
				printer.printUnsuccessful();
			}
		}
	}

	public static String[] getServiceNames() {
		return SERVICE_NAMES;
	}

	public int[] getQSmall() {
		return QSmall;
	}

	public int getQSmall(int index) {
		return QSmall[index];
	}

	public void setQSmall(int[] serviceQSmall) {
		this.QSmall = serviceQSmall;
	}

	public void setQSmall(int serviceQSmall, int index) {
		this.QSmall[index] = serviceQSmall;
	}

	public int[] getQLarge() {
		return QLarge;
	}

	public int getQLarge(int index) {
		return QLarge[index];
	}

	public void setQLarge(int[] serviceQLarge) {
		this.QLarge = serviceQLarge;
	}

	public void setQLarge(int serviceQLarge, int index) {
		this.QLarge[index] = serviceQLarge;
	}

	public double[] getsLRatio() {
		return sLRatio;
	}

	public void setsLRatio(double[] sLRatio) {
		this.sLRatio = sLRatio;
	}

	public void setsLRatio(double sLRatio, int index) {
		this.sLRatio[index] = sLRatio;
	}

}
