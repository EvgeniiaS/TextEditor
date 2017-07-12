
package model;

import java.util.HashSet;


/**
 * A class that represents a dictionary.
 * @author Evgeniia Shcherbinina
 */
public class DictionaryHS implements Dictionary {

    private HashSet<String> words;
	
	public DictionaryHS () {
	    words = new HashSet<String>();
	}
	
    /** 
     * Add this word to the dictionary.
     * @param word to add
     * @return true if the word was added to the dictionary (it wasn't already there)
     */
	@ Override
	public boolean addWord(String word) {
		char last = word.charAt(word.length() - 1);
		if (last == '.' || last == ',' || last == '!' || last == '?' || last == ';') {
			word = word.substring(0, word.length() - 1);
		}
		return words.add(word);
	}

	/** 
	 * Returns the number of words in the dictionary.
	 */
	@ Override
	public int size() {
    	 return words.size();
	}
	
	/** 
	 * Is this a word according to this dictionary? 
	 */
	@ Override
	public boolean isWord(String s) {
    	//return words.contains(s.toLowerCase());
    	return words.contains(s) || words.contains(s.toLowerCase());
	}	
   
}
