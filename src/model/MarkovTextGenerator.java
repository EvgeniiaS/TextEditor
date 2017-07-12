package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** 
 * This class generates the Markov text.
 * @author Evgeniia Shcherbinina
 */
public class MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting word
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	
	public MarkovTextGenerator(Random generator) {
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/**
	 * Trains the generator by adding the sourceText
	 * @param sourceText 
	 */
	public void train(String sourceText) {
		wordList.clear();
		String[] words = sourceText.split(" +");
		if (words.length > 0) {
			starter = words[0];
			wordList.add(new ListNode(starter));
			String prevWord = starter;
			int indexPrev;
		
			for (int i = 1; i < words.length; i++) {
				String curWord = words[i];
				indexPrev = indexOf(prevWord);
				wordList.get(indexPrev).addNextWord(curWord);
				if (!contains(curWord)) {
					wordList.add(new ListNode(curWord));
				}
				prevWord = curWord;
			}
			
			indexPrev = indexOf(prevWord);
			wordList.get(indexPrev).addNextWord(starter);
		}
	}
	
	
	
	/** 
	 * Generates the number of words requested.
	 */
	public String generateText(int numWords) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(starter);
	    if (numWords > 1 && starter != "") {
	    	String current = starter;
	    	for (int i = 1; i < numWords; i++) {
	    		sb.append(" ");
	    		int indexCur = indexOf(current);
	    		current = wordList.get(indexCur).getRandomNextWord(rnGenerator);
	    		sb.append(current);
	    	}
	    }
		return sb.toString();
	}
	
	
	@Override
	public String toString() {
		String toReturn = "";
		for (ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** 
	 * Retrains the generator from scratch on the source text
	 */
	public void retrain(String sourceText) {
		wordList.clear();
		train(sourceText);
	}
	
	/**
	 * Checks whether or not wordList contains the given word
	 * @param word to check
	 * @return true if contains, false otherwise
	 */
	private boolean contains(String word) {
		for (int i = 0; i < wordList.size(); i++) {
			if (wordList.get(i).getWord().equals(word)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the index of the given word in wordList 
	 * @param word to find the index
	 * @return index of the given word or -1 if there is no such the word
	 */
	private int indexOf(String word) {
		for (int i = 0; i < wordList.size(); i++) {
			if (wordList.get(i).getWord().equals(word)) {
				return i;
			}
		}
		return -1;
	}
	
	
	
	/**
	 * Private class that represents a single word with a list of its next words.
	 * @author Evgeniia Shcherbinina
	 *
	 */
	private class ListNode {
		
		private String word;
	
		// The next words
		private List<String> nextWords;
	
		public ListNode(String word) {
			this.word = word;
			nextWords = new LinkedList<String>();
		}
	
		/**
		 * Returns the word
		 * @return the word
		 */
		public String getWord() {
			return word;
		}

		/**
		 * Adds the next word
		 * @param nextWord to add
		 */
		public void addNextWord(String nextWord) {
			nextWords.add(nextWord);
		}
	
		/**
		 * Returns the random next word
		 * @param generator
		 * @return the randomly chosen next word
		 */
		public String getRandomNextWord(Random generator) {
			int index = generator.nextInt(nextWords.size());
			return nextWords.get(index);
		}

		@ Override
		public String toString() {
			String toReturn = word + ": ";
			for (String s : nextWords) {
				toReturn += s + "->";
			}
			toReturn += "\n";
			return toReturn;
		}
	}
	
}


