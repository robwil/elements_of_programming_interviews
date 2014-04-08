package book.chapter.twelve;

import java.lang.reflect.Method;
import java.util.HashMap;

import book.common.MutableInt;

/**
 * Problem 12.9, p. 98
 * Write a function that returns true iff an anonymous letter
 * could be made us from the letters found in a given magazine.
 * 
 * No time or space complexity targets were given.
 * 
 * canWriteLetterFromMagazine1 is what I initially wrote.
 * It works, but is ultra inefficient because of how it uses charAt.
 * 
 * canWriteLetterFromMagazine2 - 5 were written on a computer
 * using Javadoc of IDE. The insight in #5 vastly improved speed by
 * doing away with the hash table.
 * 
 * I looked at the book solution after 1-5. It turns out my #5 insight
 * is mentioned in the book's remark. I did, however, do the hash implementation
 * backward - requiring full parse of Magazine + Letter, instead of their method
 * of using hash for the Letter and working from there.
 * 
 * I implemented #6 to show that using HT method, and #7 to implement that with array method.
 * #6 and #7 are the two fastest for any situation where length(magazine) >> length(letter).
 * But of course #7 is fastest.
 * 
 * @author rob
 *
 */
public class Problem12_09 {
	public static boolean canWriteLetterFromMagazine1(String letter, String magazine) {
		// Assumption: Inputs are stripped of non-word /\W/ characters, and transformed to one case
		HashMap<Character, Integer> magazineChars = new HashMap<Character, Integer>();
		// Count all characters found in Magazine
		for (int i = 0; i < magazine.length(); i++) {
			char chr = magazine.charAt(i);
			if (magazineChars.get(chr) != null) {
				magazineChars.put(chr,  magazineChars.get(chr) + 1);
			} else { // first occurrence, so initialize to 1
				magazineChars.put(chr, 1);
			}
		}
		// Iterate through the Anonymous Letter, and decrement the counts
		// from the magazine. If a null or 0 count is found, return false
		// because that means the magazine doesn't have enough characters.
		for (int i = 0; i < letter.length(); i++) {
			char chr = letter.charAt(i);
			if (magazineChars.get(chr) == null || magazineChars.get(chr).compareTo(0) < 0) {
				return false;
			}
			magazineChars.put(chr, magazineChars.get(chr) - 1);
		}
		// If it makes it this far, then all characters must have been present
		// in Magazine.
		return true;
	}

	// This version simply pre-allocated 512 slots in the HashMap, rather than let it grow.
	public static boolean canWriteLetterFromMagazine2(String letter, String magazine) {
		HashMap<Character, Integer> magazineChars = new HashMap<Character, Integer>(512);
		for (int i = 0; i < magazine.length(); i++) {
			char chr = magazine.charAt(i);
			if (magazineChars.get(chr) != null) {
				magazineChars.put(chr,  magazineChars.get(chr) + 1);
			} else {
				magazineChars.put(chr, 1);
			}
		}
		for (int i = 0; i < letter.length(); i++) {
			char chr = letter.charAt(i);
			if (magazineChars.get(chr) == null || magazineChars.get(chr).compareTo(0) < 0) {
				return false;
			}
			magazineChars.put(chr, magazineChars.get(chr) - 1);
		}
		return true;
	}
	
	// Change to use char[] instead of charAt
	public static boolean canWriteLetterFromMagazine3(String letter, String magazine) {
		HashMap<Character, Integer> magazineChars = new HashMap<Character, Integer>(512);
		char[] letterCharArray = letter.toCharArray();
		char[] magazineCharArray = magazine.toCharArray();
		for (int i = 0; i < magazineCharArray.length; i++) {
			char chr = magazineCharArray[i];
			Integer currentCountForChar = magazineChars.get(chr);
			if (currentCountForChar != null) {
				magazineChars.put(chr, currentCountForChar + 1);
			} else {
				magazineChars.put(chr, 1);
			}
		}
		for (int i = 0; i < letterCharArray.length; i++) {
			char chr = letterCharArray[i];
			Integer currentCountForChar = magazineChars.get(chr);
			if (currentCountForChar == null || currentCountForChar.compareTo(0) < 0) {
				return false;
			}
			magazineChars.put(chr, currentCountForChar - 1);
		}
		return true;
	}
	
	// Change to use mutable integer objects
	public static boolean canWriteLetterFromMagazine4(String letter, String magazine) {
		HashMap<Character, MutableInt> magazineChars = new HashMap<Character, MutableInt>(512);
		char[] letterCharArray = letter.toCharArray();
		char[] magazineCharArray = magazine.toCharArray();
		for (int i = 0; i < magazineCharArray.length; i++) {
			char chr = magazineCharArray[i];
			MutableInt currentCountForChar = magazineChars.get(chr);
			if (currentCountForChar != null) {
				currentCountForChar.increment();
			} else {
				magazineChars.put(chr, new MutableInt(1));
			}
		}
		for (int i = 0; i < letterCharArray.length; i++) {
			char chr = letterCharArray[i];
			MutableInt currentCountForChar = magazineChars.get(chr);
			if (currentCountForChar == null || currentCountForChar.decrement() < 0) {
				return false;
			}
		}
		return true;
	}
	
