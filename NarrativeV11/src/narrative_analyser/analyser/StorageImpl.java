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
		String load = null;
		try {
			load = Files.readString(Paths.get(properties.getRouteTexts() + fileName + ".txt"));
		} catch (IOException | RuntimeException e) {
			LOG.debug(String.format("Failed to load: %s!", e.getMessage()));
		}
		return load;
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
		Path path = Paths.get(properties.getRouteReports() + fileName + ".report");
		try {
			Files.writeString(path, analysis.getReport());
		} catch (IOException | RuntimeException e) {
			LOG.debug(String.format("Failed to save: %s!", e.getMessage()));
		}
	}

	@Override
	public String loadReport(String fileName) {
		String load = null;
		try {
			load = Files.readString(Paths.get(properties.getRouteReports() + fileName + ".report"));
		} catch (IOException | RuntimeException e) {
			LOG.debug(String.format("Failed to load: %s!", e.getMessage()));
		}
		return load;
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
	public Dictionary loadDictionaryFromString(String fileName, Language language) {
		Path path = Paths.get(properties.getRouteDictionaries() + fileName + ".dictionary");
		Dictionary dictionary = null;
		try {
			String load = Files.readString(path);
			dictionary = new Dictionary(load, language);
		} catch (IOException e) {
			LOG.debug(String.format("Failed to load: %s!", e.getMessage()));
		}
		return dictionary;
	}

	@Override
	public void saveDictionary(Set<String> dictionaryWords, Language language, String fileName) {
		Dictionary dictionary = new Dictionary(dictionaryWords, language);
		saveDictionary(dictionary, fileName);
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
		Path path = Paths.get(properties.getRouteExplorers() + "lastSerial" + ".number");
		int last = 0;
		try {
			last = Integer.parseInt(Files.readString(path));
		} catch (IOException | RuntimeException e) {
			LOG.debug(String.format("Failed to load: %s!", e.getMessage()));
		}
		return last;
	}
	
	@Override
	public void saveLastSerialNumber(int last) {
		Path path = Paths.get(properties.getRouteExplorers() + "lastSerial" + ".number");
		try {
			Files.writeString(path, ""+last);
		} catch (IOException | RuntimeException e) {
			LOG.debug(String.format("Failed to save: %s!", e.getMessage()));
		}
	}
}
