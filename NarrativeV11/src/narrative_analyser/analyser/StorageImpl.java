package narrative_analyser.analyser;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import narrative_analyser.starter.Language;
import narrative_analyser.starter.ProgramProperties;
import narrative_analyser.starter.Storage;
import narrative_analyser.starter.TextExplorer;

public class StorageImpl implements Storage {

	private static final Logger LOG = LogManager.getLogger(StorageImpl.class);
	private final ProgramProperties properties = ProgramProperties.getProperties();

	@Override
	public String loadText(String fileName) {
		return loadString(Paths.get(properties.getRouteTexts() + fileName + ".txt"));
	}

	@Override
	public void saveExpl(TextExplorer analysis, String fileName) {
		Path path = Paths.get(properties.getRouteExplorers() + fileName + ".explorer");
		ExplorerBeans beans = new ExplorerBeans((TextExplorerImpl) analysis);
		try (var encoder = new XMLEncoder(Files.newOutputStream(path))) {
			encoder.writeObject(beans);
		} catch (IOException e) {
			LOG.debug(String.format("Failed to save: %s!", e.getMessage()));
		}
	}

	@Override
	public TextExplorer loadExpl(String fileName) {
		Path path = Paths.get(properties.getRouteExplorers() + fileName + ".explorer");
		TextExplorer explorer = null;
		try (var decoder = new XMLDecoder(Files.newInputStream(path))) {
			ExplorerBeans bean = (ExplorerBeans) decoder.readObject();
			explorer = TextExplorer.getBuilder(bean.getLanguage()).//
					setSerialNumber(bean.getSerialNumber()).//
					setTime(bean.getTime()).//
					setText(bean.getTextOriginal()).//
					setPositiveWords(bean.getPositiveWords()).//
					setNegativeWords(bean.getNegativeWords()).//
					setReport(bean.getReport()).//
					build();
		} catch (IOException e) {
			LOG.debug(String.format("Failed to load: %s!", e.getMessage()));
		}
		return explorer;
	}

	@Override
	public void saveReport(TextExplorer analysis, String fileName) {
		saveString(Paths.get(properties.getRouteReports() + fileName + ".report"), analysis.getReport());
	}

	@Override
	public String loadReport(String fileName) {
		return loadString(Paths.get(properties.getRouteReports() + fileName + ".report"));
	}

	@Override
	public Dictionary loadDictionary(String fileName) {
		Path path = Paths.get(properties.getRouteDictionaries() + fileName + ".dictionary");
		Dictionary dictionary = null;
		try (var decoder = new XMLDecoder(Files.newInputStream(path))) {
			dictionary = (Dictionary) decoder.readObject();
		} catch (IOException e) {
			LOG.debug(String.format("Failed to load: %s!", e.getMessage()));
		}
		return dictionary;
	}
	
	@Override
	public void saveDictionary(Dictionary dictionary, String fileName) {
		Path path = Paths.get(properties.getRouteDictionaries() + fileName + ".dictionary");
		try (var encoder = new XMLEncoder(Files.newOutputStream(path))) {
			encoder.writeObject(dictionary);
		} catch (IOException e) {
			LOG.debug(String.format("Failed to save: %s!", e.getMessage()));
		}
	}

	@Override
	public Dictionary loadDictionaryFromString(String fileName, Language language) {
		return new Dictionary(loadString(Paths.get(properties.getRouteDictionaries() + fileName + ".dictionary")), language);
	}

	@Override
	public void saveDictionary(Set<String> dictionaryWords, Language language, String fileName) {
		saveDictionary(new Dictionary(dictionaryWords, language), fileName);
	}

	@Override
	public Set<String> getTextNames() {
		return getFileNames(properties.getRouteTexts(), ".txt");
	}

	@Override
	public Set<String> getExplorerNames() {
		return getFileNames(properties.getRouteExplorers(), ".explorer");
	}

	@Override
	public Set<String> getReportNames() {
		return getFileNames(properties.getRouteReports(), ".report");
	}

	@Override
	public Set<String> getDictionaryNames() {
		return getFileNames(properties.getRouteDictionaries(), ".dictionary");
	}

	@Override
	public Set<String> getFileNames(String directory, String extension) {
		Path[] pathnames = null;
		try {
			pathnames = toArray(Files.newDirectoryStream(Paths.get(directory), path -> path.toString().endsWith(extension)));
		} catch (IOException e) {
			LOG.debug(String.format("Failed to load: %s!", e.getMessage()));
		}
		Set<String> names = new TreeSet<>(Collator.getInstance());
		for (Path name : pathnames) {
			names.add(name.toString().substring(name.toString().lastIndexOf("\\") + 1));
		}
		return names;
	}

	private static Path[] toArray(DirectoryStream<Path> stream) {
		return StreamSupport.stream(stream.spliterator(), false).toArray(length -> new Path[length]);
	}

	@Override
	public void autoSave(TextExplorer analysis) {
		saveExpl(analysis, "autoSave");
	}

	@Override
	public int loadLastSerialNumber() {
		int last = 0;
		try {
			last = Integer.parseInt(loadString(Paths.get(properties.getRouteExplorers() + "lastSerial" + ".number")));
		} catch (RuntimeException e) {
			LOG.debug(String.format("Failed to read lastSerialNumber: %s!", e.getMessage()));
		}
		return last;
	}

	@Override
	public void saveLastSerialNumber(int last) {
		saveString(Paths.get(properties.getRouteExplorers() + "lastSerial" + ".number"), "" + last);
	}

	private String loadString(Path path) {
		String load = null;
		try {
			load = Files.readString(path);
		} catch (IOException | RuntimeException e) {
			LOG.debug(String.format("Failed to load: %s!", e.getMessage()));
		}
		return load;
	}

	private void saveString(Path path, String string) {
		try {
			Files.writeString(path, string);
		} catch (IOException | RuntimeException e) {
			LOG.debug(String.format("Failed to save: %s!", e.getMessage()));
		}
	}

}
