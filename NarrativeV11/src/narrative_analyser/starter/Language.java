package narrative_analyser.starter;

import java.text.Collator;
import java.util.Locale;

public enum Language {

	NONE("NONE","",null),
	HU("HU", "[^A-Za-záéíóöőúüűÁÉÍÓÖŐÚÜŰ\\s]", Collator.getInstance(new Locale("HU", "hu"))), //
	EN("EN", "[^A-Za-z\\s]", Collator.getInstance(new Locale("EN", "en")));

	Language(String label, String nonAlphaBet, Collator collator) {
		this.label = label;
		this.nonAlphaBet = nonAlphaBet;
		this.collator = collator;
	}

	private final String label;

	private final String nonAlphaBet;

	private final Collator collator;

	public String getNonAlphaBet() {
		return nonAlphaBet;
	}

	public String getLabel() {
		return label;
	}

	public Collator getCollator() {
		return collator;
	}

}
