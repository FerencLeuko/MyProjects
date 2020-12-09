package narrative_analyser.starter;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProgramProperties {

	private static final Logger LOG = LogManager.getLogger(ProgramProperties.class);
	private static final ProgramProperties SINGLETON = new ProgramProperties();

	private final Properties defaultSettings;

	{
		defaultSettings = new Properties();
		try (var is = getClass().getResourceAsStream("/settings.properties")) {
			defaultSettings.load(is);		
		} catch (IOException | RuntimeException e) {
			LOG.debug(String.format("Failed to load properties: %s!", e.getMessage()));
		}
	}

	private Language language;
	private String routeDictionaries;
	private String routeTexts;
	private String routeReports;
	private String routeExplorers;
	private String suffixHuDictionary;

	private ProgramProperties() {
		try {
			language = (defaultSettings.getProperty("defaultLanguage").equals("HU") ? Language.HU : Language.EN);
			routeDictionaries = defaultSettings.getProperty("routeDictionaries");
			routeTexts = defaultSettings.getProperty("routeTexts");
			routeReports = defaultSettings.getProperty("routeReports");
			routeExplorers = defaultSettings.getProperty("routeExplorers");
			suffixHuDictionary = defaultSettings.getProperty("suffixHuDictionary");
		} catch (Exception e) {
			language = Defaults.getLanguage();
			routeDictionaries = Defaults.getRouteDictionaries();
			suffixHuDictionary = Defaults.getSuffixHu();
			LOG.info(String.format("Program starts with default properties: %s!", e.getMessage()));
		}
	}

	public static ProgramProperties getProperties() {
		return SINGLETON;
	}

	public Language getLanguage() {
		return language;
	}

	public String getRouteDictionaries() {
		return routeDictionaries;
	}
	
	public Properties getDefaultSettings() {
		return defaultSettings;
	}
	
	public String getRouteTexts() {
		return routeTexts;
	}
	
	public String getRouteReports() {
		return routeReports;
	}

	public String getRouteExplorers() {
		return routeExplorers;
	}

	public String getSuffixHuDictionary() {
		return suffixHuDictionary;
	}


	private static class Defaults {

		private static final Language LANGUAGE = Language.EN;
		private static final String ROUTE_DICTIONARIES = "C:/";
		private static final String SUFFIX_HU = "t,at,et,ot,ban,ben,ba,be,ból,b\\u0151l,ról,r\\u0151l,val,vel,al,el,ra,re,tól,t\\u0151l,nak,nek,hoz,höz,ik,ok,nk,ak,ek,k";
		
		private static Language getLanguage() {
			return LANGUAGE;
		}

		private static String getRouteDictionaries() {
			return ROUTE_DICTIONARIES;
		}

		private static String getSuffixHu() {
			return SUFFIX_HU;
		}

	}

}
