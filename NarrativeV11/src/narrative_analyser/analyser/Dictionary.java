package narrative_analyser.analyser;

import java.io.Serializable;
import java.text.Collator;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import narrative_analyser.starter.Language;

public class Dictionary implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Language language;
	private Set<String> dictionarySet = new TreeSet<>(Collator.getInstance());

	public Dictionary(){
	}
	
	public Dictionary(String dictionaryString, Language language){
		this.language = language;
		this.dictionarySet = new TreeSet<String>(Arrays.asList(dictionaryString.replaceAll("[\\[\\]\\s+]", "").split(",")));
	}
	
	public Dictionary(Set<String> dictionarySet, Language language){
		this.language = language;
		this.dictionarySet = dictionarySet;
	}
	
	public String getDictionaryString() {
		return dictionarySet.toString().replaceAll(language.getNonAlphaBet(), "").replaceAll(" ", ",");
	}

	public Set<String> getDictionarySet() {
		return dictionarySet;
	}

	public void setDictionarySet(Set<String> dictionarySet) {
		this.dictionarySet = dictionarySet;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	

}
