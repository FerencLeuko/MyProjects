package game;

public interface Checker {
	
	public GameResult completeGameCheck (Square[][] board, int[] prevRowCol);
	
	public GameResult checkMarks(Square[][] board, int[] prev);

}
