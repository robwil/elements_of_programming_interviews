package book.chapter.twelve;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Problem 12.7, p. 98
 * Given an input dictionary, partition the dictionary into subsets of words that are all anagrams of each other.
 * I defined the output as a List<List<String>>.
 * 
 * My initial implementation tried to use my own raw List[] Array as a hash table,
 * but I quickly remembered all the implementation details I would have to deal with,
 * and decided to swap to HashMap<Integer, List>.
 * 
 * @author rob
 *
 */
public class Problem12_07_Anagram_Dictionary {
	// Hash function for words, such that two words which are anagrams receive the same hashCode
	// This works by sorting the letters in a word, so that words with same letters will be equivalent.
	// e.g. hashWord("art") == hashWord("tar")
	private static int hashWord(String word) {
		// convert word to lower case for consistency, char array for iterability
		char[] original = word.toLowerCase().toCharArray();
		// perform O(N) counting sort on word so that it can be rearranged into consistent order
		// e.g. "movie" -> "eimov", "tar" -> "art", "rat" -> "art"
		int[] letterCounts = new int[26];
		for (int i = 0; i < original.length; i++) {
			letterCounts[original[i] - 'a']++;
		}
		StringBuffer str = new StringBuffer(original.length);
		for (int i = 0; i < 26; i++) {
			while (letterCounts[i] > 0) {
				str.append((char)(i + 'a'));
				letterCounts[i]--;
			}
		}
		return str.toString().hashCode();
	}
	
	public static List<List<String>> getAnagrams(File inputDictionary) throws IOException {
		// Read all words from dictionary file into Words array
		List<String> words = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(inputDictionary));
		String word;
		while ((word = br.readLine()) != null) {
			words.add(word);
		}
		br.close();
		// Find anagrams by hashing each word, from hashCode(word) --> list of words
		Map<Integer, List<String>> anagrams = new HashMap<Integer, List<String>>();
		for (int i = 0; i < words.size(); i++) {
			int hashCode = hashWord(words.get(i));
			if (!anagrams.containsKey(hashCode)) {
				anagrams.put(hashCode, new LinkedList<String>());
			}
			anagrams.get(hashCode).add(words.get(i));
		}
		// Convert Map<Integer, List<String>> which is a sparse hash table including single non-anagram words,
		// into a List<List<String>> of all actual anagrams
		List<List<String>> anagramsList = new LinkedList<List<String>>();
		for (Entry<Integer, List<String>> e : anagrams.entrySet()) {
			if (e.getValue().size() > 1) // Exclude words which do not have an anagram
				anagramsList.add(e.getValue());
		}
		return anagramsList;
	}
	
	// Same as above, but using my own personal HT implementation
	public static List<List<String>> getAnagramsHT(File inputDictionary) throws IOException {
		// Read all words from dictionary file into Words array
		List<String> words = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(inputDictionary));
		String word;
		while ((word = br.readLine()) != null) {
			words.add(word);
		}
		br.close();
		// Find anagrams by hashing each word, from hashCode(word) --> list of words
		HT anagrams = new HT();
		for (int i = 0; i < words.size(); i++) {
			int hashCode = hashWord(words.get(i));
			if (anagrams.get(hashCode) == null) {
				anagrams.put(hashCode, new LinkedList<String>());
			}
			((LinkedList<String>)anagrams.get(hashCode)).add(words.get(i));
		}
		// Convert HT which is a sparse hash table including single non-anagram words,
		// into a List<List<String>> of all actual anagrams
		List<List<String>> anagramsList = new LinkedList<List<String>>();
		for (HTEntry e : anagrams.entrySet()) {
			if (((List<String>)e.getValue()).size() > 1) // Exclude words which do not have an anagram
				anagramsList.add((List<String>)e.getValue());
		}
		return anagramsList;
	}
	
	public static void main(String[] args) {
		File dictionary = new File("dictionary.txt");
		try {
			//List<List<String>> anagrams = getAnagrams(dictionary);
			List<List<String>> anagrams = getAnagramsHT(dictionary);
			System.out.println(anagrams.toString());
		} catch (IOException e) {
			System.err.println("IO EXCEPTION ENCOUNTERED!!");
			e.printStackTrace();
		}
	}
}
