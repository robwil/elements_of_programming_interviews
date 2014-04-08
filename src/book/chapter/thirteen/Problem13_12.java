package book.chapter.thirteen;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Problem 13.12, p. 105
 * Points Covering Intervals
 * 
 * You are given a set of N tasks, modeled as closed intervals [a_i, b_i], for i = 0 .. n-1.
 * A set S of visit times covers the tasks if [a_i, b_i] intersect S != empty set, for all i.
 * Design an efficient algorithm for finding the set S with minimum cardinality set,
 * i.e. find the smallest set of visit times that covers all tasks.
 * 
 * I began by trying to find a conceptual approach that I could prove would give the optimal result.
 * My premise is to use the following algorithm:
 * 	1) sort the tasks by their end time (b_i)
 *  2) visit next unvisited element at its end time
 *  3) mark all other nodes with intervals including that time as visited
 *  4) repeat #2 for the next unvisited node
 *  5) repeat #2-4 until all nodes are visited.
 *  
 * It turns out in implementation we don't actually need to mark things as visited, but this was the concept.
 * 
 * Proof that this gives optimal answer:
 * Take task [x,y] such that y is the minimum end time value.
 * 
 * 	Option 1: [x,y] is disjoint. In this case, we would have to visit it without visiting any other node,
 * 			  so the time we choose is irrelevant. Visiting at time y is as good a time as any.
 *  Option 2: There exists one or more tasks [x_j, y_j] intersecting [x,y].
 *  		  Because of the properties of intervals, we know y_j > x_j.
 *  		  If x_j > y then [x_j, y_j] would not intersect [x,y]. Therefore x_j <= y by contradiction.
 *  		  Because y is the minimum value, we know that y_j > y.
 *  		  Putting this together, we know x_j <= y < y_j, which means that by visiting y we will visit task [x_j, y_j].
 *  Therefore, by visiting task [x,y] (where y is the minimum end time) at point y, we will also visit any tasks which intersect [x,y].
 *  By following this strategy for all unvisited nodes, we are guaranteed visit all nodes with their interesecting tasks, if any.
 *  
 *  
 * My solution implemented below as findVisitTimes, runs in O(N log N) time complexity (from the sort)
 * and O(N) space complexity.
 * 
 * The book solution uses the same general principle as me, taking b_min aka minimum end time each time.
 * However, they maintain two separate sorted sets, one sorted by start time and one sorted by end time.
 * They do an explicit step of marking items as visited, by walking through the one sorted by start time.
 * 
 * I'm not convinced that is necessary. I think the way I do it works for all scenarios.
 * I tried to come up with a counterexample that would stump my algorithm, but I can't do it.
 * I'm rather convinced by my proof that my method works, that x_j <= y will be true for all tasks that intersect y.
 * 
 * @author rob
 *
 */
public class Problem13_12 {
	public static List<Integer> findVisitTimes(Set<Task> tasks) {
		// Put all tasks in an array, and sort using built-in Arrays util class.
		// Task's compareTo method causes the sort to be done on endTime property.
		Task[] sortedTasks = new Task[tasks.size()];
		int i = 0;
		for (Task t : tasks) {
			sortedTasks[i++] = t;
		}
		Arrays.sort(sortedTasks);
		// Loop through tasks, sorted by endTime. Visit only when the current task is disjoint from the previous visit time.
		// Keep track of visit times in Set for returning
		int lastVisitTime = Integer.MIN_VALUE;
		List<Integer> visitTimes = new LinkedList<Integer>();
		for (i = 0; i < sortedTasks.length; i++) {
			if (sortedTasks[i].getStartTime() > lastVisitTime) {
				lastVisitTime = sortedTasks[i].getEndTime();
				visitTimes.add(lastVisitTime);
			}
		}
		return visitTimes;
	}
	
	public static void main(String[] args) {
		Set<Task> tasks = new HashSet<Task>();
		tasks.add(new Task(0, 3));
		tasks.add(new Task(1, 4));
		tasks.add(new Task(2, 5));
		tasks.add(new Task(5, 8));
		tasks.add(new Task(4, 7));
		tasks.add(new Task(6, 9));
		
		System.out.println(findVisitTimes(tasks));
		
		// another example
		Set<Task> tasks2 = new HashSet<Task>();
		tasks2.add(new Task(1, 2));
		tasks2.add(new Task(0, 5));
		tasks2.add(new Task(0, 7));
		tasks2.add(new Task(3, 4));
		tasks2.add(new Task(30, 40));
		
		System.out.println(findVisitTimes(tasks2));
		
	}
}
