package book.chapter.six;

import java.util.BitSet;

public class NumbersSeen {
	private BitSet seen;
	
	public NumbersSeen() {
		seen = new BitSet(9);
	}
	
	public void init() {
		for (int i = 0; i < 9; i++) {
			seen.set(i, false);
		}
	}
	
	public void sawNumber(int n) throws DuplicateException {
		// 0 means an unfilled Sudoku space, so we don't care about this spot
		if (n == 0) return;
		// If we already saw this value, it is a duplicate so return
		if (seen.get(n-1))
			throw new DuplicateException(n + " was duplicated");
		// Otherwise, record this seen for next cases.
		seen.set(n-1, true);
	}
}