	// No need to use Map at all
	public static boolean canWriteLetterFromMagazine5(String letter, String magazine) {
		// assuming lower case
		int[] magazineCharCounts = new int[26];
		for (int i = 0; i < 26; i++) {
			magazineCharCounts[i] = 0;
		}
		char[] letterCharArray = letter.toCharArray();
		char[] magazineCharArray = magazine.toCharArray();
		for (int i = 0; i < magazineCharArray.length; i++) {
			char chr = magazineCharArray[i];
			magazineCharCounts[chr - 'a']++;
		}
		for (int i = 0; i < letterCharArray.length; i++) {
			char chr = letterCharArray[i];
			if (--magazineCharCounts[chr - 'a'] < 0)
				return false;
		}
		return true;
	}
	
	// Reverse the message put in Hash Table (letter instead of magazine)
	public static boolean canWriteLetterFromMagazine6(String letter, String magazine) {
		HashMap<Character, MutableInt> letterChars = new HashMap<Character, MutableInt>(512);
		char[] letterCharArray = letter.toCharArray();
		char[] magazineCharArray = magazine.toCharArray();
		for (int i = 0; i < letterCharArray.length; i++) {
			char chr = letterCharArray[i];
			MutableInt currentCountForChar = letterChars.get(chr);
			if (currentCountForChar != null) {
				currentCountForChar.increment();
			} else {
				letterChars.put(chr, new MutableInt(1));
			}
		}
		for (int i = 0; i < magazineCharArray.length; i++) {
			char chr = magazineCharArray[i];
			MutableInt currentCountForChar = letterChars.get(chr);
			if (currentCountForChar != null && currentCountForChar.decrement() <= 0) {
				letterChars.remove(chr);
				if (letterChars.isEmpty())
					return true;
			}
		}
		return false;
	}
	
	// No need to use Map at all
	public static boolean canWriteLetterFromMagazine7(String letter, String magazine) {
		// assuming lower case
		int[] letterCharCounts = new int[26];
		int distinctLetterChars = 0;
		for (int i = 0; i < 26; i++) {
			letterCharCounts[i] = 0;
		}
		char[] letterCharArray = letter.toCharArray();
		char[] magazineCharArray = magazine.toCharArray();
		for (int i = 0; i < letterCharArray.length; i++) {
			char chr = letterCharArray[i];
			if (letterCharCounts[chr - 'a']++ == 0)
				distinctLetterChars++;
		}
		for (int i = 0; i < magazineCharArray.length; i++) {
			char chr = magazineCharArray[i];
			if (--letterCharCounts[chr - 'a'] == 0) {
				if (--distinctLetterChars == 0)
					return true;
			}
		}
		return false;
	}
	
	public static String cleanInput(String in) {
		in = in.replaceAll("\\W", "");
		return in.toLowerCase();
	}
	
	public static void benchmark() throws Exception {
		int N = 100000;
		String[] methodsToCompare = {
				"canWriteLetterFromMagazine1",
				"canWriteLetterFromMagazine2",
				"canWriteLetterFromMagazine3",
				"canWriteLetterFromMagazine4",
				"canWriteLetterFromMagazine5",
				"canWriteLetterFromMagazine6",
				"canWriteLetterFromMagazine7"
		};
		
		String magazine = cleanInput("this is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazinethis is a pretty awesome magazine");
		String letter = cleanInput("hogs are swine");
		
		for (String methodName : methodsToCompare) {
			Class clazz = Class.forName("book.chapter.twelve.Problem12_09");
			Method method = clazz.getDeclaredMethod(methodName, String.class, String.class);

			// Time execution of the reflected method.
			long startTime = System.nanoTime();
			for (int i = 0; i < N; i++) {
				Boolean retVal = (Boolean) method.invoke(null, letter, magazine);
				if (retVal == false)
					throw new Exception("wrong result!");
			}
			long endTime = System.nanoTime();
			System.out.println("Elapsed time for " + methodName + ": " + (endTime-startTime)/1000000000.0 + " seconds");
		}
	}
	
	public static void main(String[] args) throws Exception {
		String magazine = "this is a pretty awesome magazine";
		String letter = "hello world";
		System.out.println("Expect false: " + canWriteLetterFromMagazine1(letter, magazine));
		letter = "hogs are swine";
		System.out.println("Expect true: " + canWriteLetterFromMagazine1(letter, magazine));
		
		benchmark();
	}
}