package checker;

import game.Checker;
import game.GameResult;
import game.Square;
import manager.Settings;

public class CheckerCenter implements Checker {

	int addRow;
	int addCol;
	private final Settings settings;
	private final CheckerFactory checkerFactory;
	
	public CheckerCenter  (Settings settings) {
		this.settings = settings;
		checkerFactory = new CheckerFactory(settings);
	}

	@Override
	public GameResult completeGameCheck(Square[][] board, int[] prevRowCol) {
		GameResult result = checkDirections(board, prevRowCol);
		if (result != null) {
			return result;
		} else if (checkFullBoard(board)) {
			return GameResult.TIE;
		}
		return null;
	}

	public GameResult checkDirections(Square[][] board, int[] prevRowCol) {
		GameResult result = null;
		Checker[] checkers = checkerFactory.getCheckers();
		for (Checker checkerDirection : checkers) {
			result = checkerDirection.checkMarks(board, prevRowCol);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public GameResult checkMarks(Square[][] board, int[] prevRowCol) {
		Square player = board[prevRowCol[0]][prevRowCol[1]];
		if (checkFromLastMark(board, prevRowCol) >= settings.getAmobaLength()) {
			return declareWinner(player);
		}
		return null;
	}

	private int checkFromLastMark(Square[][] board, int[] lastPos) {
		Square player = board[lastPos[0]][lastPos[1]];
		int counter = 0;
		int x = lastPos[0];
		int y = lastPos[1];
		while (!outside(board, new int[] { x, y }) && board[x][y].equals(player)) {
			x += addRow;
			y += addCol;
			counter++;
		}
		x = lastPos[0];
		y = lastPos[1];
		while (!outside(board, new int[] { x, y }) && board[x][y].equals(player)) {
			x -= addRow;
			y -= addCol;
			counter++;
		}
		return counter - 1;
	}

	private GameResult declareWinner(Square player) {
		return player.equals(Square.PLAYER_ONE) ? GameResult.PLAYER_ONE_WINS : GameResult.PLAYER_TWO_WINS;
	}

	private boolean outside(Square[][] board, int[] pos) {
		return pos[0] < 0 || pos[1] < 0 || pos[0] >= settings.getNumRow() || pos[1] >= settings.getNumCol();
	}

	private boolean checkFullBoard(Square[][] board) {
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[1].length; j++) {
				if (board[i][j].equals(Square.BACKGROUND)) {
					return false;
				}
			}
		}
		return true;
	}

}
