package model;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;

/** 
 * A trie data structure that implements the Dictionary ADT
 * @author Evgeniia Shcherbinina *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
	}
	
	
	/** 
	 * Adds a word into the trie.
	 * @param word to add
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word) {
		
		// get rid if punctuation marks at the end
		char last = word.charAt(word.length() - 1);
		if (last == '.' || last == ',' || last == '!' || last == '?' || last == ';') {
			word = word.substring(0, word.length() - 1);
		}
		
	    //word = word.toLowerCase();
	    TrieNode current = root;
	    TrieNode child;
	    
	    // add a sequence of letters of the word if they don't exist
	    for (int i = 0; i < word.length(); i++) {
	    	char c = word.charAt(i);
	    	child = current.getChild(c);
	    	if (child == null) {
	    		current.insert(c);
	    		current = current.getChild(c);
	    	} else {
	    		current = child;
	    	}
	    }
	    
	    // set the current node to be the end of the word
	    if (!current.endsWord()) {
	    	current.setEndsWord(true);
	    	size++;
	    	return true;
	    }
	    
	    return false;
	}
	
	/** 
	 * Returns the number of words in the dictionary.  
	 */
	public int size() {
	    return size;
	}
	
	
	/** 
	 * Returns whether the string is a word in the trie. 
	 */
	public boolean isWord(String s) {
		if (s.length() < 1) {
			return false;
			
		} else {
			boolean givenIsWord = isWordHelper(s); // the word as given
			boolean lowerCaseIsWord = isWordHelper(s.toLowerCase()); // to lower case
			
			return givenIsWord || lowerCaseIsWord;
		}
	}
	
	/**
	 * The helper method for defining whether the sting is a word in the trie.
	 * @param s the given string 
	 * @return true if s is a word, false otherwise
	 */
	private boolean isWordHelper(String s) {
		TrieNode current = root;
	    TrieNode child;
	    
	    for (int i = 0; i < s.length(); i++) {
	    	char c = s.charAt(i);
	    	child = current.getChild(c);
	    	if (child == null) {
	    		return false;
	    	} else {
	    		current = child;
	    	}
	    }
		return current.endsWord();
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired
     * @return A list containing the up to numCompletions best predictions
     */
     public List<String> predictCompletions(String prefix, int numCompletions) {
    	 List<String> result = new LinkedList<>();
    	 if (numCompletions < 1) {
    		 return result;
    	 }
    	 
    	 //prefix = prefix.toLowerCase();
    	 TrieNode current = root;
    	 TrieNode child;
    	 for (int i = 0; i < prefix.length(); i++) {
    		 char c = prefix.charAt(i);
    		 child = current.getChild(c);
    		 if (child == null) {
    			 return result;
    		 }
    		 current = child;    		 
    	 }
    	 
    	 Queue<TrieNode> nodes = new LinkedList<>();
    	 nodes.add(current);
    	 
    	 // check all possible legal completions
    	 while(!nodes.isEmpty() && numCompletions != 0) {
    		 
    		 TrieNode t = nodes.remove();
    		 if (t.endsWord()) {
    			 result.add(t.getText());
    			 numCompletions--;
    		 }
    		 Set<Character> children = t.getValidNextCharacters();
    		 for(Character c: children) {
    			 nodes.add(t.getChild(c));
    		 }
    	 }    	 
    	 
         return result;
     }

 	/**
 	 * Prints the trie.
 	 */
 	public void printTree() {
 		printNode(root);
 	}
 	
 	/** 
 	 * Prints a node using pre-order traversal.
 	 */
 	public void printNode(TrieNode curr) {
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
}