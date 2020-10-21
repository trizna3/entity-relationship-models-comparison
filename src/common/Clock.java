package common;

public class Clock {

	private long startTime;
	private long bound;
	private static Clock INSTANCE;
	
	public static Clock getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Clock();
		}
		return INSTANCE;
	}
	
	public void start(long bound) {
		this.bound = bound; 
		this.startTime = getTimeMillis();
	}
	
	public boolean boundReached() {
		return getTimeMillis() - startTime > bound;
	}
	
	private long getTimeMillis() {
		return System.nanoTime() / 1000000;
	}
}
