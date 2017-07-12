package model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a text document
 * @author Evgeniia Shcherbinina
 */
public class Document {

	private String text;	
	private int numWords;  // The number of words in the document
	private int numSentences;  // The number of sentences in the document
	private int numSyllables;  // The number of syllables in the document
	
	
	/** Creates a new document using the given text.
	 * @param text The text of the document
	 */
	public Document(String text) {
		this.text = text;
		processText();
	}
	
	
	/** Passes through the text one time to count the number of words, syllables 
     * and sentences.
     */
	private void processText()
	{
		// Call getTokens on the text to preserve separate strings that are 
		// either words or sentence-ending punctuation.  
		List<String> tokens = getTokens("[!?.]+|[a-zA-Z]+");
		
		for (int i = 0; i < tokens.size(); i++) {
			String s = tokens.get(i);
			if (s.contains(".") || s.contains("?") || s.contains("!")) {
				numSentences ++;
			} else {
				if (i == tokens.size() - 1) { // a sentence without sentence-ending punctuation
					numSentences ++;
				}
				numWords ++;
				numSyllables += countSyllables(s);
			}
		}
		
	}
	
	/** Returns the tokens that match the regex pattern from.
	 *  @param pattern a regular expression string specifying the token pattern desired
	 *  @return a list of tokens from the document text that match the regex pattern
	 */
	private List<String> getTokens(String pattern) {
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}		
		return tokens;
	}
	
	
	
	/** 
	 * Returns the number of syllables in a word according to the rule:
	 * Each contiguous sequence of one or more vowels is a syllable, 
	 * with the following exception: a lone "e" at the end of a word 
	 * is not considered a syllable unless the word has no other syllables. 
	 * 'Y' is a vowel.
	 * @param word to count the syllables in
	 * @return the number of syllables in the given word
	 */
	private int countSyllables(String word) {
		int number = 0;
		boolean isVow = false;
		
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (c == 'a' || c == 'e' || c == 'o' || c == 'i' || c == 'u' || c == 'y'
					|| c == 'A' || c == 'E' || c == 'O'  || c == 'I' || c == 'U' || c == 'Y') {
				
				if (i == word.length() - 1 && (c == 'e' || c == 'E') && number == 0) {
					number ++;
				} else if (i != word.length() - 1 && isVow == false) {
					number ++;
					isVow = true;
				} else if (i == word.length() - 1 && (c == 'a' || c == 'o' || c == 'i' || c == 'u' || c == 'y'
						|| c == 'A' || c == 'O'  || c == 'I' || c == 'U' || c == 'Y') 
						&& isVow == false) {
					number ++;
				}
				
			} else {
				isVow = false;
			}
		}
	    return number;
	}
	
	
	/** 
	 * Returns the number of words in this document.
	 */
	public int getNumWords() {
		return numWords;
	}
	
	/** 
	 * Return the number of sentences in this document.
	 */
	public int getNumSentences() {
		return numSentences;
	}
	
	/** 
	 * Returns the number of syllables in this document.
	 */
	public int getNumSyllables() {
		return numSyllables;
	}
	
	/** 
	 * Returns the entire text of this document.
	 */
	public String getText() {
		return text;
	}
	
	/** 
	 * Return the Flesch readability score of this document.
	 */
	public double getFleschScore() {
		int words = getNumWords();
	    return 206.835 - 1.015 * (words / getNumSentences()) - 84.6 * 
	    		(getNumSyllables() / words);
	}	
	
}
