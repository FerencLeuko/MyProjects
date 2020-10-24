package game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import checker.CheckerCenter;
import manager.Game;
import manager.Settings;
import manager.Storage;

public class GameProcess implements Game, Storage {

	private final Settings settings;
	private final Checker checker;
	private GameBoard gameBoard;
	private Square currentPlayer;
	private String prompt;
	private String input;
	private int[] prevRowCol;
	private boolean prevUndo;
	private boolean gameEnd;
	private GameResult gameResult;
	private StorageDataTranslator translator = new StorageDataTranslator();

	public GameProcess(Settings settings) {
		this.settings = settings;
		gameBoard = GameBoard.getInstance(settings.getNumRow(), settings.getNumCol());
		checker = new CheckerCenter(settings);
	}

	{
		currentPlayer = Square.PLAYER_ONE;
		prompt = "> ";
		prevRowCol = new int[2];
		prevUndo = true;
	}

	public void runGame() {
		gameBoard.printBoard();
		do {
			System.out.print(prompt);
			input = USER.askInput();
			if (input.startsWith(settings.getExitGame())) {
				break;
			}
			processInput();
		} while (!gameEnd);
		evaluateResult(gameResult);
	}

	private void processInput() {
		if (input.startsWith(settings.getBACK())) {
			if (settings.isBackValid()) {
				undo();
			} else {
				System.out.println("A visszalépési lehetőség nincs beállítva.");
			}
		} else if (input.startsWith(settings.getSAVE())) {
			saveGame();
		} else {
			executeTurn();
		}
	}

	private void executeTurn() {
		try {
			updateBoard(input.toLowerCase());
			gameBoard.printBoard();
			gameheck();
			switchPlayers();
			prevUndo = false;
		} catch (RuntimeException e) {
			System.out.println("Téves koordináták, kérem adja meg újra!");
		}
	}

	private void saveGame() {
		String fileName = input.replace(settings.getSAVE(), "");
		if (fileNameCheck(fileName)) {
			saveGame(fileName);
		} else {
			System.out.println("A megadott file név nem érvényes.");
		}
	}

	private boolean fileNameCheck(String fileName) {
		return fileName.matches("[\\p{L}\\d]{1}[\\p{L}\\d-]{0,38}[\\p{L}\\d]{1}");
	}

	private void undo() {
		if (tryUndo()) {
			switchPlayers();
			gameBoard.printBoard();
		} else {
			System.out.println("Nem lehetséges visszalépni.");
		}
	}

	private boolean tryUndo() {
		if (prevRowCol != null && !prevUndo) {
			gameBoard.setSquare(Square.BACKGROUND, prevRowCol[0], prevRowCol[1]);
			prevUndo = true;
			return true;
		} else {
			return false;
		}
	}

	private void updateBoard(String input) {
		int[] rowCol = getRowCol(input);
		if (!gameBoard.getSquare(rowCol[0], rowCol[1]).equals(Square.BACKGROUND)) {
			throw new IllegalArgumentException();
		}
		gameBoard.setSquare(currentPlayer, rowCol[0], rowCol[1]);
		prevRowCol = rowCol;
	}

	private int[] getRowCol(String input) {
		int[] rowCol = new int[2];
		if (input.matches("([a-z]{1,2})([0-9]{1,3})")) {
			Matcher matcher = Pattern.compile("([a-z]{1,2})([0-9]{1,3})").matcher(input);
			if (matcher.find()) {
				rowCol[0] = Integer.parseInt(matcher.group(2)) - 1;
				rowCol[1] = createIndex(matcher.group(1));
			}
			return rowCol;
		} else
			return null;
	}

	private static int createIndex(String label) {
		int index = 0;
		for (int i = 0, j = label.length() - 1; i < label.length(); i++, j--) {
			index += (int) (label.codePointAt(i) - 96) * Math.pow(26.0, (double) j);
		}
		return index - 1;
	}

	private void gameheck() {
		try {
			gameResult = checker.completeGameCheck(gameBoard.getBoard(), prevRowCol);
			if (gameResult != null) {
				gameEnd = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}

	private void switchPlayers() {
		if (currentPlayer == Square.PLAYER_ONE) {
			currentPlayer = Square.PLAYER_TWO;
		} else {
			currentPlayer = Square.PLAYER_ONE;
		}
	}

	private void evaluateResult(GameResult gameResult) {
		if (gameResult != null) {
			if (gameResult.equals(GameResult.TIE)) {
				System.out.printf("%n%s%n", "A játék eredménye döntetlen!");
			} else
				System.out.printf("%n%s%d%s%n", "Gratulálok az ", gameResult.equals(GameResult.PLAYER_ONE_WINS) ? 1 : 2,
						". játékos nyerte meg a játékot!");
			offerSave(gameResult);
		}
		System.out.println("Vége a játéknak.");
	}

	private void offerSave(GameResult gameResult) {
	}

	@Override
	public void saveGame(String fileName) {
		try {
			String data = translator.createData();
			FILE.saveGame(fileName, data);
			System.out.println("A játékállás mentve.");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void loadGameFromList() {
		try {
			for (String fileName : getFileNames().split(", ")) {
				System.out.println(fileName);
			}
			System.out.println("Melyik mentett játékot szeretné betölteni?");
			String data = FILE.loadGame(USER.askInput());
			translator.processData(data);
			runGame();
		} catch (RuntimeException e) {
		}
	}

	@Override
	public String getFileNames() {
		return FILE.getFileNames();
	}

	private class StorageDataTranslator {

		private void processData(String data) {
			String[] values = data.split(",");
			try {
				settings.setFlexValuesFromStorage(data.substring(data.indexOf("#") + 1));
				gameBoard = GameBoard.getInstance(settings.getNumRow(), settings.getNumCol());
				prevRowCol[0] = Integer.parseInt(values[2]);
				prevRowCol[1] = Integer.parseInt(values[3]);
				prevUndo = values[4].equals("true");
				currentPlayer = values[5].equals("PLAYER_ONE") ? Square.PLAYER_ONE : Square.PLAYER_TWO;
				for (int i = 0; i < gameBoard.getNumRow(); i++) {
					for (int j = 0; j < gameBoard.getNumCol(); j++) {
						if (values[i * gameBoard.getNumCol() + j + 6].equals(Square.PLAYER_ONE.getPlayerMark())) {
							gameBoard.setSquare(Square.PLAYER_ONE, i, j);
						} else if (values[i * gameBoard.getNumCol() + j + 6].equals(Square.PLAYER_TWO.getPlayerMark())) {
							gameBoard.setSquare(Square.PLAYER_TWO, i, j);
						} else
							gameBoard.setSquare(Square.BACKGROUND, i, j);
					}
				}
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			}
		}

		private String createData() {
			StringBuilder builder = new StringBuilder();
			builder.append(gameBoard.getNumRow()).append(",");
			builder.append(gameBoard.getNumCol()).append(",");
			builder.append(prevRowCol[0]).append(",");
			builder.append(prevRowCol[1]).append(",");
			builder.append(prevUndo).append(",");
			builder.append(currentPlayer).append(",");
			for (int i = 0; i < gameBoard.getNumRow(); i++) {
				for (int j = 0; j < gameBoard.getNumCol(); j++) {
					builder.append(gameBoard.getSquare(i, j).getPlayerMark()).append(",");
				}
			}
			builder.append("#");
			builder.append(settings.addFlexSetValuesToStorage());
			return builder.toString();
		}

	}

}
