package manager;

public class Settings {

	private static final int MAX_ROW = 200;
	private static final int MIN_ROW = 3;
	private static final int MAX_COL = 200;
	private static final int MIN_COL = 3;
	private static final int MIN_AMOBA = 3;
	private static final int MAX_AMOBA = 10;
	private static final String EXIT_GAME = "vége";
	private static final String BACK = "vissza";
	private static final String SAVE = "ment ";

	private int numRow;
	private int numCol;
	private int amobaLength;
	private boolean isBackValid;

	{
		numRow = 10;
		numCol = 20;
		amobaLength = 5;
		isBackValid = true;
	}

	private Settings() {
	}

	private static final Settings SETTINGS = new Settings();

	public static Settings getSettings() {
		return SETTINGS;
	}

	public int getNumRow() {
		return numRow;
	}

	public void setNumRow(int numRow) {
		if (numRow < MIN_ROW) {
			this.numRow = MIN_ROW;
		}
		if (numRow > MAX_ROW) {
			this.numRow = MAX_ROW;
		}
		if (numRow < MIN_ROW || numRow > MAX_ROW) {
			throw new AmobaSettingsException("Nem megengedett beállítás.");
		}
		this.numRow = numRow;
	}

	public int getNumCol() {
		return numCol;
	}

	public void setNumCol(int numCol) {
		if (numCol < MIN_COL) {
			this.numRow = MIN_COL;
		}
		if (numCol > MAX_COL) {
			this.numRow = MAX_COL;
		}
		if (numCol < MIN_COL || numCol > MAX_COL) {
			throw new AmobaSettingsException("Nem megengedett beállítás.");
		}
		this.numCol = numCol;
	}

	public int getAmobaLength() {
		return amobaLength;
	}

	public void setAmobaLength(int amobaLength) {
		if (amobaLength < MIN_AMOBA) {
			this.amobaLength = MIN_AMOBA;
		}
		if (amobaLength > MAX_AMOBA) {
			this.amobaLength = MAX_AMOBA;
		}
		if (amobaLength < MIN_AMOBA || amobaLength > MAX_AMOBA || amobaLength > MAX_COL && amobaLength > MAX_ROW) {
			throw new AmobaSettingsException("Nem megengedett beállítás.");
		}
		this.amobaLength = amobaLength;
	}

	public boolean isBackValid() {
		return isBackValid;
	}

	public void setBackValid(boolean isBackValid) {
		this.isBackValid = isBackValid;
	}

	public String getBACK() {
		return BACK;
	}

	public String getSAVE() {
		return SAVE;
	}

	public String getExitGame() {
		return EXIT_GAME;
	}
	
	// for saving game

	public String addFlexSetValuesToStorage() {
		StringBuilder builder = new StringBuilder();
		builder.append(getNumRow()).append(",");
		builder.append(getNumCol()).append(",");
		builder.append(getAmobaLength()).append(",");
		builder.append(isBackValid());
		return builder.toString();
	}
	
	public void setFlexValuesFromStorage(String data) {
		String[] values = data.split(",");
		try {
			setNumRow(Integer.parseInt(values[0]));
		} catch (AmobaSettingsException e) {
		}
		try {
			setNumCol(Integer.parseInt(values[1]));
		} catch (AmobaSettingsException e) {
		}
		try {
			setAmobaLength(Integer.parseInt(values[2]));
		} catch (AmobaSettingsException e) {
		}
		try {
			setBackValid(values[3].equals("true"));
		} catch (AmobaSettingsException e) {
		}
	}

	// for Settings Menu

	private final FlexibleSettings[] flexibleSettings = new FlexibleSettings[] { new NumRow(), new NumCol(), new AmobaLength(), new IsBackValid() };

	public FlexibleSettings[] getFlexSettingsArray() {
		return flexibleSettings;
	}

	public abstract class FlexibleSettings implements FlexibleSettingsSupply {

		String name;
		int value;

		public String getName() {
			return name;
		}

		public abstract int getValue();

		public abstract void setValue(int value);

	}

	public class NumRow extends FlexibleSettings {

		{
			name = "A játéktábla sorainak száma";
		}

		public void setValue(int value) {
			Settings.this.setNumRow(value);
		}

		public int getValue() {
			return Settings.this.getNumRow();
		}

	}

	public class NumCol extends FlexibleSettings {

		{
			name = "A játéktábla oszlopainak száma";
		}

		public void setValue(int value) {
			Settings.this.setNumCol(value);
		}

		public int getValue() {
			return Settings.this.getNumCol();
		}

	}

	public class AmobaLength extends FlexibleSettings {

		{
			name = "Az amőba hossza";
		}

		public void setValue(int value) {
			Settings.this.setAmobaLength(value);
		}

		public int getValue() {
			return Settings.this.getAmobaLength();
		}

	}

	public class IsBackValid extends FlexibleSettings {

		{
			name = "A játékban az utólsó lépés törlésének lehetősége";
		}

		public void setValue(int value) {
			if (value == 1 || value == 2) {
				Settings.this.setBackValid(value == 1);
			} else {
				throw new AmobaSettingsException("Nem értelmezhető beállítás.");
			}
		}

		public int getValue() {
			return Settings.this.isBackValid() ? 1 : 2;
		}

	}

}
