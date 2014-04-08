package book.chapter.ten;

import java.util.PriorityQueue;

/**
 * Design an efficient algorithm to merge k sorted files into
 * one output file. Each input file will be hundreds of MB, and
 * the goal is to make the space complexity of the algorithm
 * in the order of KB, not MB or GB.
 * 
 * To simplify the solution, I abstracted the File logic into a fake interface.
 * However the book actually ignores the file part and generalizes
 * to a "sort k sorted arrays" problem.
 * 
 * My verbatim solution is given as mergeKsorted1, but a more efficient
 * solution based on the book's answer is mergeKsorted2.
 *
 */
public class Problem10_01 {

	
	public static void mergeKsorted1(String[] inputFilenames, String outputFilename) {
		File outFile = new File(outputFilename);
		int N = inputFilenames.length;
		File[] inFiles = new File[N];
		boolean[] doneWithFile = new boolean[N];
		int doneCount = 0;
		PriorityQueue<String> minHeap = new PriorityQueue<String>();
		// Assume comparator splits string and compares parsed int value of first item
		for (int i = 0; i < N; i++) {
			inFiles[i] = new File(inputFilenames[i]);
			doneWithFile[i] = false;
		}
		while (doneCount != N) {
			for (int i = 0; i < N; i++) {
				if (doneWithFile[i]) continue;
				String line = inFiles[i].readLine();
				
				if (line == null) {
					inFiles[i].close();
					doneWithFile[i] = true;
					doneCount++;
					continue;
				}
				
				if (line.compareTo(minHeap.peek()) < 0) {
					minHeap.add(line);
				} else {
					inFiles[i].rewindLine();
				}
			}
			outFile.writeLine(minHeap.remove());
		}
		// Flush remainder of heap
		while (!minHeap.isEmpty()) {
			outFile.writeLine(minHeap.remove());
		}
		outFile.close();
	}
	
	public static void mergeKsorted2() {
		// TODO: implement based on book's solution
	}
}
