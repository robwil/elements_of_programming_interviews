package book.chapter.thirteen;

public class Task implements Comparable<Task> {
	private int startTime;
	private int endTime;
	public Task(int startTime, int endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public boolean equals(Task other) {
		if (other == null) return false;
		return this.startTime == other.startTime && this.endTime == other.endTime;
	}
	
	public String toString() {
		return "[" + startTime + ", " + endTime + "]";
	}
	
	@Override
	public int compareTo(Task other) {
		if (other == null) return 1;
		return this.endTime - other.endTime;
	}
	
}
