package application;

import java.util.Random;

import model.Document;


public class LaunchClass {
	
	public String dictFile = "data/dict.txt";
	
	public LaunchClass() {
		super();
	}
	
	public model.Document getDocument(String text) {
		// Change this to BasicDocument(text) for week 1 only
		return new Document(text);
	}
	
	public model.MarkovTextGenerator getMTG() {
		return new model.MarkovTextGenerator(new Random());
	}
	
	public model.WPTree getWordPath() {
		return new model.WPTree();
	}
	
    public model.AutoCompleteDictionaryTrie getAutoComplete() {
        model.AutoCompleteDictionaryTrie tr = new model.AutoCompleteDictionaryTrie();
        model.DictionaryLoader.loadDictionary(tr, dictFile);
        return tr;
    }
    
    public model.DictionaryHS getDictionary() {
        model.DictionaryHS d = new model.DictionaryHS();
        model.DictionaryLoader.loadDictionary(d, dictFile);
    	return d;
    }
    
    public model.SpellingSuggest getSpellingSuggest(model.DictionaryHS dic) {
    	//return new spelling.SpellingSuggestNW(new spelling.NearbyWords(dic));
    	return new model.SpellingSuggest(dic);
    
    }
}
