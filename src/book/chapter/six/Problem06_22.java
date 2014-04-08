package book.chapter.six;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Problem 6.22, p. 63
 * Each digit on a phone keypad, apart from 0 and 1, correspond to 3 or 4 letters.
 * Given a cell phone keypad mapping (from digit to set of characters), and a number sequence,
 * return all possible character sequences (not just legal words) that correspond to the number sequence.
 * 
 * Right off the bat I realized this is exponential growth. Each additional number in the sequence
 * causes the output set to grow by 3x or 4x. There isn't any way around that that I could think of.
 * I also could not think of any clever way to save work, so I implemented this in the straight-forward
 * "brute force" style, iteratively.
 * 
 * The book solution used recursion, which I did think of but abandoned because I was trying to return
 * the full set of responses, not merely print them. When only printing, recursion is certainly the better approach
 * since it can use O(N) memory instead of my massive O(4^N).
 * 
 * @author rob
 *
 */

public class Problem06_22 {
	public static List<String> phoneNumberMnemonics(Map<Integer, List<Character>> keypadMapping, List<Integer> phoneNumber) {
		ArrayList<StringBuffer> workingSet = new ArrayList<StringBuffer>();
		workingSet.add(new StringBuffer(phoneNumber.size())); // start w/ empty "mnemonic"
		for (Integer number : phoneNumber) {
			List<Character> chars = keypadMapping.get(number);
			// we compute the size of workingSet prior to the loop, so that the loop will only iterate over
			// elements which existed at the start of the loop. This is necessary because the loop iterations
			// will expand the size of workingSet.
			int size = workingSet.size(); 
			for (int j = 0; j < size; j++) {
				StringBuffer current = workingSet.get(j);
				for (int i = 0; i < chars.size(); i++) {
					// Last iteration of this loop simply appends its character to "current"
					if (i == chars.size() - 1) {
						current.append(chars.get(i));
					}
					// All other iterations clone "current" and append to the clone, then add the clone to workingSet.
					else {
						StringBuffer copy = new StringBuffer(current);
						copy.append(chars.get(i));
						workingSet.add(copy);
					}
				}
			}
		}
		// Convert List<StringBuffer> to List<String>
		List<String> retVal = new LinkedList<String>();
		for (StringBuffer str : workingSet) {
			retVal.add(str.toString());
		}
		return retVal;
	}
	
	public static void phoneNumberMnemonicsRecursive(Map<Integer, List<Character>> keypadMapping, List<Integer> phoneNumber) {
		phoneNumberMnemonicsRecursive(keypadMapping, phoneNumber, 0, CharBuffer.allocate(phoneNumber.size()));
	}
	
	private static void phoneNumberMnemonicsRecursive(Map<Integer, List<Character>> keypadMapping, List<Integer> phoneNumber, int i, CharBuffer result) {
		// Base case, we have a complete sequence, so print it.
		if (i == phoneNumber.size()) {
			System.out.print(result + " ");
		}
		// Otherwise, recurse by adding all characters mapped to current number.
		else {
			List<Character> chars = keypadMapping.get(phoneNumber.get(i));
			for (Character c : chars) {
				result.put(i, c);
				phoneNumberMnemonicsRecursive(keypadMapping, phoneNumber, i+1, result);
			}
		}
	}
	
	private static void printer(List<String> list) {
		for (String str : list)
			System.out.print(str + " ");
		System.out.println();
	}
	
	public static void main(String[] args) {
		Map<Integer, List<Character>> keypadMapping = new HashMap<Integer, List<Character>>();
		keypadMapping.put(2, Arrays.asList('A', 'B', 'C'));
		keypadMapping.put(3, Arrays.asList('D', 'E', 'F'));
		keypadMapping.put(4, Arrays.asList('G', 'H', 'I'));
		keypadMapping.put(5, Arrays.asList('J', 'K', 'L'));
		keypadMapping.put(6, Arrays.asList('M', 'N', 'O'));
		keypadMapping.put(7, Arrays.asList('P', 'Q', 'R', 'S'));
		keypadMapping.put(8, Arrays.asList('T', 'U', 'V'));
		keypadMapping.put(9, Arrays.asList('W', 'X', 'Y', 'Z'));
		
		List<Integer> phoneNumber = Arrays.asList(2, 3, 4);
		printer(phoneNumberMnemonics(keypadMapping, phoneNumber));
		
		phoneNumberMnemonicsRecursive(keypadMapping, phoneNumber);
		System.out.println();
	}
}
