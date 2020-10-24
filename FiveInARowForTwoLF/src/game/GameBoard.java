package game;

public class GameBoard {

	private Square[][] board;
	private final int numRow;
	private final int numCol;

	private GameBoard(int numRow, int numCol) {
		this.board = new Square[numRow][numCol];
		this.numRow = numRow;
		this.numCol = numCol;
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numCol; j++) {
				board[i][j] = Square.BACKGROUND;
			}
		}
	}

	public static GameBoard getInstance(int numRow, int numCol) {
		return new GameBoard(numRow, numCol);
	}

	public Square[][] getBoard() {
		return board;
	}

	public void setBoard(Square[][] board) {
		this.board = board;
	}

	public void setSquare(Square player, int row, int col) {
		this.board[row][col] = player;
	}

	public Square getSquare(int row, int col) {
		return board[row][col];
	}

	public int getNumRow() {
		return numRow;
	}

	public int getNumCol() {
		return numCol;
	}
	
	public void printBoard() {
		System.out.print("  ");
		for (int j = 0; j < numCol; j++) {
			System.out.print(createLabel(j));
		}
		System.out.println();
		for (int i = 0; i < numRow; i++) {
			System.out.printf("%s", i + 1<10 ? " "+ (i+1) : "" + (i+1));
			for (int j = 0; j < numCol; j++) {
				
					System.out.print(board[i][j].getPlayerMark());
			}
			System.out.println();
		}
	}

	public void printBoardPretty() {
		System.out.print("  ");
		for (int j = 0; j < numCol; j++) {
			System.out.printf(" %s", createLabel(j));
		}
		System.out.printf("%n  +%s%n", "-+".repeat(numCol));
		for (int i = 0; i < numRow; i++) {
			System.out.printf("%s|", i + 1<10 ? " "+ (i+1) : "" + (i+1));
			for (int j = 0; j < numCol; j++) {
				if (board[i][j] == null) {
					System.out.print(" |");
				} else {
					System.out.print(board[i][j].getPlayerMark()+"|");
				}

			}
			System.out.printf("%n  +%s%n", "-+".repeat(numCol));
		}
	}

	private static String createLabel(int n) {
		String label = "";
		for (; (n + 26) / 26 > 0; n /= 26, n--) {
			label = Character.toString((char) (n % 26 + 65)) + label;
		}
		return label;
	}

}
