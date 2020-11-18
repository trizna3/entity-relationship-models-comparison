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

	/**
	 * Start counting time, setting upper bound for early stop
	 */
	public void start(long bound) {
		this.bound = bound; 
		this.startTime = getTimeMillis();
	}
	
	/**
	 * Start counting time, just to report time elapsed
	 */
	public void start() {
		this.startTime = getTimeMillis();
	}
	
	public boolean boundReached() {
		return getTimeMillis() - startTime > bound;
	}
	
	public long getTimeElapsed() {
		return getTimeMillis() - startTime;
	}
	
	private long getTimeMillis() {
		return System.nanoTime() / 1000000;
	}
}
