
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * The class represents nearby words.
 * @author Evgeniia Shcherbinina
 */
public class SpellingSuggest {
	
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	private static final int THRESHOLD = 10000; 

	DictionaryHS dict;

	public SpellingSuggest (DictionaryHS dict) {
		this.dict = dict;
	}

	/** 
	 * Returns the list of Strings that are one modification away
	 * from the input string.  
	 * @param s the original String
	 * @param wordsOnly controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> retList = new ArrayList<String>();
		   insertions(s, retList, wordsOnly);
		   substitution(s, retList, wordsOnly);
		   deletions(s, retList, wordsOnly);
		   return retList;
	}

	
	/** 
	 * Adds to the currentList Strings that are one character mutation away
	 * from the input string.  
	 * @param s the original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 */
	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}
	
	/** 
	 * Adds to the currentList Strings that are one character insertion away
	 * from the input string.  
	 * @param s the original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		String before = "";
		String after = new String(s);
		
		// for each letter in the s and for all possible replacement characters
		for (int i = 0; i <= s.length(); i++) {
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				StringBuffer sb = new StringBuffer();
				sb.append(before);
				sb.append((char) charCode);
				sb.append(after);
				
				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if (!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
			
			// move one letter right
			if (i >= s.length() - 1) {
				before = s;
				after = "";
			} else {
				before = s.substring(0, i + 1);			
				after = s.substring(i + 1);
			}
		}
		
	}

	/** 
	 * Adds to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		for (int i = 0; i < s.length(); i++) {
			StringBuffer sb = new StringBuffer(s);
			sb.deleteCharAt(i);
			
			// if the item isn't in the list, isn't the original string, and
			// (if wordsOnly is true) is a real word, add to the list
			if (!currentList.contains(sb.toString()) && 
					(!wordsOnly||dict.isWord(sb.toString())) &&
					!s.equals(sb.toString())) {
				currentList.add(sb.toString());
			}
		}
	}

	/** 
	 * Gives a list of spelling suggestions for the misspelled word. 
	 * @param word the misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return 
	 * @return the list of spelling suggestions
	 */
	public List<String> suggestions(String word, int numSuggestions) {

		// strings to explore
		Queue<String> queue = new LinkedList<String>();
		
		// to avoid exploring the same string multiple times
		HashSet<String> visited = new HashSet<String>();  
		
		// words to return
		List<String> retList = new LinkedList<String>();
		 
		
		// insert first node
		queue.add(word);
		visited.add(word);
		
		int countSuggWords = THRESHOLD;
					
		while (!queue.isEmpty() && numSuggestions > 0 && countSuggWords > 0) {
			String current = queue.remove();
			List<String> neighbors = distanceOne(current, true);
			
			for (String s: neighbors) {
				if (!visited.contains(s)) {
					visited.add(s);
					queue.add(s);
					countSuggWords--;
					
					if (dict.isWord(s) && numSuggestions > 0) {
						retList.add(s);
						numSuggestions--;
					}
				}
			}
		}
		
		return retList;

	}	


}
