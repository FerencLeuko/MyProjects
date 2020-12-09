package narrative_analyser.starter;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Test {
	
	public void runTest() {
	
	String text = "Kalap almával almában öttel ötben öt öttel ötben kalap kalappal kalapos kalapba Almafa alma szilva körtével körte barack fa varjúval vassal körte=2, alma=1, almafa=1, barack=1, fa=1, varjú=1, vas=1 körte=2, alma=1, almafa=1, barack=1, fa=1, varjú=1, vas=1körte=2, alma=1, almafa=1, körte=2, alma=1, almafa=1, körte=2, alma=1, almafa=1, körte=2, alma=1, almafa=1, körte=2, alma=1, almafa=1, körte=2, alma=1, almafa=1, körte=2, alma=1, almafa=1, barack=1, fa=1, varjú=1, vas=1 barack=1, fa=1, varjú=1, vas=1 barack=1, fa=1, varjú=1, vas=1barack=1, fa=1, varjú=1, vas=1 barack=1, fa=1, varjú=1, vas=1 barack=1, fa=1, varjú=1, vas=1 barack=1, fa=1, varjú=1, vas=1 vége";
	String targetWords = "alma,körte";

	TextExplorer analyser = TextExplorer.getBuilder(Language.HU).setText(text).setPositiveWords(targetWords).build();
	
	Map<String, Map<String, Integer>> nextFa = analyser.getNextFrequentWords(1, "almával", "kalap","alma","fa", "bokor", "vége", "öt");
	analyser.addStringToReport(analyser.getNextFrequentWordsAsString(nextFa,1));
			
	Storage storage = Storage.getInstance();
	
	storage.saveExpl(analyser, "Elemzés1");
	
	TextExplorer other = storage.loadExpl("Elemzés1");
	System.out.println(other.getReport());
	
	storage.saveReport(other, "elemzés2");
	System.out.println(storage.loadReport("elemzés2"));
	
	System.out.println();
	
	storage.saveDictionary(new TreeSet<String>(Arrays.asList(targetWords.split(","))), Language.HU,"alma");
	
	System.out.println("Elérhető szótárak: ");
	Set<String> a = storage.getDictionaryNames();
	for (String string : a) {
		System.out.println(string);
	}
	
	System.out.println("Elérhető reportok: ");
	a = storage.getReportNames();
	for (String string : a) {
		System.out.println(string);
	}
	
	System.out.println("Elérhető elemzések: ");
	a = storage.getExplorerNames();
	for (String string : a) {
		System.out.println(string);
	}
	}

}
