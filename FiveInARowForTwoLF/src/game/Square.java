package game;


public enum Square {
	PLAYER_ONE("o"),
	PLAYER_TWO("x"),
	BACKGROUND(" "),
	;
	
	private String playerMark;
	
	private Square(String playerMark) {
		this.playerMark = playerMark;
	}
	
	public String getPlayerMark() {
		return playerMark;
	}
	
}