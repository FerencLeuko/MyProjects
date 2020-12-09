package narrative_analyser.analyser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import narrative_analyser.starter.Language;
import narrative_analyser.starter.Storage;
import narrative_analyser.starter.TextExplorer;

public class ExplorerBuilder {

	private final Storage storage = new StorageImpl();
	private List<ExplorerBuilder> allBuilders = new ArrayList<>();

	static public ExplorerBuilder getBuilder(Language textLanguage) {
		for (ExplorerBuilder builder : getAllBuilders()) {
			if (builder.getLanguage().equals(textLanguage))
				return builder;
		}
		throw new IllegalArgumentException("This language is not found in ExplorerBuilder.");
	}

	static private List<ExplorerBuilder> getAllBuilders() {
		ExplorerBuilder builder = new ExplorerBuilder();
		builder.addBuilders();
		return builder.allBuilders;
	}

	private void addBuilders() {
		allBuilders.add(new ExplorerBuilderHu());
		allBuilders.add(new ExplorerBuilderEn());
		allBuilders.add(new ExplorerBuilderNone());
	}

	Language language;
	int serialNumber;
	String time;
	String text;
	String report;
	Dictionary positiveWords;
	Dictionary negativeWords;

	public Language getLanguage() {
		return language;
	}

	ExplorerBuilder setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}

	ExplorerBuilder setTime(String time) {
		this.time = time;
		return this;
	}

	ExplorerBuilder setReport(String report) {
		this.report = report;
		return this;
	}

	public ExplorerBuilder setText(String text) {
		if (!text.strip().isEmpty()) {
			this.text = text;
		}
		return this;
	}

	public ExplorerBuilder setPositiveWords(String positiveWordsString) {
		if (positiveWordsString != null && !positiveWordsString.strip().isEmpty()) {
			positiveWords = new Dictionary(positiveWordsString, language);
		}
		return this;
	}

	public ExplorerBuilder setNegativeWords(String negativeWordsString) {
		if (negativeWordsString != null && !negativeWordsString.strip().isEmpty()) {
			negativeWords = new Dictionary(negativeWordsString, language);
		}
		return this;
	}

	public TextExplorer build() {
		if (text == null) {
			throw new NullPointerException("Text to analyse is null.");
		}
		if (serialNumber == 0) {
			serialNumber = storage.loadLastSerialNumber() + 1;
		}
		if (time == null) {
			time = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		}

		return createInstance();
	}

	TextExplorer createInstance() {
		return new TextExplorerImplNone(serialNumber, time, text, positiveWords, negativeWords, language, report);
	}

	public class ExplorerBuilderEn extends ExplorerBuilder {

		{
			language = Language.EN;
		}

		@Override
		TextExplorer createInstance() {
			return new TextExplorerImplEn(serialNumber, time, text, positiveWords, negativeWords, language, report);
		}

	}

	public class ExplorerBuilderHu extends ExplorerBuilder {

		{
			language = Language.HU;
		}

		@Override
		TextExplorer createInstance() {
			return new TextExplorerImplHu(serialNumber, time, text, positiveWords, negativeWords, language, report);
		}

	}

	public class ExplorerBuilderNone extends ExplorerBuilder {

		{
			language = Language.NONE;
		}

		@Override
		TextExplorer createInstance() {
			return new TextExplorerImplNone(serialNumber, time, text, positiveWords, negativeWords, language, report);
		}

	}

}
