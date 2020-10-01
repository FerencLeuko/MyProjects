package car_wash_main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {

	public static final Scanner SCANNER = new Scanner(System.in);
	private Printer printer = new Printer();

	public int askInputInt(String text) {
		int input;
		while (true) {
			try {
				printer.print(text);
				String inputString = SCANNER.nextLine();
				input = Integer.parseInt(inputString);
				return input;
			} catch (NumberFormatException e) {
				printer.print(String.format("Nem sikerült. Kérem egész számot adjon meg!%n"));
			} catch (InputMismatchException e) {
				printer.print(String.format("Nem sikerült. Kérem egy számot adjon meg!%n"));
			}
		}
	}

	public int askInputIntWithLimits(String text, int max) {
		int input = 0;
		do {
			try {
				printer.print(text);
				String inputString = SCANNER.nextLine();
				input = Integer.parseInt(inputString);
			} catch (NumberFormatException e) {
				printer.print(String.format("Nem sikerült. Kérem egy egész számot adjon meg!%n"));
			} catch (InputMismatchException e) {
				printer.print(String.format("Nem sikerült. Kérem egy számot adjon meg!%n"));
			}
		} while (input > max || input < 1);
		return input;
	}

	public int askInputIntWithLimitsAndExitOption(String text, int max) {
		int input = 0;
		do {
			try {
				printer.print(text);
				String inputString = SCANNER.nextLine();
				if (!inputString.isEmpty() && inputString.charAt(0) == Main.EXIT_CHAR) {
					return Main.EXIT_VALUE;
				}
				input = Integer.parseInt(inputString);
			} catch (NumberFormatException e) {
				printer.print(String.format("Nem sikerült. Kérem egy egész számot adjon meg!%n"));
			} catch (InputMismatchException e) {
				printer.print(String.format("Nem sikerült. Kérem egy számot adjon meg!%n"));
			}
		} while (input > max || input < 1);
		return input;
	}

	public double askInputDouble(String text) {
		double input;
		while (true) {
			try {
				printer.print(text);
				String inputString = SCANNER.nextLine();
				input = Double.parseDouble(inputString);
				return input;
			} catch (NumberFormatException e) {
				printer.print(String.format("Nem sikerült. Kérem egy egész vagy egy tört számot (pl 1.5) adjon meg!%n"));
			} catch (InputMismatchException e) {
				printer.print(String.format("Nem sikerült. Kérem egy számot adjon meg!%n"));
			}
		}
	}

}
