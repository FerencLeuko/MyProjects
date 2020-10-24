package checker;

import game.Checker;
import manager.Settings;

class CheckerFactory {

	private final Settings settings;
	private static Checker[] checkers;

	public CheckerFactory(Settings settings) {
		this.settings = settings;
	}

	public Checker[] getCheckers() {
		if (checkers == null) {
			checkers = new Checker[] { new HorizontalChecker(settings), new DiagonalLeftChecker(settings), new DiagonalRightChecker(settings),
					new VerticalChecker(settings) };
		}
		return checkers;
	}

}
